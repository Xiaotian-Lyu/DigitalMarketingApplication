/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.CustomerManagement.CustomerProfile;
import model.MarketModel.Ads;
import model.MarketModel.Channel;
import model.MarketModel.Market;
import model.MarketModel.MarketChannelAssignment;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.OrderManagement.SolutionOrderReport;
import model.MarketModel.Ads;
import model.ProductManagement.SolutionOffer;

/**
 * @author kal bugrara
 */
public class DigitalMarketingApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Create Data
        Business business = ConfigureABusiness.createABusinessAndLoadALotOfData("Food-Chose", 50,
                10, 30, 5, 5, 1, 300);
        Scanner sc = new Scanner(System.in);

        boolean exitCode = false;

        while (!exitCode) {
            System.out.println();
            System.out.println("\u001b[36m+++++++++  Welcome to Xiaotian's final project. +++++++++++++");
            System.out.println("Please pick an part: ");
            System.out.println("\u001b[0m");
            System.out.println("========================== Welcome ==========================");
            System.out.println("1. Part1-Log in and search for products and services.");
            System.out.println("2. Part2-Reports of sales revenues.");
            System.out.println("3. Part3-Check the history orders and Change the market classification.");
            System.out.println("4. Exit");

            String input = sc.next();

            if (input.equals("1")) {
                part1(business, sc);
            }

            if (input.equals("2"))
                part2(business);

            if (input.equals("3")) {
                customerInfo(business, sc);
            }

            if (input.equals("4"))
                exitCode = true;
        }

        System.out.println("Thank you, have a nice day.");
        sc.close();
    }


    //Project Part-1
    private static void part1(Business business, Scanner sc) {
        System.out.println();
        //Log in and search for products and services.
        System.out.println("=========================== Log in ===========================");
        System.out.println("Welcome to customer service. Please enter your customer id: ");

        // get the customer's id，find out the customer
        CustomerProfile customerProfile = business.getCustomerDirectory().findCustomer(sc.next());
        if (customerProfile == null) {
            System.out.println("Cannot find your customer profile, exit");
            return;
        }

        while (true) {
            System.out.println();
            System.out.println("==================== Channel Information =====================");
            System.out.println("Please select the channel you are interested in: ");
            // print channel information
            List<Channel> channels = business.getChannelcatalog().getChannels();
            for (int i = 1; i <= channels.size(); i++) {
                System.out.println(i + ". " + channels.get(i - 1).getChannelType());
            }

            // get the index input from the customer ，get the channel
            String input = sc.next();
            int selectIndex = Integer.parseInt(input) - 1;
            Channel selectedChannel = channels.get(selectIndex);

            // get the customer's markets，create a string builder，and print
            ArrayList<Market> markets = customerProfile.getMarkets();
            System.out.println();
            System.out.println("====================== Market Information ===================");
            System.out.println("System identifies you as the following market: ");

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < markets.size(); i++) {
                builder.append(markets.get(i).getCharacteristics().get(0));
                if (i == markets.size() - 1) {
                    builder.append(".");
                } else {
                    builder.append(", ");
                }
            }
            System.out.println(builder);
            System.out.println();

            System.out.println("So we recommend these bundles for you. ");
            System.out.println("Select the number if you want to make an order, or enter 0 to return back.");
            System.out.println();
            System.out.println("====================== Solution Offer =======================");

            // get the all market of customer,
            // the customer pick a channel
            // print the channel's  all solution offer in MCA(market channel assignment)
            int index = 1;
            List<SolutionOffer> solutionOffers = new ArrayList<>();
            List<Ads> adsList = new ArrayList<>();
            // traverse each market
            for (Market market : markets) {
                // find each market with the picked channel MCA
                for (MarketChannelAssignment marketChannelAssignment : market.findChannel(selectedChannel)) {
                    // find out each MCA's solution Offer
                    solutionOffers.addAll(business.getSolutionoffercatalog().findSolutionOffersByMarketChannelAssignment(marketChannelAssignment));
                    adsList.add(marketChannelAssignment.getAds());
                }
            }
            for (Ads ads : adsList) {
                ads.printAds();
            }
            // print all solution offer
            for (SolutionOffer solutionOffer : solutionOffers) {
                System.out.println(index + ". " + solutionOffer);
                index++;
            }
            // customer put in
            input = sc.next();
            selectIndex = Integer.parseInt(input) - 1;
            if (selectIndex == -1) {
                break;
            }
            //customer chose the solution offer，creat the solution order
            SolutionOffer selectedOffer = solutionOffers.get(selectIndex);
            MasterOrderList masterOrderList = business.getMasterOrderList();
            masterOrderList.newSolutionOrder(customerProfile, selectedOffer);

            //Log out
            System.out.println("Thanks for your order! Enter 0 to log out or 1 to continue");
            if (sc.next().equals("0")) {
                break;
            }
        }
    }

    //Part-2
    private static void part2(Business business) {

        SolutionOrderReport report = new SolutionOrderReport(business.getMasterOrderList().getOrders());
        System.out.println("\u001B[34mTotal Revenues: $" + report.getTotalRevenue());
        System.out.println("\u001b[0m");

        //Revenues By Each Market
        System.out.println("====================== Revenues By Market ========================");
        for (Market market : report.getRevenuesByMarket().keySet()) {
            System.out.println(market.getCharacteristics().get(0) + ": $" + report.getRevenuesByMarket().get(market));
        }
        System.out.println();

        //Find the max Revenues Of Market
        System.out.println("\u001B[34mThe Max Revenues Of Market is " + report.findTheMostRevenueOfMarket().getCharacteristics() + ": $" +
                report.getRevenuesByMarket().get(report.findTheMostRevenueOfMarket()));
        System.out.println("\u001b[0m");

        //Revenues By Each Channel
        System.out.println("====================== Revenues By Channel ========================");
        for (Channel channel : report.getRevenuesByChannel().keySet()) {
            System.out.println(channel.getChannelType() + ": $" + report.getRevenuesByChannel().get(channel));
        }
        System.out.println();

        //Find the Max Revenues of channel
        System.out.println("\u001B[34mThe Max Revenues Of Channel is [" + report.findTheMaxRevenueByChannel().getChannelType() + "] : $" +
                report.getRevenuesByChannel().get(report.findTheMaxRevenueByChannel()));
        System.out.println("\u001b[0m");

        //print the Revenues By Ads
        System.out.println();
        System.out.println("==================== Revenues By Ads =======================");
        for (Ads ads : report.getRevenuesByAds().keySet()) {
            System.out.println("Ads of Market " + ads.getMca().getMarket().getCharacteristics().get(0) +
                    " and Channel " + ads.getMca().getChannel().getChannelType() + ": $"
                    + report.getRevenuesByAds().get(ads));
            System.out.println();
        }

        //Revenues By Solution Offer
        System.out.println("================== Revenues By Solution Offer =====================");
        for (SolutionOffer solutionOffer : report.getRevenuesBySolutionOffer().keySet()) {
            System.out.println("Solution Offer #" + solutionOffer.getId() + ": $" + report.getRevenuesBySolutionOffer().get(solutionOffer));
            System.out.println("[" + solutionOffer.getTheNameOfOrders() + "]");
            System.out.println();
        }


    }

    /**
     * Part3 - creative ideas：
     * 1. customer check their own history orders
     * 2. customer could change the original market
     */
    private static void customerInfo(Business business, Scanner sc) {
        System.out.println();
        System.out.println("==================== Information Center ======================");
        System.out.println("Welcome to customer information center. Please enter your customer id: ");

        CustomerProfile customerProfile = business.getCustomerDirectory().findCustomer(sc.next());
        if (customerProfile == null) {
            System.out.println("Cannot find your customer profile, exit");
            return;
        }
        while (true) {
            System.out.println("\u001b[33m++++++++++++++++++++ Welcome, Customer #" + customerProfile.getCustomerId() + " ++++++++++++++++++++");
            System.out.println("1. View my orders");
            System.out.println("2. Change my market classification");
            System.out.println("3. Exit");
            System.out.println("\u001b[0m");
            String input = sc.next();
            if (input.equals("1")) {
                System.out.println("==================== History Orders ======================");

                //get history orders
                ArrayList<Order> orders = business.getMasterOrderList().getOrders();
                List<Order> customerOrders = new ArrayList<>();
                for (Order order : orders) {
                    if (order.getCustomerId().equals(customerProfile.getCustomerId())) {
                        customerOrders.add(order);
                    }
                }
                for (int i = 0; i < customerOrders.size(); i++) {
                    System.out.println(i + 1 + ". " + customerOrders.get(i));
                }
                System.out.println();

                //the function - change the original market
            } else if (input.equals("2")) {
                System.out.println("Please select the markets that describe you, separate different markets by commas: ");

                //customer choose the new markets
                for (int i = 0; i < business.getMarketcatalog().getMarkets().size(); i++) {
                    System.out.println(i + 1 + ". " + business.getMarketcatalog().getMarkets().get(i).getCharacteristics().get(0));
                }
                input = sc.next();
                String[] split = input.split(",");

                //create a new market list for the customer
                customerProfile.setMarkets(new ArrayList<>());
                for (String s : split) {
                    int index = Integer.parseInt(s) - 1;
                    //add new market into the customer profile
                    customerProfile.addMarket(business.getMarketcatalog().getMarkets().get(index));
                }
                ArrayList<Market> markets = customerProfile.getMarkets();


                System.out.println("====================== Set Successfully =======================");
                System.out.println("System identifies you as the following market: ");

                //creat a stringBuilder to print the new market
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < markets.size(); i++) {
                    builder.append(markets.get(i).getCharacteristics().get(0));
                    if (i == markets.size() - 1) {
                        builder.append(".");
                    } else {
                        builder.append(", ");
                    }
                }
                System.out.println(builder);
                System.out.println();
            } else {
                break;
            }
        }

    }
}
