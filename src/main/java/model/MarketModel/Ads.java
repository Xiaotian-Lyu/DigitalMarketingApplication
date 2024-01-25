package model.MarketModel;

import model.MarketModel.MarketChannelAssignment;

public class Ads {
    MarketChannelAssignment mca;
    int duration;
    int id;

    public Ads(MarketChannelAssignment mca, int duration, int id) {
        this.mca = mca;
        this.duration = duration;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void printAds() {
        System.out.println("Check out the bundles of " + mca.getMarket().getCharacteristics().get(0) + ", " +
                "all bundles are " + (100 - mca.getDiscountPercent()) + "% percent off!");
    }
    public MarketChannelAssignment getMca() {
        return mca;
    }

}
