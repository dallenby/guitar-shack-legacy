package com.guitarshack;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StockMonitor {
    private final Alert alert;
    private final Request productRequest;
    private final Request salesRequest;

    public StockMonitor(Alert alert, Request productRequest, Request salesRequest) {
        this.alert = alert;
        this.productRequest = productRequest;
        this.salesRequest = salesRequest;
    }

    public void productSold(int productId, int quantity) {
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String result = productRequest.get(params);
        Product product = new Gson().fromJson(result, Product.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Calendar.getInstance().getTime());
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE, -30);
        Date startDate = calendar.getTime();
        SalesTotal total = getSalesTotal(product, endDate, startDate, new SalesHistory(salesRequest));
        if(product.getStock() - quantity <= (int) ((double) (total.getTotal() / 30) * product.getLeadTime()))
            alert.send(product);
    }

    private SalesTotal getSalesTotal(Product product, Date endDate, Date startDate, SalesHistory salesHistory) {
        DateFormat format = new SimpleDateFormat("M/d/yyyy");
        Map<String, Object> params1 = new HashMap<>(){{
            put("productId", product.getId());
            put("startDate", format.format(startDate));
            put("endDate", format.format(endDate));
            put("action", "total");
        }};
        String result1 = salesHistory.getSalesRequest().get(params1);
        return new Gson().fromJson(result1, SalesTotal.class);
    }
}
