package com.guitarshack;

import java.util.Calendar;
import java.util.Date;

public class ReorderThreshold {
    private final Calendar calendar;
    private final SalesHistory salesHistory;

    public ReorderThreshold(Calendar calendar, SalesHistory salesHistory) {
        this.calendar = calendar;
        this.salesHistory = salesHistory;
    }

    int getReorderThreshold(Product product) {
        calendar.add(Calendar.YEAR, -1);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DATE, 30);
        Date endDate = calendar.getTime();
        SalesTotal total = salesHistory.getSalesTotal(product, endDate, startDate);
        return (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
    }
}
