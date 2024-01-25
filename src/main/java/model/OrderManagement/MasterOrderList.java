/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.OrderManagement;

import java.util.ArrayList;

import model.CustomerManagement.CustomerProfile;
import model.ProductManagement.Product;
import model.ProductManagement.SolutionOffer;
import model.SalesManagement.SalesPersonProfile;

/**
 * @author kal bugrara
 */
public class MasterOrderList {
    ArrayList<Order> orders;
    MasterOrderReport masterOrderReport;

    public MasterOrderList() {
        orders = new ArrayList<Order>();

    }

    public Order newOrder(CustomerProfile cp) {
        Order o = new Order(cp);
        orders.add(o);
        return o;

    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * @param cp CustomerProfile
     * @param so SolutionOffer
     * @return Orders
     */
    public Order newSolutionOrder(CustomerProfile cp, SolutionOffer so) {
        Order o = new Order(cp);//new order for customer

        //SolutionOrder different from order it has market channel assigment
        o.setMca(so.getMarketChannelComb());//set market channel assigment
        o.setOffer(so);//set offer

        //add new product to order
        for (Product product : so.getProducts()) {
            int price = product.getCeilingPrice() * so.getMarketChannelComb().getDiscountPercent() / 100;
            o.newOrderItem(product, price, 1);//new order item with price and number:1
        }
        orders.add(o);
        return o;
    }

    public Order newOrder(CustomerProfile cp, SalesPersonProfile spp) {
        Order o = new Order(cp, spp);
        orders.add(o);

        return o;
    }

    public MasterOrderReport generateMasterOrderReport() {
        masterOrderReport = new MasterOrderReport();
        masterOrderReport.generateOrderReport(orders);

        return masterOrderReport;
    }

    public int getSalesVolume() {

        int sum = 0;
        for (Order order : orders) {
            sum = sum + order.getOrderTotal();
        }
        return sum;
    }

    public void printShortInfo() {
        System.out.println("Checking what's inside the master order list.");
        System.out.println("There are " + orders.size() + " order.");
    }

}
