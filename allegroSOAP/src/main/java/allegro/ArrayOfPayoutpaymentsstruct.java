
package allegro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfPayoutpaymentsstruct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPayoutpaymentsstruct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="item" type="{https://webapi.allegro.pl/service.php}PayoutPaymentsStruct" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPayoutpaymentsstruct", propOrder = {
    "item"
})
public class ArrayOfPayoutpaymentsstruct {

    protected List<PayoutPaymentsStruct> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PayoutPaymentsStruct }
     * 
     * 
     */
    public List<PayoutPaymentsStruct> getItem() {
        if (item == null) {
            item = new ArrayList<PayoutPaymentsStruct>();
        }
        return this.item;
    }

}
