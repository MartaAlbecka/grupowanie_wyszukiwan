package soap;

import serialize.AllegroItem;

import java.util.List;

public interface SoapClient {
    /**
     * Method communicates with allegro and gets the search result for specified input
     * @param searchValue word used by user in the search input field
     * @return List<ItemsListType> List of the items that apply to search conditions
     */
     List<AllegroItem> getItemsByKeyWord(String searchValue);
}
