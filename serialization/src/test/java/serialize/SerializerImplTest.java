package serialize;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class SerializerImplTest {
    private SerializerImpl serializer;
    private String FILENAME = "tmp";
    private List<AllegroItem> items;
    private List<AllegroItem> items2;
    private String TITLE1 = "tytul1";
    private String TITLE2 = "tytul2";
    private long ITEMID1 = 1;
    private long ITEMID2 = 2;
    private int CATEGORY1 = 1;
    private int CATEGORY2 = 2;
    private String CONDITIONINFO1 = "nowe";
    private String CONDITIONINFO2 = "stary";
    private String SELLERLOGIN1 = "login1";
    private String SELLERLOGIN2 = "login2";
    private Date ENDDATE1 = new Date();
    private Date ENDDATE2 = new Date();
    private String PHOTO1 = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Cluster-2.svg/1200px-Cluster-2.svg.png";
    private String PHOTO2 = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Iscosahedral_water_cluster_100.png/1200px-Iscosahedral_water_cluster_100.png";
    private String PRICETYPE1 = "typ1";
    private String PRICETYPE2 = "typ2";
    private float PRICEVALUE1 = 1;
    private float PRICEVALUE2 = 2;
    private PriceSerializable PRICE1 = new PriceSerializable(PRICETYPE1, PRICEVALUE1);
    private PriceSerializable PRICE2 = new PriceSerializable(PRICETYPE2, PRICEVALUE2);
    private List<PriceSerializable> PRICES1 = new ArrayList<>();
    private List<PriceSerializable> PRICES2 = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        serializer = new SerializerImpl();
        serializer.setFilePath("../serialize/%s.ser");
        items = new ArrayList<>();
        PRICES1.add(PRICE1);
        PRICES2.add(PRICE2);

        AllegroItem item1 = new AllegroItem(TITLE1, ITEMID1, CATEGORY1, CONDITIONINFO1, SELLERLOGIN1, ENDDATE1, PHOTO1, PRICES1);
        AllegroItem item2 = new AllegroItem(TITLE2, ITEMID2, CATEGORY2, CONDITIONINFO2, SELLERLOGIN2, ENDDATE2, PHOTO2, PRICES2);
        items.add(item1);
        items.add(item2);


        items2 = new ArrayList<>();
        AllegroItem item3 = new AllegroItem(TITLE1, ITEMID1, CATEGORY1, CONDITIONINFO1, SELLERLOGIN1, ENDDATE1,
                null, PRICES1);
        AllegroItem item4 = new AllegroItem(TITLE2, ITEMID2, CATEGORY2, CONDITIONINFO2, SELLERLOGIN2, ENDDATE2,
                null, PRICES2);
        items2.add(item3);
        items2.add(item4);
    }

    @Test
    public void serializeDeserialize() throws Exception {
        serializer.serialize(items, FILENAME);
        List<AllegroItem> deserializedItems = serializer.deserialize(FILENAME);
        assertThat(deserializedItems.get(0).getTitle(), equalTo(TITLE1));
        assertThat(deserializedItems.get(0).getItemId(), equalTo(ITEMID1));
        assertThat(deserializedItems.get(0).getCategoryId(), equalTo(CATEGORY1));
        assertThat(deserializedItems.get(0).getConditionInfo(), equalTo(CONDITIONINFO1));
        assertThat(deserializedItems.get(0).getSellerLogin(), equalTo(SELLERLOGIN1));
        assertThat(deserializedItems.get(0).getEndingTime(), equalTo(ENDDATE1));
        assertThat(deserializedItems.get(0).getPhotoUrl(), equalTo(PHOTO1));
        assertThat(deserializedItems.get(0).getImage(), not(equalTo(null)));
        assertThat(deserializedItems.get(0).getPrices().get(0).getPriceType(), equalTo(PRICETYPE1));
        assertThat(deserializedItems.get(0).getPrices().get(0).getPriceValue(), equalTo(PRICEVALUE1));

        assertThat(deserializedItems.get(1).getTitle(), equalTo(TITLE2));
        assertThat(deserializedItems.get(1).getItemId(), equalTo(ITEMID2));
        assertThat(deserializedItems.get(1).getCategoryId(), equalTo(CATEGORY2));
        assertThat(deserializedItems.get(1).getConditionInfo(), equalTo(CONDITIONINFO2));
        assertThat(deserializedItems.get(1).getSellerLogin(), equalTo(SELLERLOGIN2));
        assertThat(deserializedItems.get(1).getEndingTime(), equalTo(ENDDATE2));
        assertThat(deserializedItems.get(1).getPhotoUrl(), equalTo(PHOTO2));
        assertThat(deserializedItems.get(1).getImage(), not(equalTo(null)));
        assertThat(deserializedItems.get(1).getPrices().get(0).getPriceType(), equalTo(PRICETYPE2));
        assertThat(deserializedItems.get(1).getPrices().get(0).getPriceValue(), equalTo(PRICEVALUE2));
    }

    @Test
    public void noImageSerialization() throws Exception {
        serializer.serialize(items2, FILENAME);
        List<AllegroItem> deserializedItems = serializer.deserialize(FILENAME);

        assertThat(deserializedItems.get(0).getTitle(), equalTo(TITLE1));
        assertThat(deserializedItems.get(0).getItemId(), equalTo(ITEMID1));
        assertThat(deserializedItems.get(0).getCategoryId(), equalTo(CATEGORY1));
        assertThat(deserializedItems.get(0).getConditionInfo(), equalTo(CONDITIONINFO1));
        assertThat(deserializedItems.get(0).getSellerLogin(), equalTo(SELLERLOGIN1));
        assertThat(deserializedItems.get(0).getEndingTime(), equalTo(ENDDATE1));
        assertThat(deserializedItems.get(0).getPhotoUrl(), equalTo(null));
        assertThat(deserializedItems.get(0).getImage(), equalTo(null));
        assertThat(deserializedItems.get(0).getPrices().get(0).getPriceType(), equalTo(PRICETYPE1));
        assertThat(deserializedItems.get(0).getPrices().get(0).getPriceValue(), equalTo(PRICEVALUE1));

        assertThat(deserializedItems.get(1).getTitle(), equalTo(TITLE2));
        assertThat(deserializedItems.get(1).getItemId(), equalTo(ITEMID2));
        assertThat(deserializedItems.get(1).getCategoryId(), equalTo(CATEGORY2));
        assertThat(deserializedItems.get(1).getConditionInfo(), equalTo(CONDITIONINFO2));
        assertThat(deserializedItems.get(1).getSellerLogin(), equalTo(SELLERLOGIN2));
        assertThat(deserializedItems.get(1).getEndingTime(), equalTo(ENDDATE2));
        assertThat(deserializedItems.get(1).getPhotoUrl(), equalTo(null));
        assertThat(deserializedItems.get(1).getImage(), equalTo(null));
        assertThat(deserializedItems.get(1).getPrices().get(0).getPriceType(), equalTo(PRICETYPE2));
        assertThat(deserializedItems.get(1).getPrices().get(0).getPriceValue(), equalTo(PRICEVALUE2));
    }

}