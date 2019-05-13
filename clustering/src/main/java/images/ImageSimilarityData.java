package images;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Marta on 2017-11-01
 **/
public class ImageSimilarityData {
    private static Logger log = Logger.getLogger(ImageSimilarityData.class.getName());

    //image fields
    private Map<Integer, Integer> colorHist = new HashMap<>();
    private String photoUrl;
    private BufferedImage photo = new BufferedImage(128, 96, BufferedImage.TYPE_INT_RGB);
    private int deletedHistValue = 0;
    private int[] gradientHist = new int[20];
    private int[] edgeDensityHist = new int[16];
    private boolean histReady = false;

    public ImageSimilarityData(BufferedImage photo) {
        this.photo = photo;
    }

    public boolean isEnoughSimilarity(ImageSimilarityData item, double similarity) {
        double imageSimilarity = ImageSimilarity.getImageSimilarity(this, item);
        return imageSimilarity >= similarity;
    }

    public BufferedImage getImage() {
        return photo;
    }

    public Map<Integer, Integer> getColorHist() {
        return colorHist;
    }

    public void addColorHist(Integer k, Integer v) {
        colorHist.put(k, v);
    }

    public void removeColorHist(Integer k) {
        colorHist.remove(k);
    }

    public void incrementColorHist(Integer key) {
        colorHist.replace(key, colorHist.get(key) + 1);
    }

    public void setDeletedHistValue(int deletedHistValue) {
        this.deletedHistValue = deletedHistValue;
    }

    public void incrGradientHist(int k) {
        this.gradientHist[k] += 1;
    }

    public void incrEdgeDensityHist(int k) {
        this.edgeDensityHist[k] += 1;
    }

    public boolean isHistReady() {
        return histReady;
    }

    public void setHistReady(boolean histReady) {
        this.histReady = histReady;
    }

    public int getEdgeDensityHist(int i) {
        return edgeDensityHist[i];
    }

    public int getGradientHist(int i) {
        return gradientHist[i];
    }

    public int getDeletedHistValue() {
        return deletedHistValue;
    }
}
