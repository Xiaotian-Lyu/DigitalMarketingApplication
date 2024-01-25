/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;

import model.MarketModel.MarketChannelAssignment;

/**
 *
 * @author kal bugrara
 */
public class SolutionOffer {
    ArrayList<Product> products;
    int price;// floor, ceiling, and target ideas
    MarketChannelAssignment marketChannelComb;
    int id;

    public SolutionOffer(MarketChannelAssignment m, int id) {
        marketChannelComb = m;
        this.id = id;
        products = new ArrayList<Product>();
    }

    public MarketChannelAssignment getMarketChannelComb() {
        return marketChannelComb;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public void setPrice(int p) {
        price = p;

    }

    public int getId() {
        return id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        //print solution offer
        StringBuilder ret = new StringBuilder();
        int originalPrice = 0;//calculate the original price
        ret.append("SolutionOffer { products=[ ");
        for (Product product : this.products) {
            originalPrice += product.getCeilingPrice();//the original price total
            ret.append(product.toString()).append(",");
        }
        ret.setLength(ret.length() - 1);
        ret.append(" ]\n");
        ret.append("   The original price is: $").append(originalPrice);

        //show the discount information
        ret.append("\n   After the discount:").append(100-this.marketChannelComb.getDiscountPercent()).append("% Off ");
        //show after discount price
        ret.append(" Now only Price: $").append(price).append(" }");
        System.out.println();
        return ret.toString();
    }

    public String getTheNameOfOrders(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : products) {
            stringBuilder.append(product.getName());
        }
        return stringBuilder.toString();
    }


}
