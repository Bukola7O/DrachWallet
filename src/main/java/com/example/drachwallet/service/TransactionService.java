package com.example.drachwallet.service;

import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    public Transaction addTransaction(Transaction transaction) throws TransactionException, WalletException;

    public List<Transaction> findByWallet(String key) throws TransactionException, WalletException, CustomerException;

    public Transaction findByTransactionId(String key, Integer transactionId)throws TransactionException, CustomerException;

    public List<Transaction> findByTransactionType(String key, String transactionType) throws TransactionException,CustomerException;

    public List<Transaction> viewTransactionBetweenDate(String key, LocalDate startDate, LocalDate endDate) throws TransactionException,CustomerException;

    public List<Transaction> viewAllTransaction()throws TransactionException;
}
