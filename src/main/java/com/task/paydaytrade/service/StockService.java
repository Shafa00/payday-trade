package com.task.paydaytrade.service;

import com.task.paydaytrade.model.stock.StockRsModel;

import java.util.List;

public interface StockService {
    List<StockRsModel> getStockData();
}
