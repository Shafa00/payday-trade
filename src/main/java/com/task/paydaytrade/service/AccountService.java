package com.task.paydaytrade.service;

import com.task.paydaytrade.model.account.AccountRqModel;
import com.task.paydaytrade.model.account.AccountRsModel;

public interface AccountService {
    AccountRsModel depositCash(String email, AccountRqModel accountRqModel);

}
