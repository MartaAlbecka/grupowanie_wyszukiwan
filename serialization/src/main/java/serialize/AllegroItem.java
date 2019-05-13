package serialize;


import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class AllegroItem implements Serializable {
    private static Logger log = Logger.getLogger(AllegroItem.class.getName());

    private String title;
    private long itemId;
    private String sellerLogin;
    private Date endingTime;
    private String photoUrl;
    private transient BufferedImage image;
    private List<PriceSerializable> prices;
    private transient String photoBase64;
    private transient String conditionInfo;
    private transient int categoryId;

    public AllegroItem() {
    }

    public AllegroItem(String title, long itemId, int categoryId, String conditionInfo, String sellerLogin,
                       Date endingTime, String photoUrl, List<PriceSerializable> price) {
        this.title = title;
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.conditionInfo = conditionInfo;
        this.sellerLogin = sellerLogin;
        this.photoUrl = photoUrl;
        this.prices = price;
        this.endingTime = endingTime;

        if (photoUrl != null) {
            this.image = getImage(photoUrl);
        }
    }

    public AllegroItem(AllegroItem allegroItem) {
        this(allegroItem.title, allegroItem.itemId, allegroItem.categoryId, allegroItem.conditionInfo,
                allegroItem.sellerLogin, allegroItem.endingTime, allegroItem.photoUrl, allegroItem.prices);
    }

    public AllegroItem(String title, String photoUrl) {
        this.title = title;
        this.photoUrl = photoUrl;

        if (photoUrl != null) {
            this.image = getImage(photoUrl);
        }
    }

    public String getItemUrl() {
        return "http://allegro.pl/show_item.php?item=" + itemId;
    }

    public static BufferedImage getImage(String photoUrl) {
        try {
            URL url = new URL(photoUrl);
            return ImageIO.read(url);
        } catch (MalformedURLException e) {
            log.info("URL to auction: " + photoUrl + " is not correct and could't be processed " + e);
        } catch (IOException e) {
            log.info("Image to auction: " + photoUrl + "  could't be processed " + e);
        }

        return null;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (image != null) {
            out.writeBoolean(true);
            ImageIO.write(image, "png", out); // png is lossless
        } else {
            out.writeBoolean(false);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        boolean imageExists = in.readBoolean();
        if (imageExists) {
            image = ImageIO.read(in);
        }
    }

    public BufferedImage getImage() {
        if (this.photoBase64 == null && this.image != null) {
            this.photoBase64 = imageToBase64(image);
        }
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public List<PriceSerializable> getPrices() {
        return prices;
    }

    public void setPrices(List<PriceSerializable> prices) {
        this.prices = prices;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getConditionInfo() {
        return conditionInfo;
    }

    public void setConditionInfo(String conditionInfo) {
        this.conditionInfo = conditionInfo;
    }

    public String getSellerLogin() {
        return sellerLogin;
    }

    public void setSellerLogin(String sellerLogin) {
        this.sellerLogin = sellerLogin;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    private static String imageToBase64(BufferedImage image) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

}
