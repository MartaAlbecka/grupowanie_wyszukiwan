package images;

import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by Marta on 2017-12-01
 **/
public class ImageSimilarityTest {
    private ImageSimilarityData ITEM1;
    private ImageSimilarityData ITEM2;

    private String PHOTO1 = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Cluster-2.svg/1200px-Cluster-2.svg.png";
    private String PHOTO2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Iscosahedral_water_cluster_100.png/1200px-Iscosahedral_water_cluster_100.png";

    @Before
    public void setUp() throws Exception {
        ITEM1 = new ImageSimilarityData(ImageIO.read(new URL(PHOTO1)));
        ITEM2 = new ImageSimilarityData(ImageIO.read(new URL(PHOTO2)));
    }

    @Test
    public void getImageSimilarity() throws Exception {
        assertThat(ImageSimilarity.getImageSimilarity(ITEM1, ITEM1), equalTo(1.0));
        assertThat(ImageSimilarity.getImageSimilarity(ITEM1, ITEM2), not(equalTo(1.0)));

    }

}