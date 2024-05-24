package com.example.drachwallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;

    private BigDecimal balance;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_Id")
    private Customer customer;
}
