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

    public Calendar getCalendar() {
        return calendar;
    }

    public SalesHistory getSalesHistory() {
        return salesHistory;
    }

    int getReorderThreshold(Product product) {
        getCalendar().add(Calendar.YEAR, -1);
        Date startDate = getCalendar().getTime();
        getCalendar().add(Calendar.DATE, 30);
        Date endDate = getCalendar().getTime();
        SalesTotal total = getSalesHistory().getSalesTotal(product, endDate, startDate);
        return (int) ((double) (total.getTotal() / 30) * product.getLeadTime());
    }
}
