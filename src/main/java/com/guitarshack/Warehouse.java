package com.guitarshack;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    private final Request productRequest;

    public Warehouse(Request productRequest) {
        this.productRequest = productRequest;
    }

    public Request getProductRequest() {
        return productRequest;
    }

    Product getProduct(int productId) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String result = getProductRequest().get(params);
        return new Gson().fromJson(result, Product.class);
    }
}
