package attributes;

import images.ImageSimilarity;
import images.ImageSimilarityData;

import java.awt.image.BufferedImage;

/**
 * Created on 17-Oct-17.
 */
public class GraphicAttribute implements Attribute {

    private String name;
    private BufferedImage photo;
    private ImageSimilarityData imageSimilarityData;

    public GraphicAttribute(String name, BufferedImage photo) {
        this.name = name;
        this.photo = photo;
    }

    @Override
    public double distance(Attribute attribute) {
        if (attribute instanceof GraphicAttribute) {

            GraphicAttribute attribute1 = (GraphicAttribute) attribute;

            if (imageSimilarityData == null)
                imageSimilarityData = new ImageSimilarityData(photo);

            if (attribute1.imageSimilarityData == null)
                attribute1.imageSimilarityData = new ImageSimilarityData(attribute1.photo);

            return 100 * (1 - ImageSimilarity.getImageSimilarity(
                    imageSimilarityData, attribute1.imageSimilarityData));
        }

        throw new IllegalArgumentException("Distance between two different attribute types cannot be calculated.");
    }


    @Override
    public Attribute copy() {
        GraphicAttribute graphicAttribute = new GraphicAttribute(getName(), photo);
        graphicAttribute.imageSimilarityData = imageSimilarityData;
        return graphicAttribute;
    }

    @Override
    public String toString() {
        return photo.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public ImageSimilarityData getImageSimilarityData() {
        return imageSimilarityData;
    }
}
