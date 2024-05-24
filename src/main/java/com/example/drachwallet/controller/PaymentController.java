package com.example.drachwallet.controller;

import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.PaymentException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    /*--------------------------------------------   Add Bill Payment Mapping -------------------------------------------------*/
    @PostMapping("/addBillPayment")
    public ResponseEntity<String> addPayment(@RequestParam("targetMobile") String targetMobile, @RequestParam("Name") String Name, @RequestParam("amount") double amount, @RequestParam("BillType") String BillType, @RequestParam("key") String key) throws PaymentException, WalletException, CustomerException, TransactionException {

        LocalDate date=LocalDate.now();
        String output = paymentService.addPayment(targetMobile, Name, amount, BillType,date , 0, key);

        return new ResponseEntity<String>(output, HttpStatus.OK);
    }
}
