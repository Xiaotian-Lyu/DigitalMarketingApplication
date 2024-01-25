/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ProductManagement;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author kal bugrara
 */
public class ProductsReport {

    ArrayList<ProductSummary> productSummaryList;
    String sortingRule;

    public ProductsReport(String sortingRule) {
        productSummaryList = new ArrayList<ProductSummary>();
        this.sortingRule = sortingRule;
        /// We could make it "pre-populate" with the existing product data

    }

    public void addProductSummary(ProductSummary ps) {

        productSummaryList.add(ps);

        ProductSummaryComparator comparator = new ProductSummaryComparator(sortingRule);
        Collections.sort(productSummaryList, comparator);

    }

    public ProductSummary getTopProductAboveTarget() {
        ProductSummary currentTopProduct = null;

        for (ProductSummary ps : productSummaryList) {
            if (currentTopProduct == null) {
                currentTopProduct = ps; // initial step
            } else if (ps.getNumberAboveTarget() > currentTopProduct.getNumberAboveTarget()) {
                currentTopProduct = ps; // we have a new higher total
            }

        }
        return currentTopProduct;
    }

    public ArrayList<ProductSummary> getProductsAlwaysAboveTarget() {
        ArrayList<ProductSummary> productsAlwaysAboveTarget = new ArrayList<ProductSummary>(); // temp array list

        for (ProductSummary ps : productSummaryList) {
            if (ps.isProductAlwaysAboveTarget() == true) {
                productsAlwaysAboveTarget.add(ps);
            }
        }

        return productsAlwaysAboveTarget;
    }

    public void printProductReport() {
        System.out.println("Product Performance Report");
        System.out.println("Below are product name, actual sales and number of sales above target.");
        for (ProductSummary ps : productSummaryList) {
            int index = productSummaryList.indexOf(ps);
            System.out.print((index + 1) + " ");
            ps.printProductSummary();
        }

    }
}
