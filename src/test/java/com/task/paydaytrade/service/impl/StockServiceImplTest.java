package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.client.StockClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StockServiceImplTest {
    @Mock
    StockClient stockClient;

    @InjectMocks
    StockServiceImpl stockService;

    @Test
    void shouldGetStockData() {
        var response = ResponseEntity.ok(List.of(MockData.getStockRsModel()));
        when(stockClient.getStockData(any())).thenReturn(response);

        Assertions.assertEquals(stockService.getStockData(), response.getBody());
        verify(stockClient, times(1)).getStockData(any());
    }
}