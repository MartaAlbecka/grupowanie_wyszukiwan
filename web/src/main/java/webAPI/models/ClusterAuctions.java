package webAPI.models;

import java.util.List;

public class ClusterAuctions {
    private int clusterId;
    private List<Auction> auctions;

    public ClusterAuctions(int clusterId, List<Auction> auctions) {
        this.clusterId = clusterId;
        this.auctions = auctions;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }
}
