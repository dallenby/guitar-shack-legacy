package com.guitarshack;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SalesHistory {
    private final Request salesRequest;

    public SalesHistory(Request salesRequest) {
        this.salesRequest = salesRequest;
    }

    public Request getSalesRequest() {
        return salesRequest;
    }

    SalesTotal getSalesTotal(Product product, Date endDate, Date startDate) {
        DateFormat format = new SimpleDateFormat("M/d/yyyy");
        Map<String, Object> params1 = new HashMap<>(){{
            put("productId", product.getId());
            put("startDate", format.format(startDate));
            put("endDate", format.format(endDate));
            put("action", "total");
        }};
        String result1 = getSalesRequest().get(params1);
        return new Gson().fromJson(result1, SalesTotal.class);
    }
}
