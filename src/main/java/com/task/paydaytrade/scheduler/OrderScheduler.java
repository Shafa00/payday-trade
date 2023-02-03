package com.task.paydaytrade.scheduler;

import com.task.paydaytrade.entity.Order;
import com.task.paydaytrade.entity.User;
import com.task.paydaytrade.exception.DataNotFoundException;
import com.task.paydaytrade.repository.AccountRepository;
import com.task.paydaytrade.repository.OrderRepository;
import com.task.paydaytrade.service.StockService;
import com.task.paydaytrade.utility.MailSenderService;
import com.task.paydaytrade.utility.OrderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

import java.util.List;

import static com.task.paydaytrade.utility.Constant.MAIL_BODY;
import static com.task.paydaytrade.utility.Constant.MAIL_SUBJECT;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
@Log4j2
public class OrderScheduler {
    private final OrderRepository orderRepository;
    private final StockService stockService;
    private final AccountRepository accountRepository;
    private final MailSenderService mailSenderService;

    @Scheduled(cron = "${scheduler.cron.fill-order}")
    @SchedulerLock(name = "OrderScheduler_fillOrder", lockAtLeastFor = "30s", lockAtMostFor = "50s")
    public void fillOrder() {
        log.info("OrderScheduler_fillOrder started");
        var orders = orderRepository.findAllByIsFilledFalse();
        orders.forEach(order -> {
            var user = order.getUser();
            var account = accountRepository.findByUserEmail(user.getEmail())
                    .orElseThrow(() -> new DataNotFoundException("Account not found"));

            stockService.getStockData().forEach(stockRsModel -> {
                if (order.getStock().equals(stockRsModel.symbol())) {
                    if (order.getOrderType().equals(OrderType.BUY)
                            && order.getPrice().compareTo(stockRsModel.lastPrice()) >= 0) {
                        account.setAmount(account.getAmount().subtract(stockRsModel.lastPrice()));
                        accountRepository.save(account);
                        completeOrder(orders, order, user);
                    }
                    if (order.getOrderType().equals(OrderType.SELL)
                            && order.getPrice().compareTo(stockRsModel.lastPrice()) <= 0) {
                        account.setAmount(account.getAmount().add(stockRsModel.lastPrice()));
                        accountRepository.save(account);
                        completeOrder(orders, order, user);
                    }
                }
            });
        });
        log.info("OrderScheduler_fillOrder ended");
    }

    private void completeOrder(List<Order> orders, Order order, User user) {
        order.setIsFilled(Boolean.TRUE);
        orderRepository.saveAll(orders);

        try {
            mailSenderService.sendEmail(user.getEmail(), MAIL_SUBJECT,
                    format(MAIL_BODY, order.getStock()));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}