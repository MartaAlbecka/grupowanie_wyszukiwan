package soap;

import allegro.*;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.junit.Before;
import org.junit.Test;
import serialize.AllegroItem;
import serialize.SerializerImpl;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SoapClientImplTest {

    private ServiceService mockService;
    private ServicePort mockPort;
    private SoapClientImpl soapClient;
    private String TITLE = "title";
    private int CATEGORY = 1;
    private String PRICE_TYPE = "price type";
    private float PRICE_VALUE = 10;
    private String SEARCH_VALUE = "search";

    @Before
    public void setUp() throws Exception {
        mockService = mock(ServiceService.class);
        mockPort = mock(ServicePort.class);
        when(mockService.getServicePort()).thenReturn(mockPort);
        soapClient = new SoapClientImpl(mockService);

        PriceInfoType price = new PriceInfoType();
        price.setPriceType(PRICE_TYPE);
        price.setPriceValue(PRICE_VALUE);

        ArrayOfPriceinfotype prices = new ArrayOfPriceinfotype();
        prices.getItem().add(price);

        ItemsListType item = new ItemsListType();
        item.setItemTitle(TITLE);
        item.setCategoryId(CATEGORY);
        item.setPriceInfo(prices);
        item.setEndingTime(new XMLGregorianCalendarImpl());

        ArrayOfPhotoinfotype photoTypes = new ArrayOfPhotoinfotype();
        item.setPhotosInfo(photoTypes);

        ArrayOfItemslisttype array = new ArrayOfItemslisttype();
        array.getItem().add(item);

        DoGetItemsListResponse response = new DoGetItemsListResponse();
        response.setItemsList(array);
        response.setItemsCount(1);


        when(mockPort.doGetItemsList(any())).thenReturn(response);
    }

    @Test
    public void getItemsByKeyWord() throws Exception {
        List<AllegroItem> items = soapClient.getItemsByKeyWord(SEARCH_VALUE);

        verify(mockPort, times(1)).doGetItemsList(any());
        assertThat(items, notNullValue());
        assertThat(items.get(0).getTitle(), equalTo(TITLE));
        assertThat(items.get(0).getCategoryId(), equalTo(CATEGORY));
        assertThat(items.get(0).getPrices().get(0).getPriceValue(), equalTo(PRICE_VALUE));
        assertThat(items.get(0).getPrices().get(0).getPriceType(), equalTo(PRICE_TYPE));
    }

    public static List<AllegroItem> getAllegroItemsFor(String searchValue) {
        SerializerImpl serializer = new SerializerImpl();
        serializer.setFilePath("../serialize/%s.ser");

        long start = System.currentTimeMillis();

        List<AllegroItem> deserialize = serializer.deserialize(searchValue);
        if (deserialize != null) {
            System.out.println(System.currentTimeMillis() - start + "ms for deserialization");
            return deserialize;
        } else {
            SoapClientImpl soapClient = new SoapClientImpl();

            start = System.currentTimeMillis();

            final List<AllegroItem> allegroItems = soapClient.getItemsByKeyWord(searchValue);

            System.out.println(System.currentTimeMillis() - start + "ms for serialization");

            serializer.serialize(allegroItems, searchValue);
            return allegroItems;
        }
    }
}