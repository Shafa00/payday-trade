package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.exception.DataNotFoundException;
import com.task.paydaytrade.model.account.AccountRqModel;
import com.task.paydaytrade.model.account.AccountRsModel;
import com.task.paydaytrade.repository.AccountRepository;
import com.task.paydaytrade.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountRsModel depositCash(String email, AccountRqModel accountRqModel) {
        var account = accountRepository.findByUserEmail(email).orElseThrow(() -> new DataNotFoundException(
                format("Account not found by given email: %s", email)));

        account.setAmount(account.getAmount().add(accountRqModel.amount()));
        accountRepository.save(account);

        return new AccountRsModel(account.getAccountNumber(), account.getAmount());
    }
}
