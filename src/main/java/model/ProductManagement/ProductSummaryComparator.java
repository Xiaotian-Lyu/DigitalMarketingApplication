package model.ProductManagement;

import java.util.Comparator;

public class ProductSummaryComparator implements Comparator<ProductSummary> {

    String sortingRule;

    public ProductSummaryComparator(String sortingRule) {
        this.sortingRule = sortingRule;
    }


    @Override
    public int compare(ProductSummary o1, ProductSummary o2) {
        if (sortingRule.equals("Name")) {
            return o1.getSubjectproduct().getName().compareTo(o2.getSubjectproduct().getName());
        }

        return (-1) * Integer.compare(o1.getSalesRevenues(), o2.getSalesRevenues());
    }
    
}
