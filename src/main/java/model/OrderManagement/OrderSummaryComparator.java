package model.OrderManagement;

import java.util.Comparator;

public class OrderSummaryComparator implements Comparator<OrderSummary> {

    @Override
    public int compare(OrderSummary o1, OrderSummary o2) {
        
        if (Integer.compare(o2.numberOfItems, o1.numberOfItems) != 0) return Integer.compare(o2.numberOfItems, o1.numberOfItems);

        return Integer.compare(o2.salesvolume, o1.salesvolume);
    }
    
}
