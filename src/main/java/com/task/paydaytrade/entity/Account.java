package com.task.paydaytrade.entity;

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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "external_id")
    String externalId;

    @Column(name = "account_number")
    String accountNumber;

    @Column(name = "amount")
    BigDecimal amount;

    @OneToOne(mappedBy = "account")
    User user;
}
