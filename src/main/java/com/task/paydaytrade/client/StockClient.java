package com.task.paydaytrade.client;

import com.task.paydaytrade.model.stock.StockRsModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "stock-client", url = "${client.stock.url}")
public interface StockClient {
    @GetMapping("/any")
    ResponseEntity<List<StockRsModel>> getStockData(@RequestHeader(name = "X-RapidAPI-Key") String rapidApiKey);
}
