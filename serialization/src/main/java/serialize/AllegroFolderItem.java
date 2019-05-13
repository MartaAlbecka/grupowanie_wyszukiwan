package serialize;

import java.io.Serializable;

/**
 * Created by Marta on 2017-11-20
 **/
public class AllegroFolderItem implements Serializable {
    private Integer folderId;
    private String title;
    private long itemId;
    private String sellerLogin;

    public AllegroFolderItem(Integer folderId, AllegroItem item) {
        this.folderId = folderId;
        this.title = item.getTitle();
        this.itemId = item.getItemId();
        this.sellerLogin = item.getSellerLogin();

    }

    public AllegroFolderItem(Integer folderId, String title, long itemId, String sellerLogin) {
        this.folderId = folderId;
        this.title = title;
        this.itemId = itemId;
        this.sellerLogin = sellerLogin;
    }

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
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

    public String getSellerLogin() {
        return sellerLogin;
    }

    public void setSellerLogin(String sellerLogin) {
        this.sellerLogin = sellerLogin;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AllegroFolderItem) {
            AllegroFolderItem item = (AllegroFolderItem) other;

            if (item.folderId.equals(folderId) && item.itemId == itemId &&
                    item.sellerLogin.equals(sellerLogin) && item.title.equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cluster:\t" + this.folderId + "\tSeller:\t" + this.sellerLogin + "\tItem_ID:\t" + this.itemId + "\tTitle:\t" + this.title;
    }
}
