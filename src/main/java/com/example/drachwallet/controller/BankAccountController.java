package com.example.drachwallet.controller;

import com.example.drachwallet.dto.BankAccountDTO;
import com.example.drachwallet.exceptions.BankAccountException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.model.BankAccount;
import com.example.drachwallet.model.Wallet;
import com.example.drachwallet.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/bankaccount")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;


    @PostMapping("/add")
    public ResponseEntity<String> addAccountMapping(@RequestParam String key,@Valid @RequestBody BankAccountDTO bankAccountDTO) throws BankAccountException, CustomerException{

        bankAccountService.addBankAccount(key, bankAccountDTO);

        return new ResponseEntity<String>("Bank Account Added Successfully",HttpStatus.CREATED);

    }


    @DeleteMapping("/delete")
    public ResponseEntity<Wallet> removeAccountMapping(@RequestParam String key,@Valid @RequestBody BankAccountDTO bankAccount) throws BankAccountException, CustomerException{

        return new ResponseEntity<>(bankAccountService.removeBankAccount(key, bankAccount),HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<Optional<BankAccount>> getBankAccountDetailsMapping(@RequestParam String key, @RequestParam Integer accountNo) throws BankAccountException, CustomerException{

        return new ResponseEntity<Optional<BankAccount>>(bankAccountService.viewBankAccount(key, accountNo),HttpStatus.OK);

    }


    @GetMapping("/all")
    public ResponseEntity<List<BankAccount>> getAllBankAccountMapping(@RequestParam String key) throws BankAccountException, CustomerException{

        return new ResponseEntity<List<BankAccount>>(bankAccountService.viewAllBankAccounts(key),HttpStatus.FOUND);

    }

}
