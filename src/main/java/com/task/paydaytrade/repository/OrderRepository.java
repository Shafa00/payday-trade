package com.task.paydaytrade.repository;

import com.task.paydaytrade.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByIsFilledFalse();
}
