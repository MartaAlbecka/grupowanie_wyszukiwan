import allegro.*;
import config.Variables;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created on 08-Sep-17.
 */
public class RequestTest {

    private ServiceService service;
    private ServicePort allegro;
    private Variables variables;

    @Before
    public void setUp() throws Exception {
        service = new ServiceService();
        allegro = service.getServicePort();
        variables = new Variables();
    }

    @Test
    public void listFilters() throws Exception {
        // Kod pokazuje jak wylistować sobie oferty z kategorii Motoryzacja oraz użycie cechy Uszkodzone->Tak
        // Do kodu cechy dobrałem się poprzez wyszukiwanie w kategorii ofert , a następnie drukowanie każdej oferty
        // z osobna pod względem jej cech i w ten sposób ctrl + f i szukamy odpowiedniej frazy cechy :) kod w jaki
        // znalazłem te id cechy(dodałem filter do kategorii , żeby nie było za dużo wyników) :

        DoGetItemsListRequest itemsreq = new DoGetItemsListRequest();
        itemsreq.setCountryId(variables.COUNTRY_CODE);
        itemsreq.setWebapiKey(variables.WEB_API_KEY);
        Integer scope = 0, size = 0, offset = 0;
        itemsreq.setResultOffset(offset);
        itemsreq.setResultSize(size);
        itemsreq.setResultScope(scope);

        ArrayOfFilteroptionstype filter = new ArrayOfFilteroptionstype();
        // Filter Kategorii , id można zdobyć poprzez http://allegro.pl/category_map.php?id=0&cols=3&show=cids lub funkcją
        // z webapi , ale to już sobie można znaleźć
        FilterOptionsType fotcat = new FilterOptionsType();
        fotcat.setFilterId("category");
        ArrayOfString categories = new ArrayOfString();
        categories.getItem().add("149");
        fotcat.setFilterValueId(categories);
        // Dodajemy do gamy filtrów
        filter.getItem().add(fotcat);

        itemsreq.setFilterOptions(filter);

        DoGetItemsListResponse doGetItemsList = allegro.doGetItemsList(itemsreq);

        List<FiltersListType> item = doGetItemsList.getFiltersList().getItem();
        // Ograniczyłem też iterację do 1000 jak później widać w ifie , bo nie chciałem odrazu całości przeszukiwać
        // ale jak będziecie mieć lipe i nie znajdziecie to wywalcie to po prostu
        int i = 0;

        for (FiltersListType flt : item) {
            if (i < 1000) {
                System.out.println("-----------------");
                System.out.println("Filter name : " + flt.getFilterName());
                System.out.println("Filter id   : " + flt.getFilterId());
                if (flt.getFilterValues() != null)
                    for (FilterValueType fvt : flt.getFilterValues().getItem())
                        System.out.println("Filter Value id : " + fvt.getFilterValueId() + " Filter Value name : " + fvt.getFilterValueName());
                i++;
            }
        }

    }

    @Test
    public void attributeSearch() throws Exception {
        //-------------------------------------------------------------------------------------------------------------------------------
        // Kod do Wyszukiwania po cesze którą już mamy(u mnie 178 , czyli Uszkodzone i wartość cechy czyli 2):
        // Tworzymy Request
        DoGetItemsListRequest itemsreq = new DoGetItemsListRequest();
        itemsreq.setCountryId(variables.COUNTRY_CODE); // Kod Kraju który wcześniej trzeba było zaimplementować
        itemsreq.setWebapiKey(variables.WEB_API_KEY); // Klucz WebApi
        Integer scope=0,size=0,offset=0; // Te wartości mnie nie interesowały więc 0 :)
        itemsreq.setResultOffset(offset);
        itemsreq.setResultSize(size);
        itemsreq.setResultScope(scope);
        // Inicjujemy Filter
        ArrayOfFilteroptionstype filter = new ArrayOfFilteroptionstype();

        FilterOptionsType fotcat = new FilterOptionsType();
        fotcat.setFilterId("category");
        ArrayOfString categories = new ArrayOfString();
        categories.getItem().add("149");
        fotcat.setFilterValueId(categories);

        filter.getItem().add(fotcat);

        FilterOptionsType fotfeature = new FilterOptionsType();
        fotfeature.setFilterId("178");
        ArrayOfString yes = new ArrayOfString();
        yes.getItem().add("2");
        fotfeature.setFilterValueId(yes);

        filter.getItem().add(fotfeature);

        itemsreq.setFilterOptions(filter);

        DoGetItemsListResponse doGetItemsList = allegro.doGetItemsList(itemsreq);

    }


}
