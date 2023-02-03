package com.task.paydaytrade.entity;

import com.task.paydaytrade.utility.OrderType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "app_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "external_id")
    String externalId;

    @Column(name = "stock")
    String stock;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "is_filled")
    Boolean isFilled;

    @Column(name = "order_type")
    @Enumerated(value = EnumType.STRING)
    OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
