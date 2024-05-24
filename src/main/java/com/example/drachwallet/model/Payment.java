package com.example.drachwallet.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer billId;

    private Double amount;

    private String billType;

    private LocalDate PaymentDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Wallet wallet;


    public Payment(Double amount, String billType, LocalDate PaymentDate) {
        this.amount = amount;
        this.billType = billType;
        this.PaymentDate = PaymentDate;
    }
}
