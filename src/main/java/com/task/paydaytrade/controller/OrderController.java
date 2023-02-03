package com.task.paydaytrade.controller;

import com.task.paydaytrade.model.ResponseModel;
import com.task.paydaytrade.model.order.OrderRqModel;
import com.task.paydaytrade.model.order.OrderRsModel;
import com.task.paydaytrade.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.task.paydaytrade.utility.MessageConstant.RESPONSE_MSG;
import static com.task.paydaytrade.utility.UrlConstant.PLACE_ORDER;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@Validated
@Log4j2
public class OrderController {
    private final OrderService orderService;

    @PostMapping(PLACE_ORDER)
    public ResponseEntity<ResponseModel<OrderRsModel>> placeOrder(@Valid @RequestBody OrderRqModel orderRqModel,
                                                                  @Parameter(hidden = true) Authentication auth) {
        var response = ResponseEntity.ok(ResponseModel.of(orderService
                .placeOrder(((UserDetails) auth.getPrincipal()).getUsername(), orderRqModel), OK));

        log.info(RESPONSE_MSG, PLACE_ORDER, response);
        return response;
    }
}
