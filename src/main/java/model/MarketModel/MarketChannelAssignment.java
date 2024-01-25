/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import model.MarketModel.Ads;
import model.ProductManagement.SolutionOffer;

import java.util.List;

/**
 *
 * @author kal bugrara
 */
/**
 *
 * @author kal bugrara
 */
public class MarketChannelAssignment {

    Market market;
    Channel channel;
    int adBudget;
    int targetRevenue;
    int revenues;
    List<SolutionOffer> offers;
    int discountPercent;
    /**
     * @param m Market
     * @param c Channel
     * @param discount add discount
     * @param adBudget add budget
     */
    public MarketChannelAssignment(Market m, Channel c, int discount, int adBudget){
        market = m;
        channel = c;
        this.discountPercent = discount;
        this.adBudget = adBudget;
    }
    Ads ads;

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }
    public int getDiscountPercent() {
        return discountPercent;
    }

    public Market getMarket() {
        return market;
    }

    public Channel getChannel() {
        return channel;
    }

    public int getAdBudget() {
        return adBudget;
    }

}
