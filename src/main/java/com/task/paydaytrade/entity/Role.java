package com.task.paydaytrade.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "external_id")
    String externalId;

    @Column(name = "role_name")
    String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    List<User> users;
}
