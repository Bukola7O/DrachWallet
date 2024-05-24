package com.example.drachwallet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer transactionId;

    private String transactionType;

    private LocalDate transactionDate;

    private double amount;

    private String Description;

}
