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

    public StockMonitor(Alert alert) {
        this.alert = alert;
    }

    public void productSold(int productId, int quantity) {
        String baseURL = "https://6hr1390c1j.execute-api.us-east-2.amazonaws.com/default/product";
        Map<String, Object> params = new HashMap<>() {{
            put("id", productId);
        }};
        String result = new Request(baseURL).get(params);
        Product product = new Gson().fromJson(result, Product.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Calendar.getInstance().getTime());
        Date endDate = calendar.getTime();
        calendar.add(Calendar.DATE, -30);
        Date startDate = calendar.getTime();
        DateFormat format = new SimpleDateFormat("M/d/yyyy");
        Map<String, Object> params1 = new HashMap<>(){{
            put("productId", product.getId());
            put("startDate", format.format(startDate));
            put("endDate", format.format(endDate));
            put("action", "total");
        }};
        String url = "https://gjtvhjg8e9.execute-api.us-east-2.amazonaws.com/default/sales";
        String result1 = new Request(url).get(params1);
        SalesTotal total = new Gson().fromJson(result1, SalesTotal.class);
        if(product.getStock() - quantity <= (int) ((double) (total.getTotal() / 30) * product.getLeadTime()))
            alert.send(product);
    }
}
