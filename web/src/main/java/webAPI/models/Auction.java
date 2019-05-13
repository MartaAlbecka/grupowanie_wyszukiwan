package webAPI.models;

import serialize.AllegroItem;
import serialize.PriceSerializable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

public class Auction implements Serializable{

    private String title;
    private String auctionUrl;
    private String sellerLogin;
    private String endingTime;
    private String photoBase64;
    private List<PriceSerializable> prices;

    public Auction(AllegroItem allegroItem) {
        this.title = allegroItem.getTitle();
        this.auctionUrl = allegroItem.getItemUrl();
        this.sellerLogin = allegroItem.getSellerLogin();
        if (allegroItem.getEndingTime() == null) {
            this.endingTime = "Kup teraz";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String dateStr = sdf.format(allegroItem.getEndingTime());
            this.endingTime = dateStr;
        }
        this.photoBase64 = "data:image/png;base64, " + allegroItem.getPhotoBase64();
        this.prices = allegroItem.getPrices();
    }

    public String getTitle() {
        return title;
    }

    public String getAuctionUrl() {
        return auctionUrl;
    }

    public String getSellerLogin() {
        return sellerLogin;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public List<PriceSerializable> getPrices() {
        return prices;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuctionUrl(String auctionUrl) {
        this.auctionUrl = auctionUrl;
    }

    public void setSellerLogin(String sellerLogin) {
        this.sellerLogin = sellerLogin;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public void setPrices(List<PriceSerializable> prices) {
        this.prices = prices;
    }
}
