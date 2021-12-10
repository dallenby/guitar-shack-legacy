package com.guitarshack;

public class SalesHistory {
    private final Request salesRequest;

    public SalesHistory(Request salesRequest) {
        this.salesRequest = salesRequest;
    }

    public Request getSalesRequest() {
        return salesRequest;
    }
}
