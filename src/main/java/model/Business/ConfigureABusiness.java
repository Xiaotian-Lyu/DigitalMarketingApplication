/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Business;

import java.util.*;

import com.github.javafaker.Faker;

import model.CustomerManagement.ChannelCatalog;
import model.CustomerManagement.CustomerDirectory;
import model.CustomerManagement.CustomerProfile;
import model.CustomerManagement.MarketCatalog;
import model.MarketModel.*;
import model.OrderManagement.MasterOrderList;
import model.OrderManagement.Order;
import model.Personnel.Person;
import model.Personnel.PersonDirectory;
import model.ProductManagement.*;
import model.Supplier.Supplier;
import model.Supplier.SupplierDirectory;

/**
 * @author kal bugrara
 */
public class ConfigureABusiness {

    static int upperPriceLimit = 50;
    static int lowerPriceLimit = 10;
    static int range = 5;
    static int productMaxQuantity = 5;

    public static Business createABusinessAndLoadALotOfData(String name, int supplierCount, int productCount,
                                                            int customerCount, int offerCount,
                                                            int offerProductCount, int marketPerCustomer, int orderCount) {
        Business business = new Business(name);

        // Add Suppliers +
        loadSuppliers(business, supplierCount);

        // Add Products +
        loadProducts(business, productCount);

        // Add Customers
        loadCustomers(business, customerCount);

        // Add channels and markets
        loadChannelsAndMarkets(business, marketPerCustomer);

        // Add products to solution offers
        loadSolutionOffers(business, offerCount, offerProductCount);

        // Add solution offer orders
        loadSolutionOfferOrders(business, orderCount);

        loadAds(business);

        return business;
    }

    private static void loadAds(Business business) {
        AdsCatalog adsCatalog = business.getAdsCatalog();
        for (Market market : business.getMarketcatalog().getMarkets()) {
            for (MarketChannelAssignment mca : market.getChannels()) {
                Ads ads = adsCatalog.newAds(mca, getRandom(10, 20));
                mca.setAds(ads);
            }
        }
    }


    /**
     * add solution offer's orders
     *
     * @param b          Business name
     * @param orderCount total order counts
     */
    private static void loadSolutionOfferOrders(Business b, int orderCount) {
        CustomerDirectory customerDirectory = b.getCustomerDirectory();

        //traversal the customer directory
        for (int i = 0; i < orderCount; i++) {
            CustomerProfile customerProfile = customerDirectory.pickRandomCustomer();//pick a random customer
            ArrayList<Market> markets = customerProfile.getMarkets();

            if (markets.isEmpty()) continue;//if the customer's market is empty new one
            Market market = markets.get(new Random().nextInt(markets.size()));
            MarketChannelAssignment mca = market.getRandomMCA();// assign market and channel

            if (mca == null) continue;
            SolutionOffer so = b.getSolutionoffercatalog().getRandomSolutionOfferByMCA(mca);//add solution offer's orders
            b.getMasterOrderList().newSolutionOrder(customerProfile, so);
        }
    }

    /**
     * load SolutionOffer（bundle）
     * @param business business
     * @param offerCount        each Market Channel Assigment SolutionOffer number
     * @param offerProductCount each Offer's product number
     */
    private static void loadSolutionOffers(Business business, int offerCount, int offerProductCount) {
        SolutionOfferCatalog solutionoffercatalog = business.getSolutionoffercatalog();
        MarketCatalog marketcatalog = business.getMarketcatalog();
        SupplierDirectory supplierDirectory = business.getSupplierDirectory();

        // traverse each market add solution offer
        for (Market market : marketcatalog.getMarkets()) {
            ArrayList<Market> submarkets = market.getSubmarkets();
            if (submarkets.isEmpty()) {
                addSolutionOffers(offerCount, offerProductCount, market, supplierDirectory, solutionoffercatalog);
            } else {
                for (Market submarket : submarkets) {
                    addSolutionOffers(offerCount, offerProductCount, submarket, supplierDirectory, solutionoffercatalog);
                }
            }
        }
    }

    /**
     * add bundle
     * @param offerCount           each Market Channel Assigment's offer number
     * @param offerProductCount    each offer's product number
     * @param market market
     * @param supplierDirectory supplierDirectory
     * @param solutionoffercatalog solution offer catalog
     */
    private static void addSolutionOffers(int offerCount, int offerProductCount, Market market,
                                          SupplierDirectory supplierDirectory,
                                          SolutionOfferCatalog solutionoffercatalog) {
        //traversal the Market Channel Assignment
        for (MarketChannelAssignment mca : market.getChannels()) {
            for (int i = 0; i < offerCount; i++) {
                int price = 0;
                List<Product> products = new ArrayList<>();
                Set<Product> productSet = new HashSet<>();
                for (int j = 0; j < offerProductCount; j++) {

                    //random pick up product
                    Supplier supplier = supplierDirectory.pickRandomSupplier();
                    Product product = supplier.getProductCatalog().pickRandomProduct();

                    while (productSet.contains(product)) {
                        supplier = supplierDirectory.pickRandomSupplier();
                        product = supplier.getProductCatalog().pickRandomProduct();
                    }
                    productSet.add(product);
                    products.add(product);
                    price += product.getCeilingPrice();//add all Ceiling Price total
                }
                //add discount to new solution offer
                solutionoffercatalog.newSolutionOffer(mca, products, price * mca.getDiscountPercent() / 100);
            }
        }
    }

