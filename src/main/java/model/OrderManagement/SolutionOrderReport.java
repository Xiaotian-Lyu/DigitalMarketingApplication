package model.OrderManagement;

import model.MarketModel.Channel;
import model.MarketModel.Market;
import model.MarketModel.Ads;
import model.ProductManagement.SolutionOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolutionOrderReport {
    List<Order> solutionOrders;
    int totalRevenue;
    Map<Market, Integer> revenuesByMarket;
    Map<Channel, Integer> revenuesByChannel;
    Map<SolutionOffer, Integer> revenuesBySolutionOffer;

    Map<Ads, Integer> revenuesByAds;

    //Report of solution order
    public SolutionOrderReport(List<Order> orders) {
        this.solutionOrders = pickSolutionOrders(orders);
        this.totalRevenue = calTotalRevenue(this.solutionOrders);
        this.revenuesByMarket = calRevenueByMarket(this.solutionOrders);
        this.revenuesByChannel = calRevenueByChannel(this.solutionOrders);
        this.revenuesBySolutionOffer = calRevenuesBySolutionOffer(this.solutionOrders);
        this.revenuesByAds = calRevenuesByAds(this.solutionOrders);
    }

    /**
     * @param solutionOrders solution Orders
     * @return Revenues By Solution Offer
     */
    private Map<SolutionOffer, Integer> calRevenuesBySolutionOffer(List<Order> solutionOrders) {
        Map<SolutionOffer, Integer> revenuesBySolutionOffer = new HashMap<>();
        for (Order solutionOrder : solutionOrders) {
            SolutionOffer offer = solutionOrder.offer;
            revenuesBySolutionOffer.put(offer, revenuesBySolutionOffer.getOrDefault(offer, 0) + solutionOrder.getOrderTotal());
        }
        return revenuesBySolutionOffer;
    }


    private Map<Ads, Integer> calRevenuesByAds(List<Order> solutionOrders) {
        Map<Ads, Integer> revenuesByAds = new HashMap<>();
        for (Order solutionOrder : solutionOrders) {
            Ads ads = solutionOrder.mca.getAds();
            revenuesByAds.put(ads, revenuesByAds.getOrDefault(ads, 0) + solutionOrder.getOrderTotal());
        }
        return revenuesByAds;
    }

    /**
     * @param solutionOrders solution Orders
     * @return Revenue Of Markets
     */
    private Map<Market, Integer> calRevenueByMarket(List<Order> solutionOrders) {
        Map<Market, Integer> revenuesByMarket = new HashMap<>();
        //traverse the solution order
        for (Order solutionOrder : solutionOrders) {
            //add all the same market price total
            Market market = solutionOrder.getMca().getMarket();
            revenuesByMarket.put(market, revenuesByMarket.getOrDefault(market, 0) + solutionOrder.getOrderTotal());
        }
        return revenuesByMarket;
    }

    /**
     * find The Most Revenue Of Market
     * @return maxMarket
     */
    public Market findTheMostRevenueOfMarket(){
        Market maxMarket = null;
        int maxRevenue = 0;
        for (Market market : revenuesByMarket.keySet()) {
            if(maxMarket == null){
                maxMarket = market;
            }
            if(revenuesByMarket.get(market) > maxRevenue ){
                maxMarket = market;
                maxRevenue = revenuesByMarket.get(market);
            }
        }
        return maxMarket;
    }

    /**
     * @param solutionOrders solution Orders
     * @return revenues of Channel
     */
    private Map<Channel, Integer> calRevenueByChannel(List<Order> solutionOrders) {
        Map<Channel, Integer> revenuesByChannel = new HashMap<>();
        for (Order solutionOrder : solutionOrders) {
            Channel channel = solutionOrder.getMca().getChannel();
            revenuesByChannel.put(channel, revenuesByChannel.getOrDefault(channel, 0) + solutionOrder.getOrderTotal());
        }
        return revenuesByChannel;
    }

    /**
     * find The Max Revenue By Channel
     * @return Channel
     */
    public Channel findTheMaxRevenueByChannel(){
        Channel maxchannel = null;
        int maxRevenue = 0;
        for (Channel channel : revenuesByChannel.keySet()) {
            if(maxchannel == null){
                maxchannel = channel;
            }
            if(revenuesByChannel.get(channel) > maxRevenue){
                maxchannel = channel;
                maxRevenue = revenuesByChannel.get(channel);
            }
        }
        return maxchannel;
    }

    /**
     * @param orders orders
     * @return total revenue
     */
    private int calTotalRevenue(List<Order> orders) {
        int sum = 0;
        for (Order order : orders) {
            sum += order.getOrderTotal();
        }
        return sum;
    }

    private List<Order> pickSolutionOrders(List<Order> orders) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            if (order.isSolutionOrder()) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    public List<Order> getSolutionOrders() {
        return solutionOrders;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public Map<Market, Integer> getRevenuesByMarket() {
        return revenuesByMarket;
    }

    public Map<Channel, Integer> getRevenuesByChannel() {
        return revenuesByChannel;
    }

    public Map<Ads, Integer> getRevenuesByAds() {
        return revenuesByAds;
    }


    public Map<SolutionOffer, Integer> getRevenuesBySolutionOffer() {
        return revenuesBySolutionOffer;
    }
}
