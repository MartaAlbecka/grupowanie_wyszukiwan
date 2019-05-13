package images;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.StrictMath.min;

/**
 * Created by Marta on 2017-10-24
 **/
public class ImageSimilarity {
    private static Logger log = Logger.getLogger(ImageSimilarity.class.getName());

    //CONSTS
    private static double edgeThreshold = 2.0;
    private static int discLevelGradient = 20;
    private static int discColorLength = 65;
    private static int[][] grWinX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}}; //Gradient Window X
    private static int[][] grWinY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}}; //Gradient Window Y
    private static int[] edgeFrameSize = new int[]{3, 3};
    public static int discLevelEdges = 16;
    private static int[] discEdges = new int[]{0, 3, 6, 9, 12, 15, 18, 21, 24, 27,
            30, 33, 36, 39, 42, 45, 49};

    private static double discGradientTable(int i) {
        return 12.7500 * i;
    }

    private static double discColorTable(int i) {
        return 255. / discColorLength * i;
    }

    public static double getImageSimilarity(ImageSimilarityData p1, ImageSimilarityData p2) {
        int N = 0;

        if (!p1.isHistReady()) {
            int status = calculateHistograms(p1);
            if (status == -1) return 0.0;
        }
        if (!p2.isHistReady()) {
            int status = calculateHistograms(p2);
            if (status == -1) return 0.0;
        }

        Map<Integer, Integer> p1ColorHist = p1.getColorHist();
        Map<Integer, Integer> p2ColorHist = p2.getColorHist();
        for (Integer color : p1ColorHist.keySet()) {
            if (p2ColorHist.containsKey(color)) {
                N += min(p1ColorHist.get(color), p2ColorHist.get(color));
            }
        }

        BufferedImage p1Image = p1.getImage();
        BufferedImage p2Image = p2.getImage();
        int D = min(p1Image.getHeight() * p1Image.getWidth(), p2Image.getHeight() * p2Image.getWidth());
        int tempMinDenominator = min(p1.getDeletedHistValue(), p2.getDeletedHistValue());
        double d_CH = ((double) N / (double) (D - tempMinDenominator));

        N = 0;
        for (int i = 0; i < discLevelEdges; i++) {
            N += min(p1.getEdgeDensityHist(i), p2.getEdgeDensityHist(i));
        }
        double d_ED = ((double) N / (double) D);

        N = 0;
        for (int i = 0; i < discLevelGradient; i++) {
            N += min(p1.getGradientHist(i), p2.getGradientHist(i));
        }

        double d_G = ((double) N / (double) D);

        double result = 0.6 * d_CH + 0.35 * d_ED + 0.05 * d_G;

        if (result > 1)
            log.info("Invalid image similarity result !!! (result = " + result + ")");

        return result;
    }

    public static int calculateHistograms(ImageSimilarityData p) {
        BufferedImage buffImg = p.getImage();
        //--- INITIALIZING CONSTANTS AND VARIABLES--//
        int w = buffImg.getWidth();
        int h = buffImg.getHeight();
        int[] imgRGB = buffImg.getRGB(0, 0, w, h, null, 0, w);

        int[] imgGray = calculateGrayImage(w, h, imgRGB);

        calculateColorHistogram(w, h, imgRGB, p);

        int[][] gradient = calculateGradient(w, h, imgGray);

        int[][] edges = calculateEdges(w, h, imgGray, gradient);

        normalizeGradient(w, h, gradient);

        calculateGradientDiscreteHistogram(w, h, gradient, p);

        int[][] edgeDensity = calculateEdgeDensity(w, h, edges);

        calculateEdgeDensityDiscreteHistogram(w, h, edgeDensity, p);

        p.setHistReady(true);
        return 0;
    }

    private static int[] calculateGrayImage(int w, int h, int[] imgRGB) {
        int[] imgGray = new int[w * h];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                //0.21 R + 0.72 G + 0.07 B
                imgGray[i * w + j] =
                        (int) (0.21 * (0xFF & (imgRGB[i * w + j] >> 16)) +
                                0.72 * (0xFF & (imgRGB[i * w + j] >> 8)) +
                                0.07 * (0xFF & (imgRGB[i * w + j])));
                if (imgGray[i * w + j] > 255 || imgGray[i * w + j] < 0)
                    log.info("Invalid value of imgGray = " + imgGray[i * w + j]);
            }
        }

        return imgGray;
    }

    private static void calculateColorHistogram(int w, int h, int[] imgRGB, ImageSimilarityData p) {
        byte[] temp = new byte[3];
        int codedDiscreteColor;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (int color = 0; color < 3; color++) {
                    for (int k = 1; k < discColorLength; k++) {
                        if (((imgRGB[i * w + j] >> (2 - color) * 8) & 0xFF) <= discColorTable(k)) {
                            temp[color] = (byte) (k - 1);
                            break;
                        }
                    }
                }
                //Code color to Integer variable
                codedDiscreteColor = temp[0] << 16;
                codedDiscreteColor |= temp[1] << 8;
                codedDiscreteColor |= temp[2];
                if (p.getColorHist().get(codedDiscreteColor) == null) {
                    p.addColorHist(codedDiscreteColor, 1);
                } else {
                    p.incrementColorHist(codedDiscreteColor);
                }
            }
        }
        int maxKey = findMaxKey(p.getColorHist());
        p.setDeletedHistValue(p.getColorHist().get(maxKey));
        p.removeColorHist(maxKey);
    }

    private static int[][] calculateGradient(int w, int h, int[] imgGray) {
        int[][] gradient = new int[h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int fx = 0;
                int fy = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        int m = getM(h, i, k);
                        int n = getM(w, j, l);

                        fx += grWinX[k + 1][l + 1] * imgGray[m * w + n];
                        fy += grWinY[k + 1][l + 1] * imgGray[m * w + n];
                    }
                }
                gradient[i][j] = (int) (Math.sqrt(fx * fx + fy * fy));
            }
        }

        return gradient;
    }

    private static int[][] calculateEdges(int w, int h, int[] imgGray, int[][] gradient) {
        int[][] edges = new int[h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (gradient[i][j] > imgGray[i * w + j] * (1 + edgeThreshold))
                    edges[i][j] = 1;
                else
                    edges[i][j] = 0;
            }
        }

        return edges;
    }

    private static void normalizeGradient(int w, int h, int[][] gradient) {
        double gradNormCoeff = findMax(gradient) / 255.0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                gradient[i][j] /= gradNormCoeff;
                if (gradient[i][j] > 255 || gradient[i][j] < 0)
                    log.info("Invalid value of gradient = " + gradient[i][j]);
            }
        }
    }

    private static void calculateGradientDiscreteHistogram(int w, int h, int[][] gradient, ImageSimilarityData p) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (int k = 1; k <= discLevelGradient; k++) {
                    if (gradient[i][j] <= discGradientTable(k)) {
                        p.incrGradientHist(k - 1);
                        break;
                    }
                }
            }
        }
    }

    private static int[][] calculateEdgeDensity(int w, int h, int[][] edges) {
        int[][] edgeDensity = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int tempED = 0;
                for (int k1 = -edgeFrameSize[0]; k1 <= edgeFrameSize[0]; k1++) {
                    for (int l = -edgeFrameSize[1]; l <= edgeFrameSize[1]; l++) {
                        int m, n;
                        if (i + k1 < 0)
                            m = 1;
                        else if (i + k1 > h - 1)
                            m = h - 1;
                        else
                            m = i + k1;

                        n = getM(w, j, l);

                        tempED += edges[m][n];
                    }
                }
                edgeDensity[i][j] = tempED;
            }
        }
        return edgeDensity;
    }

    private static void calculateEdgeDensityDiscreteHistogram(int w, int h, int[][] edgeDensity, ImageSimilarityData p) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                for (int k = 1; k < discLevelEdges; k++) {
                    if (edgeDensity[i][j] <= discEdges[k]) {
                        p.incrEdgeDensityHist(k - 1);
                        break;
                    }
                }
            }
        }
    }

    private static int getM(int dimension, int i, int k) {
        if (i + k < 0)
            return 0;
        else if (i + k > dimension - 1)
            return dimension - 1;
        else
            return i + k;
    }

    private static int findMaxKey(Map<Integer, Integer> histogram) {
        int tempMaxHist = 0;
        Integer maxKey = null;

        for (Map.Entry<Integer, Integer> entry : histogram.entrySet()) {
            if (entry.getValue() > tempMaxHist) {
                tempMaxHist = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        return maxKey;
    }

    private static int findMax(int[][] matrix) {
        int max = Integer.MIN_VALUE;
        for (int[] row : matrix) {
            for (int v : row) {
                max = Math.max(v, max);
            }
        }
        return max;
    }

}
