
package model.OrderManagement;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author kal bugrara
 */
public class MasterOrderReport {
    ArrayList<OrderSummary> orderSummaryList;

    public MasterOrderReport() {

        orderSummaryList = new ArrayList<OrderSummary>();

    }

    public void generateOrderReport(ArrayList<Order> orders) {
        ArrayList<Order> orderList = orders;
        OrderSummary orderSummary;

        OrderSummaryComparator comparator = new OrderSummaryComparator();

        for (Order order : orderList) {
            orderSummary = new OrderSummary(order);
            orderSummaryList.add(orderSummary);
        }
        Collections.sort(orderSummaryList, comparator);
    }

    public OrderSummary getTopProfitableOrder() { // most profitable order
        OrderSummary currentTopOrder = null;

        for (OrderSummary os : orderSummaryList) {
            if (currentTopOrder == null)
                currentTopOrder = os; // initial step
            else if (os.getOrderProfit() > currentTopOrder.getOrderProfit()) {
                currentTopOrder = os; // we have a new higher total
            }
        }
        return currentTopOrder;
    }

    public void printOrderReport() {
        System.out.println("Master Order Report");
        for (OrderSummary os : orderSummaryList) {
            int index = orderSummaryList.indexOf(os);
            System.out.print((index + 1) + " ");
            os.printOrderSummary();
        }

    }
}
