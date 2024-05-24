package com.example.drachwallet.serviceImpl;

import com.example.drachwallet.dto.BankAccountDTO;
import com.example.drachwallet.exceptions.BankAccountException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.model.BankAccount;
import com.example.drachwallet.model.CurrentUserSession;
import com.example.drachwallet.model.Wallet;
import com.example.drachwallet.repositories.BankAccountRepository;
import com.example.drachwallet.repositories.CurrentUserSessionRepository;
import com.example.drachwallet.repositories.WalletRepository;
import com.example.drachwallet.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Override
    public Wallet addBankAccount(String key, BankAccountDTO bankAccountDTO)  throws BankAccountException,CustomerException{

        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<BankAccount> optional = bankAccountRepository.findById(bankAccountDTO.getAccountNo());
        if(optional.isEmpty()){

            Wallet wallet =  walletRepository.showCustomerWalletDetails(currentUserSession.getUserId());

            BankAccount createBankAccount = new BankAccount(bankAccountDTO.getAccountNo(), bankAccountDTO.getBVN(), bankAccountDTO.getBankName(), bankAccountDTO.getBalance());
            createBankAccount.setWallet(wallet);

            bankAccountRepository.save(createBankAccount);

            return wallet;
        }
        throw new BankAccountException("Bank Account already exist With Given AccountNumber... Try Different");


    }


    /*---------------------------------------   Remove Customer Bank Account  ----------------------------------------*/
    @Override
    public Wallet removeBankAccount(String key, BankAccountDTO bankAccountDTO) throws BankAccountException, CustomerException{

        CurrentUserSession currUserSession = currentUserSessionRepository.findByUuid(key);
        if(currUserSession==null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        Optional<BankAccount> optional = bankAccountRepository.findById(bankAccountDTO.getAccountNo());
        if(optional.isPresent()) {

            bankAccountRepository.delete(optional.get());
            Wallet wallet = optional.get().getWallet();

            return wallet;

        }
        throw new BankAccountException("No Bank Account exist");

    }

    @Override
    public Optional<BankAccount> viewBankAccount(String key, Integer accountNo) throws BankAccountException, CustomerException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }


        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountNo);
        if(bankAccount == null) {
            throw new BankAccountException("No Bank Account exist");
        }
        return bankAccount;
    }

    @Override
    public List<BankAccount> viewAllBankAccounts(String key) throws BankAccountException, CustomerException {
        CurrentUserSession currentUserSession = currentUserSessionRepository.findByUuid(key);
        if(currentUserSession == null) {
            throw new CustomerException("No Customer LoggedIn");
        }

        List<BankAccount> bankAccounts = bankAccountRepository.findAllByWallet(walletRepository.showCustomerWalletDetails(currentUserSession.getUserId()).getWalletId());
        if(bankAccounts == null) {
            throw new BankAccountException("No Bank Account exist");
        }
        return bankAccounts;
    }
}

