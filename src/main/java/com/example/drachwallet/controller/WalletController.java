package com.example.drachwallet.controller;

import com.example.drachwallet.exceptions.BankAccountException;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.Customer;
import com.example.drachwallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/mywallet")
public class WalletController {
    @Autowired
    public WalletService walletService;

    @PostMapping("/createaccount")
    public ResponseEntity<Customer> createAccount(@Valid @RequestBody Customer customer) throws CustomerException {

        return new ResponseEntity<Customer>(walletService.createCustomerAccount(customer), HttpStatus.CREATED);
    }


    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> showBalance(@RequestParam String key, @RequestParam String mobileNumber) throws CustomerException{

        return new ResponseEntity<BigDecimal>(walletService.showBalance(mobileNumber, key), HttpStatus.ACCEPTED);
    }


    @PostMapping("/updateaccount")
    public ResponseEntity<Customer> updateCustomerDetails(@Valid @RequestBody Customer customer, @RequestParam String key) throws CustomerException{

        return new ResponseEntity<Customer>(walletService.updateAccount(customer,key), HttpStatus.ACCEPTED);
    }


    @PostMapping("/deposit/wallet")
    public ResponseEntity<String> depositToWallet(@RequestParam Integer accountNo, @RequestParam BigDecimal amount, @RequestParam String key) throws BankAccountException, CustomerException, TransactionException, WalletException {

        return new ResponseEntity<String>(walletService.depositAmount(amount, accountNo,  key), HttpStatus.OK);
    }

    @PostMapping("/transfer/mobile")
    public ResponseEntity<String> fundTransfer(@RequestParam String mobile, @RequestParam String name, @RequestParam BigDecimal amount, @RequestParam String key) throws WalletException, CustomerException, TransactionException{

        return new ResponseEntity<String>(walletService.fundTransfer(mobile, name, amount, key), HttpStatus.OK);
    }
}
