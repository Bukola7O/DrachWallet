package com.example.drachwallet.serviceImpl;

import com.example.drachwallet.exceptions.BankAccountException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.*;
import com.example.drachwallet.repositories.*;
import com.example.drachwallet.service.TransactionService;
import com.example.drachwallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Override
    public Customer createCustomerAccount(Customer customer) throws CustomerException {
        List<Customer> customers = customerRepository.findCustomerByMobile(customer.getMobileNumber());

        if(customers.isEmpty()) {

            Wallet wallet = new Wallet();
            wallet.setBalance(BigDecimal.valueOf(0));

            wallet.setCustomer(customer);
            walletRepository.save(wallet);

            return customerRepository.save(customer);
        }
        throw new CustomerException("Duplicate Mobile Number [ Already Registered with different customer ] ");

    }

    @Override
    public BigDecimal showBalance(String mobile, String key) throws CustomerException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Integer userId = currentUserSession.getUserId();
        Wallet wallet = walletRepository.showCustomerWalletDetails(userId);

        return wallet.getBalance();
    }

    @Override
    public String fundTransfer(String name, String targetMobileNumber, BigDecimal amount, String key) throws WalletException, TransactionException, CustomerException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }


        Integer userId = currentUserSession.getUserId();
        Wallet wallet = walletRepository.showCustomerWalletDetails(userId);

        Beneficiary beneficiary = new Beneficiary(targetMobileNumber, name);

        List<Beneficiary> beneficiaries = beneficiaryRepository.findByWallet(wallet.getWalletId());

        if(!beneficiaries.contains(beneficiary)) beneficiaryRepository.save(beneficiary);


        List<Customer> customers =  customerRepository.findCustomerByMobile(targetMobileNumber);

        if(customers.isEmpty()) {
            throw new CustomerException("Customer with mobile number "+ targetMobileNumber +" does not exist");
        }

        Wallet targetWallet = walletRepository.showCustomerWalletDetails(customers.get(0).getCustomerId());

        if(wallet.getBalance().compareTo(amount)<0) throw new WalletException("Add more amount in wallet for transaction");

        targetWallet.setBalance(targetWallet.getBalance().add(amount));
        walletRepository.save(targetWallet);

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);


        Transaction transaction = new Transaction("Bank transfer", LocalDate.now(),  amount.doubleValue(),amount +" transferred to "+ targetMobileNumber);
        transaction.setWallet(wallet);

        transactionService.addTransaction(transaction);

        return "Fund Transferred successfully";
    }

    @Override
    public String depositAmount(BigDecimal amount, Integer accountNo, String key) throws BankAccountException, WalletException, CustomerException, TransactionException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Integer userId = currentUserSession.getUserId();
        Wallet wallet = walletRepository.showCustomerWalletDetails(userId);

        List<BankAccount> accounts = bankAccountRepository.findAllByWallet(wallet.getWalletId());

        if(accounts.isEmpty()) {
            throw new BankAccountException("Add bank account for transaction");
        }

        BankAccount bankAccount = null;

        for(BankAccount b : accounts) {
            if((b.getAccountNo().toString()).equals(accountNo.toString())) {
                bankAccount = b;
                break;
            }

        }

        if(bankAccount==null){
            throw new BankAccountException("Bank account number does not match the data of saved accounts");
        }

        if(bankAccount.getBalance() < amount.doubleValue()) {
            throw new BankAccountException("Insufficient balance in account");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());
        wallet.setBalance(wallet.getBalance().add(amount));

        bankAccountRepository.save(bankAccount);

        double value = amount.doubleValue();
        Transaction transaction = new Transaction("Bank transfer", LocalDate.now(), value,"transferred from bank "+bankAccount.getBankName()+" to wallet");
        transaction.setWallet(wallet);


        transactionService.addTransaction(transaction);


        return "Your bank account no "+ accountNo +" debited for "+ amount +" Naira " ;


    }

    @Override
    public Customer updateAccount(Customer customer, String key) throws CustomerException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<Customer> customer1 = customerRepository.findById(currentUserSession.getUserId());


        if(!customer1.isPresent()) {
            throw new CustomerException("Customer with given CustomerId not exist");
        }

        customer.setCustomerId(currentUserSession.getUserId());

        return customerRepository.save(customer);
    }

    @Override
    public String addMoney(Wallet wallet, Integer accountNo, BigDecimal amount, String key) throws WalletException, BankAccountException, CustomerException, TransactionException {
        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Integer userId = currUserSession.getUserId();
        wallet = walletRepository.showCustomerWalletDetails(userId);

        List<BankAccount> accounts = bankAccountRepository.findAllByWallet(wallet.getWalletId());

        if(accounts.isEmpty()) {
            throw new BankAccountException("Add bank account for transaction");
        }

        BankAccount bankAccount = null;

        for(BankAccount b: accounts) {
            if((b.getAccountNo().toString()).equals(accountNo.toString())) {
                bankAccount = b;
                break;
            }

        }

        if(bankAccount == null){
            throw new BankAccountException("Bank account number does not match the data of saved accounts");
        }

        if(bankAccount.getBalance() < amount.doubleValue()) {
            throw new BankAccountException("Insufficient balance in account");
        }

        bankAccount.setBalance(bankAccount.getBalance() - amount.doubleValue());
        wallet.setBalance(wallet.getBalance().add(amount));

        bankAccountRepository.save(bankAccount);

        double value = amount.doubleValue();
        Transaction transaction = new Transaction("Bank transfer", LocalDate.now(), value,"transferred from bank "+bankAccount.getBankName()+" to wallet");
        transaction.setWallet(wallet);


        transactionService.addTransaction(transaction);


        return "Your bank account no "+ accountNo +" debited for "+ amount +" Naira" ;

    }
}
