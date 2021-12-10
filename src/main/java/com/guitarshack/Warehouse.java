package com.guitarshack;

public class Warehouse {
    private final Request productRequest;

    public Warehouse(Request productRequest) {
        this.productRequest = productRequest;
    }

    public Request getProductRequest() {
        return productRequest;
    }
}
