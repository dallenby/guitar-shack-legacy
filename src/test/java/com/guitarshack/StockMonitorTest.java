package com.guitarshack;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
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
        StockMonitor stockMonitor = new StockMonitor(alert, productRequest, salesRequest);
        stockMonitor.productSold(811, 27);
        verify(alert).send(any());
    }
}