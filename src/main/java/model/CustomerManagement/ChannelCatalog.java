/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.CustomerManagement;

import model.MarketModel.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kal bugrara
 */
public class ChannelCatalog {
    List<Channel> channels;

    public ChannelCatalog() {
        this.channels = new ArrayList<>();
        for (String s : channelType) {
            newChannel(s);
        }
    }

    //Channel Type
    private static final String[] channelType = {"Traditional Retail Stores", "Online Shopping Platforms",
            "Subscription Services", "Direct from Producers", "Restaurants and Dining Establishments",};


    public Channel newChannel(String type) {
        Channel channel = new Channel(type);
        this.channels.add(channel);
        return channel;
    }

    public List<Channel> getChannels() {
        return channels;
    }
}
