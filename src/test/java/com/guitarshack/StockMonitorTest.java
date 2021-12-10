package com.guitarshack;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockMonitorTest {

    @Test
    public void reorderAlertSent() {
        Alert alert = Mockito.mock(Alert.class);
        Request productRequest = Mockito.mock(Request.class);
        Request salesRequest = Mockito.mock(Request.class);
        when(productRequest.get(any())).thenReturn("{'stock':27,'id':811,'leadTime':14}");
        when(salesRequest.get(any())).thenReturn("{'total':30}");
        StockMonitor stockMonitor = new StockMonitor(alert, productRequest, new SalesHistory(salesRequest));
        stockMonitor.productSold(811, 27);
        verify(alert).send(any());
    }

    @Test
    public void startDateIsOneYearInThePast(){
        Alert alert = Mockito.mock(Alert.class);
        Request productRequest = Mockito.mock(Request.class);
        when(productRequest.get(any())).thenReturn("{'stock':27,'id':811,'leadTime':14}");
        SalesHistory salesHistory = Mockito.mock(SalesHistory.class);
        when(salesHistory.getSalesTotal(any(), any(), any())).thenReturn(new SalesTotal());
        StockMonitor stockMonitor = new StockMonitor(alert, productRequest, salesHistory);
        stockMonitor.productSold(811, 27);
//        verify(salesHistory).getSalesTotal(any(), any(), eq())
    }
}