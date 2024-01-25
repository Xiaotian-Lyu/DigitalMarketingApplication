package model.MarketModel;

import model.MarketModel.MarketChannelAssignment;

import java.util.ArrayList;
import java.util.List;

public class AdsCatalog {
    List<Ads> adsList;

    int id = 1;

    public AdsCatalog() {
        this.adsList = new ArrayList<>();
    }

    public Ads newAds(MarketChannelAssignment mca, int duration) {
        Ads ads = new Ads(mca, duration, id++);
        adsList.add(ads);
        return ads;
    }

    public List<Ads> getAdsList() {
        return adsList;
    }



}