/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import model.MarketModel.MarketChannelAssignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kal bugrara
 */
public class SolutionOfferCatalog {
    ArrayList<SolutionOffer> solutionOffers;

    int offerId = 1;

    public SolutionOfferCatalog() {
        this.solutionOffers = new ArrayList<>();
    }

    public SolutionOffer newSolutionOffer(MarketChannelAssignment mca, List<Product> products, int price) {
        SolutionOffer solutionOffer = new SolutionOffer(mca, offerId++);
        for (Product product : products) {
            solutionOffer.addProduct(product);
        }
        solutionOffer.setPrice(price);
        solutionOffers.add(solutionOffer);
        return solutionOffer;
    }

    /**
     * find Solution Offers By Market Channel Assignment
     */
    public List<SolutionOffer> findSolutionOffersByMarketChannelAssignment(MarketChannelAssignment mca) {
        List<SolutionOffer> offers = new ArrayList<>();
        for (SolutionOffer solutionOffer : this.solutionOffers) {
            if (solutionOffer.marketChannelComb.equals(mca)) {
                offers.add(solutionOffer);
            }
        }
        return offers;
    }

    /**
     * @param mca MarketChannelAssignment
     * @return SolutionOffer
     */
    public SolutionOffer getRandomSolutionOfferByMCA(MarketChannelAssignment mca) {
        List<SolutionOffer> offers = this.findSolutionOffersByMarketChannelAssignment(mca);
        if (offers.isEmpty()) return null;
        return offers.get(new Random().nextInt(offers.size()));
    }
}
