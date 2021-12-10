package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

public class StockMonitor {
    private final Alert alert;
    private final Request productRequest;
    private final SalesHistory salesHistory;
    private final Calendar calendar;
    private final Warehouse warehouse;

    public StockMonitor(Alert alert, Request productRequest, SalesHistory salesHistory, Calendar calendar) {
        this.alert = alert;
        this.productRequest = productRequest;
        this.salesHistory = salesHistory;
        this.calendar = calendar;
        warehouse = new Warehouse(productRequest);
    }

    public void productSold(int productId, int quantity) {
        Product product = warehouse.getProduct(productId);
        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 30);
        Date endDate = calendar.getTime();
        SalesTotal total = salesHistory.getSalesTotal(product, endDate, startDate);
        if(product.getStock() - quantity <= (int) ((double) (total.getTotal() / 30) * product.getLeadTime()))
            alert.send(product);
    }
}
