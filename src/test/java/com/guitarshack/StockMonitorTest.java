package com.guitarshack;

import org.junit.Before;
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

    @Before
    public void setup() {
        Request productRequest = Mockito.mock(Request.class);
        when(productRequest.get(any())).thenReturn("{'stock':27,'id':811,'leadTime':14}");
        when(salesHistory.getSalesTotal(any(), any(), any())).thenReturn(new SalesTotal());
        calendar.set(2021, Calendar.DECEMBER, 1);
        StockMonitor stockMonitor = new StockMonitor(alert, productRequest, salesHistory, calendar);
        stockMonitor.productSold(811, 27);
    }

    @Test
    public void reorderAlertSent() {
        verify(alert).send(any());
    }

    @Test
    public void startDateIsOneYearInThePast() {
        calendar.set(2020, Calendar.DECEMBER, 1);
        verify(salesHistory).getSalesTotal(any(), any(), eq(calendar.getTime()));
    }

    @Test
    public void endDateIs30DaysAfterStartDate() {
        calendar.set(2020, Calendar.DECEMBER, 31);
        verify(salesHistory).getSalesTotal(any(), eq(calendar.getTime()), any());
    }
}