package com.example.drachwallet.serviceImpl;

import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.CurrentUserSession;
import com.example.drachwallet.model.Transaction;
import com.example.drachwallet.model.Wallet;
import com.example.drachwallet.repositories.CurrentUserSessionRepository;
import com.example.drachwallet.repositories.TransactionRepository;
import com.example.drachwallet.repositories.WalletRepository;
import com.example.drachwallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;
    @Override
    public Transaction addTransaction(Transaction transaction) throws TransactionException, WalletException {
        Optional<Wallet> wallet=   walletRepository.findById(transaction.getWallet().getWalletId());
        if(!wallet.isPresent())throw new WalletException("Wallet id worng.");
        if(transactionRepository.save(transaction) != null)return transaction;
        throw new TransactionException("Data is null");
    }

    @Override
    public List<Transaction> findByWallet(String key) throws TransactionException, WalletException, CustomerException {
        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Wallet wallet = walletRepository.showCustomerWalletDetails(currUserSession.getUserId());

        Optional<Wallet> optional = walletRepository.findById(wallet.getWalletId());
        if(!optional.isPresent()){
            throw new WalletException("Invalid walletId");
        }

        List<Transaction> transactions = transactionRepository.findByWallet(wallet.getWalletId());
        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions");
        }
        return transactions;
    }

    @Override
    public Transaction findByTransactionId(String key, Integer transactionId) throws TransactionException, CustomerException {
        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if(!transaction.isPresent()){
            throw new TransactionException("Invalid transactionId");
        }
        return transaction.get();

    }

    @Override
    public List<Transaction> findByTransactionType(String key, String transactionType) throws TransactionException, CustomerException {
        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        List<Transaction> transactions = transactionRepository.findByTransactionType(transactionType);
        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions");
        }
        return transactions;
    }

    @Override
    public List<Transaction> viewTransactionBetweenDate(String key, LocalDate startDate, LocalDate endDate) throws TransactionException, CustomerException {
        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        LocalDate localDate = LocalDate.now();
        if(startDate.isAfter(localDate)){
            throw new TransactionException("Invalid Start Date [ Future Date ]");
        }
        if(endDate.isAfter(localDate)){
            throw new TransactionException("Invalid End Date [ Future Date ]");
        }
        if(startDate.isAfter(endDate)) {
            throw new TransactionException("Invalid Start Date ");
        }

        List<Transaction> transactions= transactionRepository.findByTransactionDateBetween(startDate, endDate);
        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions");
        }
        return transactions;
    }

    @Override
    public List<Transaction> viewAllTransaction() throws TransactionException {
        List<Transaction> transactions = transactionRepository.findAll();

        if(transactions.isEmpty()){
            throw new TransactionException("No Transactions");
        }
        return transactions;
    }
}
