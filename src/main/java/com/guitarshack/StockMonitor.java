package com.guitarshack;

public class StockMonitor {
    private final Alert alert;
    private final Warehouse warehouse;
    private final ReorderThreshold reorderThreshold;

    public StockMonitor(Alert alert, Warehouse warehouse, ReorderThreshold reorderThreshold) {
        this.alert = alert;
        this.warehouse = warehouse;
        this.reorderThreshold = reorderThreshold;
    }

    public void productSold(int productId, int quantity) {
        Product product = warehouse.getProduct(productId);
        if(product.getStock() - quantity <= reorderThreshold.ofProduct(product))
            alert.send(product);
    }
}
