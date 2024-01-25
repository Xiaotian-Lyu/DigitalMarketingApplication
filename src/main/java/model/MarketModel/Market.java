/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

import java.util.ArrayList;
import java.util.Random;

import model.ProductManagement.SolutionOffer;

/**
 *
 * @author kal bugrara
 */
public class Market {
    ArrayList<SolutionOffer> so;
    ArrayList<MarketChannelAssignment> channels;
    ArrayList<String> characteristics;
    ArrayList<Market> submarkets;
    int size;
    String ads;

    public Market(String s) {
        characteristics = new ArrayList<String>();
        characteristics.add(s);
        so = new ArrayList<>();
        channels = new ArrayList<>();
        submarkets = new ArrayList<>();
    }

    public void addMarketChannelAssignment(MarketChannelAssignment mca) {
        this.channels.add(mca);
    }

    public ArrayList<MarketChannelAssignment> getChannels() {
        return channels;
    }

    /**
     * find out the market correspond channel's MCA（market channel assigment）
     */
    public ArrayList<MarketChannelAssignment> findChannel(Channel channel) {
        ArrayList<MarketChannelAssignment> res = new ArrayList<>();
        // for the market's MCA
        // only when the channel equal the input channel
        // that is the channel we want to find out
        for (MarketChannelAssignment marketChannelAssignment : this.channels) {
            if (marketChannelAssignment.channel.equals(channel)) {
                res.add(marketChannelAssignment);
            }
        }
        return res;
    }

    public MarketChannelAssignment getRandomMCA() {
        if (channels.isEmpty()) return null;
        return channels.get(new Random().nextInt(channels.size()));
    }

    public ArrayList<Market> getSubmarkets() {
        return submarkets;
    }

    public ArrayList<String> getCharacteristics() {
        return characteristics;
    }
}
