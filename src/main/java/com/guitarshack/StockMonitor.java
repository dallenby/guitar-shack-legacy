package com.guitarshack;

import java.util.Calendar;

public class StockMonitor {
    private final Alert alert;
    private final SalesHistory salesHistory;
    private final Calendar calendar;
    private final Warehouse warehouse;
    private final ReorderThreshold reorderThreshold;

    public StockMonitor(Alert alert, SalesHistory salesHistory, Calendar calendar, Warehouse warehouse, ReorderThreshold reorderThreshold) {
        this.alert = alert;
        this.salesHistory = salesHistory;
        this.calendar = calendar;
        this.warehouse = warehouse;
        this.reorderThreshold = reorderThreshold;
    }

    public void productSold(int productId, int quantity) {
        Product product = warehouse.getProduct(productId);
        if(product.getStock() - quantity <= reorderThreshold.ofProduct(product))
            alert.send(product);
    }
}
