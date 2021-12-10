package com.guitarshack;

import java.util.Calendar;

public class Program {

    private static StockMonitor monitor;

    static {
        Request salesRequest = new Request("https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales");
        monitor = new StockMonitor(product -> {
            // We are faking this for now
            System.out.println(
                    "You need to reorder product " + product.getId() +
                            ". Only " + product.getStock() + " remaining in stock");
        }, new Request("https://6hr1390c1j.execute-api.us-east-2.amazonaws.com/default/product"), new SalesHistory(salesRequest), Calendar.getInstance());
    }

    public static void main(String[] args) {
        int productId = Integer.parseInt(args[0]);
        int quantity = Integer.parseInt(args[1]);

        monitor.productSold(productId, quantity);
    }
}
