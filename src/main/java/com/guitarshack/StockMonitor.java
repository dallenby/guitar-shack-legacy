package com.guitarshack;

import java.util.Calendar;

public class StockMonitor {
    private final Alert alert;
    private final SalesHistory salesHistory;
    private final Calendar calendar;
    private final Warehouse warehouse;

    public StockMonitor(Alert alert, SalesHistory salesHistory, Calendar calendar, Warehouse warehouse) {
        this.alert = alert;
        this.salesHistory = salesHistory;
        this.calendar = calendar;
        this.warehouse = warehouse;
    }

    public void productSold(int productId, int quantity) {
        Product product = warehouse.getProduct(productId);
        if(product.getStock() - quantity <= new ReorderThreshold(calendar, salesHistory).getReorderThreshold(product))
            alert.send(product);
    }

}
