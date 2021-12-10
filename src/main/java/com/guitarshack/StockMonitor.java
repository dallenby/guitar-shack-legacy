package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

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
        if(product.getStock() - quantity <= getReorderThreshold(product, new ReorderThreshold(calendar, salesHistory)))
            alert.send(product);
    }

    private int getReorderThreshold(Product product, ReorderThreshold reorderThreshold) {
        reorderThreshold.getCalendar().add(Calendar.YEAR, -1);
        Date startDate = reorderThreshold.getCalendar().getTime();
        reorderThreshold.getCalendar().add(Calendar.DATE, 30);
        Date endDate = reorderThreshold.getCalendar().getTime();
        SalesTotal total = reorderThreshold.getSalesHistory().getSalesTotal(product, endDate, startDate);
        return (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
    }
}
