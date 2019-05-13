package soap;

import allegro.*;
import config.Variables;
import serialize.AllegroItem;
import serialize.PriceSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SoapClientImpl implements SoapClient {

    private final int BUFFER_SIZE = 1000;

    private ServiceService service;
    private ServicePort port;

    public SoapClientImpl() {
        this.service = new ServiceService();
        this.port = this.service.getServicePort();
    }

    public SoapClientImpl(ServiceService service) {
        this.service = service;
        this.port = this.service.getServicePort();
    }

    @Override
    public List<AllegroItem> getItemsByKeyWord(String searchValue) {
        List<AllegroItem> list = Collections.synchronizedList(new ArrayList<>());

        DoGetItemsListRequest itemsreq = getDoGetItemsListRequest(searchValue);

        int offset = 0;
        int itemsCount;

        do {
            long start = System.currentTimeMillis();
            itemsreq.setResultOffset(offset);
            DoGetItemsListResponse doGetItemsList = port.doGetItemsList(itemsreq);
            itemsCount = doGetItemsList.getItemsCount();

            System.out.println(itemsCount + " all items");

            if (itemsCount == 0) {
                return null;
            }

            List<ItemsListType> itemsList = doGetItemsList.getItemsList().getItem();

            offset += itemsList.size();

            long allegro = System.currentTimeMillis();
            System.out.println(allegro - start + "ms for allegro download\n" + itemsList.size() + " items");

            itemsList.parallelStream().forEach(item -> list.add(getAllegroItem(item)));

            System.out.println(System.currentTimeMillis() - allegro + "ms for creating " + itemsList.size() +
                    " allegro items\n");

        } while (itemsCount > offset);

        return list;
    }

    private AllegroItem getAllegroItem(ItemsListType item) {
        String photoUrl = null;
        if (item.getPhotosInfo() != null) {
            for (PhotoInfoType photo : item.getPhotosInfo().getItem()) {
                if (photo.isPhotoIsMain() && photo.getPhotoSize().equals("small")) {
                    photoUrl = photo.getPhotoUrl();
                    break;
                }
            }
        }
        List<PriceSerializable> prices = new ArrayList<>();
        for (PriceInfoType price : item.getPriceInfo().getItem()) {
            prices.add(new PriceSerializable(price.getPriceType(), price.getPriceValue()));
        }

        String userLogin = null;
        if (item.getSellerInfo() != null) {
            userLogin = item.getSellerInfo().getUserLogin();
        }

        Date endingTime = null;
        if (item.getEndingTime() != null) {
            endingTime = item.getEndingTime().toGregorianCalendar().getTime();
        }
        return new AllegroItem(item.getItemTitle(), item.getItemId(), item.getCategoryId(),
                item.getConditionInfo(), userLogin, endingTime, photoUrl, prices);
    }

    private DoGetItemsListRequest getDoGetItemsListRequest(String searchValue) {
        DoGetItemsListRequest itemsreq = new DoGetItemsListRequest();
        itemsreq.setCountryId(Variables.COUNTRY_CODE);
        itemsreq.setWebapiKey(Variables.WEB_API_KEY);

        ArrayOfFilteroptionstype filter = new ArrayOfFilteroptionstype();

        FilterOptionsType fot = new FilterOptionsType();
        fot.setFilterId(Variables.SEARCH);

        ArrayOfString search = new ArrayOfString();
        search.getItem().add(searchValue);
        fot.setFilterValueId(search);

        filter.getItem().add(fot);

        itemsreq.setFilterOptions(filter);

        itemsreq.setResultSize(BUFFER_SIZE);
        return itemsreq;
    }
}
