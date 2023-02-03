package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.exception.DataNotFoundException;
import com.task.paydaytrade.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceImplTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void shouldDepositCashThrowException() {
        when(accountRepository.findByUserEmail("test@gmail.com")).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> accountService.depositCash("test@gmail.com", MockData.getAccountRqModel()));
        verify(accountRepository, times(1)).findByUserEmail(any());
    }

    @Test
    void shouldDepositCash() {
        when(accountRepository.findByUserEmail("test@gmail.com")).thenReturn(Optional.of(MockData.getAccount()));

        var accountRsModel= accountService.depositCash("test@gmail.com", MockData.getAccountRqModel());
        assertEquals(accountRsModel.amount(), MockData.getOrderRqModel().price());
        verify(accountRepository, times(1)).findByUserEmail(any());
        verify(accountRepository, times(1)).save(any());
    }
}