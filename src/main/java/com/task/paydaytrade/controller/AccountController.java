package com.task.paydaytrade.controller;

import com.task.paydaytrade.model.ResponseModel;
import com.task.paydaytrade.model.account.AccountRqModel;
import com.task.paydaytrade.model.account.AccountRsModel;
import com.task.paydaytrade.service.AccountService;
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
import static com.task.paydaytrade.utility.UrlConstant.DEPOSIT_CASH;
import static org.springframework.http.HttpStatus.OK;

@AllArgsConstructor
@RestController
@Validated
@Log4j2
public class AccountController {
    private final AccountService accountService;

    @PostMapping(DEPOSIT_CASH)
    public ResponseEntity<ResponseModel<AccountRsModel>> depositCash(@Valid @RequestBody AccountRqModel accountRqModel,
                                                                     @Parameter(hidden = true) Authentication auth) {
        var response = ResponseEntity.ok(ResponseModel.of(accountService
                .depositCash(((UserDetails) auth.getPrincipal()).getUsername(), accountRqModel), OK));

        log.info(RESPONSE_MSG, DEPOSIT_CASH, response);
        return response;
    }
}
