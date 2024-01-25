/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketModel;

/**
 *
 * @author kal bugrara
 */
public class Channel {
    private final String channelType;
    private int price;
    private String unitOfMeasure;

    public Channel(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelType() {
        return channelType;
    }
}
