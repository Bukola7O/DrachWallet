package com.example.drachwallet.service;

import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.PaymentException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;

import java.time.LocalDate;

public interface PaymentService {
    public String addPayment(String targetMobile, String Name, double amount, String BillType, LocalDate paymentDate, Integer walletId, String key) throws PaymentException, WalletException, CustomerException, TransactionException;
}
