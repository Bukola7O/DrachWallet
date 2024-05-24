package com.example.drachwallet.service;

import com.example.drachwallet.dto.BankAccountDTO;
import com.example.drachwallet.exceptions.BankAccountException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.model.BankAccount;
import com.example.drachwallet.model.Wallet;

import java.util.List;
import java.util.Optional;

public interface BankAccountService {
    public Wallet addBankAccount(String key, BankAccountDTO bankAccountDTO) throws BankAccountException,CustomerException;

    public Wallet removeBankAccount(String key,BankAccountDTO bankAccountDTO) throws BankAccountException, CustomerException;

    public Optional<BankAccount> viewBankAccount(String key, Integer accountNo)throws BankAccountException, CustomerException;

    public List<BankAccount> viewAllBankAccounts(String key)throws BankAccountException, CustomerException;
}
