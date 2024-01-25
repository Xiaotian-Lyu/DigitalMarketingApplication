/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.CustomerManagement;

import model.MarketModel.Market;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author kal bugrara
 */
public class MarketCatalog {

    ArrayList<Market> markets;

    public MarketCatalog() {
        this.markets = new ArrayList<>();
        for(String s:marketType){
            newMarket(s);
        }
    }
    private static final String[] marketType = {"Asian flavors", "Western tastes",
            "Latin American influences", "Middle Eastern flavors", "African culinary preferences"};

    public Market newMarket(String s) {
        Market market = new Market(s);
        markets.add(market);
        return market;
    }

    public ArrayList<Market> getMarkets() {
        return markets;
    }

    public Market pickRandomMarket() {
        Random random = new Random();
        return this.markets.get(random.nextInt(markets.size()));
    }
    
}