    public static void loadSuppliers(Business b, int supplierCount) {
        Faker faker = new Faker();

        SupplierDirectory supplierDirectory = b.getSupplierDirectory();
        for (int index = 1; index <= supplierCount; index++) {
            supplierDirectory.newSupplier(faker.company().name());
        }
    }

    static void loadProducts(Business b, int productCount) {
        SupplierDirectory supplierDirectory = b.getSupplierDirectory();

        for (Supplier supplier : supplierDirectory.getSupplierList()) {

            int randomProductNumber = getRandom(1, productCount);
            ProductCatalog productCatalog = supplier.getProductCatalog();

            for (int index = 1; index <= randomProductNumber; index++) {

                String productName = new Faker().food().dish();
                int randomFloor = getRandom(lowerPriceLimit, lowerPriceLimit + range);
                int randomCeiling = getRandom(upperPriceLimit - range, upperPriceLimit);
                int randomTarget = getRandom(randomFloor, randomCeiling);

                productCatalog.newProduct(productName, randomFloor, randomCeiling, randomTarget);
            }
        }
    }

    /**
     * Add channels and markets to business
     * and assign markets to each customer
     */
    static void loadChannelsAndMarkets(Business b, int marketPerCustomer) {
        ChannelCatalog channelcatalog = b.getChannelcatalog();
        MarketCatalog marketcatalog = b.getMarketcatalog();

        // for each combination of market and channel
        // creat a MarketChannelAssignment，and set a random discount，for the bundle price discount
        for (Channel channel : channelcatalog.getChannels()) {
            for (Market market : marketcatalog.getMarkets()) {
                int discount = getRandom(80, 90);//get a random discount
                int addsBudget = getRandom(10000, 20000);//get a random adds budget
                MarketChannelAssignment mca = new MarketChannelAssignment(market, channel, discount, addsBudget);
                market.addMarketChannelAssignment(mca);
            }
        }
        // for each customer assign market
        for (CustomerProfile customerProfile : b.getCustomerDirectory().getCustomerlist()) {
            Set<Market> marketSet = new HashSet<>();
            for (int j = 0; j < marketPerCustomer; j++) {
                // pick up a random market
                Market market = marketcatalog.pickRandomMarket();
                while (marketSet.contains(market)) {
                    market = marketcatalog.pickRandomMarket();
                }
                marketSet.add(market);
                customerProfile.addMarket(market);
            }
        }
    }

    static int getRandom(int lower, int upper) {
        Random r = new Random();

        // nextInt(n) will return a number from zero to 'n'. Therefore e.g. if I want
        // numbers from 10 to 15
        // I will have result = 10 + nextInt(5)
        int randomInt = lower + r.nextInt(upper + 1 - lower);
        return randomInt;
    }

    static void loadCustomers(Business b, int customerCount) {
        CustomerDirectory customerDirectory = b.getCustomerDirectory();
        PersonDirectory personDirectory = b.getPersonDirectory();

        Faker faker = new Faker();

        for (int index = 1; index <= customerCount; index++) {
            Person newPerson = personDirectory.newPerson(String.valueOf(index), faker.name().fullName());
            customerDirectory.newCustomerProfile(newPerson);
        }
    }

    static void loadOrders(Business b, int orderCount, int itemCount) {

        // reach out to masterOrderList
        MasterOrderList mol = b.getMasterOrderList();

        // pick a random customer (reach to customer directory)
        CustomerDirectory cd = b.getCustomerDirectory();
        SupplierDirectory sd = b.getSupplierDirectory();

        for (int index = 0; index < orderCount; index++) {

            CustomerProfile randomCustomer = cd.pickRandomCustomer();
            if (randomCustomer == null) {
                System.out.println("Cannot generate orders. No customers in the customer directory.");
                return;
            }

            // create an order for that customer
            Order randomOrder = mol.newOrder(randomCustomer);

            // add order items
            // -- pick a supplier first (randomly)
            // -- pick a product (randomly)
            // -- actual price, quantity

            int randomItemCount = getRandom(1, itemCount);
            for (int itemIndex = 0; itemIndex < randomItemCount; itemIndex++) {

                Supplier randomSupplier = sd.pickRandomSupplier();
                if (randomSupplier == null) {
                    System.out.println("Cannot generate orders. No supplier in the supplier directory.");
                    return;
                }
                ProductCatalog pc = randomSupplier.getProductCatalog();
                Product randomProduct = pc.pickRandomProduct();
                if (randomProduct == null) {
                    System.out.println("Cannot generate orders. No products in the product catalog.");
                    return;
                }

                int randomPrice = getRandom(randomProduct.getFloorPrice(), randomProduct.getCeilingPrice());
                int randomQuantity = getRandom(1, productMaxQuantity);

                randomOrder.newOrderItem(randomProduct, randomPrice, randomQuantity);
            }
        }
        // Make sure order items are connected to the order

    }

}

