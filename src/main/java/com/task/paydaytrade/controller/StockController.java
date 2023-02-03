package com.task.paydaytrade.controller;

import com.task.paydaytrade.model.ResponseModel;
import com.task.paydaytrade.model.stock.StockRsModel;
import com.task.paydaytrade.service.StockService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.task.paydaytrade.utility.MessageConstant.RESPONSE_MSG;
import static com.task.paydaytrade.utility.UrlConstant.STOCKS_LIST;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@Validated
@Log4j2
public class StockController {
    private final StockService stockService;

    @GetMapping(STOCKS_LIST)
    public ResponseEntity<ResponseModel<List<StockRsModel>>> stocksList() {
        var response = ResponseEntity.ok(
                ResponseModel.of(stockService.getStockData(), OK));

        log.info(RESPONSE_MSG, STOCKS_LIST, response);
        return response;
    }
}
