package serialize;

import java.io.Serializable;

public class PriceSerializable implements Serializable {
    private String priceType;
    private float priceValue;

    public PriceSerializable(String priceType, float priceValue) {
        this.priceType = priceType;
        this.priceValue = priceValue;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public float getPriceValue() {
        return priceValue;
    }

    public void setPriceValue(float priceValue) {
        this.priceValue = priceValue;
    }
}
