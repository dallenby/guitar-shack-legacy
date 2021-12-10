package com.guitarshack;

import java.util.Calendar;

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
}
