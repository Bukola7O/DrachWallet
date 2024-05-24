package com.example.drachwallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;

    private String transactionType;

    private LocalDate transactionDate;

    private double amount;

    private String Description;


    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public Transaction(String transactionType, LocalDate transactionDate, double amount, String description) {
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.amount = amount;
        Description = description;
    }
}
