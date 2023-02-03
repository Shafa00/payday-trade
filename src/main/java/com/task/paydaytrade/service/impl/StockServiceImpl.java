package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.client.StockClient;
import com.task.paydaytrade.model.stock.StockRsModel;
import com.task.paydaytrade.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class StockServiceImpl implements StockService {
    @Value("${client.stock.secret-key}")
    private String rapidApiKey;

    private final StockClient stockClient;

    @Override
    public List<StockRsModel> getStockData() {
        var response = stockClient.getStockData(rapidApiKey).getBody();

        log.info("Stock client response: {}", response);
        return response;
    }
}
