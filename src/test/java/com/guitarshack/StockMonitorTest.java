package com.guitarshack;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockMonitorTest {

    private final Calendar calendar = Calendar.getInstance();
    private final Alert alert = Mockito.mock(Alert.class);
    private final SalesHistory salesHistory = Mockito.mock(SalesHistory.class);

    @Test
    public void reorderAlertSent() {
        Request productRequest = Mockito.mock(Request.class);
        Request salesRequest = Mockito.mock(Request.class);
        when(productRequest.get(any())).thenReturn("{'stock':27,'id':811,'leadTime':14}");
        when(salesRequest.get(any())).thenReturn("{'total':30}");
        StockMonitor stockMonitor = new StockMonitor(alert, productRequest, new SalesHistory(salesRequest), calendar);
        stockMonitor.productSold(811, 27);
        verify(alert).send(any());
    }

    @Test
    public void startDateIsOneYearInThePast() {
        Request productRequest = Mockito.mock(Request.class);
        when(productRequest.get(any())).thenReturn("{'stock':27,'id':811,'leadTime':14}");
        when(salesHistory.getSalesTotal(any(), any(), any())).thenReturn(new SalesTotal());
        calendar.set(2021, Calendar.DECEMBER, 1);
        StockMonitor stockMonitor = new StockMonitor(alert, productRequest, salesHistory, calendar);
        stockMonitor.productSold(811, 27);
        calendar.set(2020, Calendar.DECEMBER, 1);
        verify(salesHistory).getSalesTotal(any(), any(), eq(calendar.getTime()));
    }
}