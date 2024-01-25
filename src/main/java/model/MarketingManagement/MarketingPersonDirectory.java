/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.MarketingManagement;

import java.util.ArrayList;

import model.Business.Business;
import model.Personnel.Person;

/**
 * @author kal bugrara
 */
public class MarketingPersonDirectory {

    Business business;
    ArrayList<MarketingPersonProfile> marketingPersonList;

    public MarketingPersonDirectory(Business d) {
        business = d;
        marketingPersonList = new ArrayList<MarketingPersonProfile>();
    }

    public MarketingPersonProfile newMarketingPersonProfile(Person p) {
        MarketingPersonProfile sp = new MarketingPersonProfile(p);
        marketingPersonList.add(sp);
        return sp;
    }

    public MarketingPersonProfile findMarketingPerson(String id) {

        for (MarketingPersonProfile sp : marketingPersonList) {

            if (sp.isMatch(id)) {
                return sp;
            }
        }
        return null; // not found after going through the whole list
    }

}
