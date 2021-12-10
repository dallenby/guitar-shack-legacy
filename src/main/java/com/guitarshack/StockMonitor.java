package com.guitarshack;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;
    private final Request productRequest;
    private final SalesHistory salesHistory;
    private final Calendar calendar;

    public StockMonitor(Alert alert, Request productRequest, SalesHistory salesHistory, Calendar calendar) {
        this.alert = alert;
        this.productRequest = productRequest;
        this.salesHistory = salesHistory;
        this.calendar = calendar;
    }

    public void productSold(int productId, int quantity) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String result = productRequest.get(params);
        Product product = new Gson().fromJson(result, Product.class);
        calendar.setTime(Calendar.getInstance().getTime());
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE, -30);
        Date startDate = calendar.getTime();
        SalesTotal total = salesHistory.getSalesTotal(product, endDate, startDate);
        if(product.getStock() - quantity <= (int) ((double) (total.getTotal() / 30) * product.getLeadTime()))
            alert.send(product);
    }

}
