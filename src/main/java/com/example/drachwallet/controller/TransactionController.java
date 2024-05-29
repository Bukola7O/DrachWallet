package com.example.drachwallet.controller;

import com.example.drachwallet.dto.TransactionDTO;
import com.example.drachwallet.exceptions.CustomerException;
import com.example.drachwallet.exceptions.TransactionException;
import com.example.drachwallet.exceptions.WalletException;
import com.example.drachwallet.model.Transaction;
import com.example.drachwallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;


    @PostMapping("/wallet")
    public ResponseEntity<List<TransactionDTO>> viewByWallet(@RequestParam String key) throws TransactionException, WalletException, CustomerException{

        List<Transaction> transactions= transactionService.findByWallet(key);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for(Transaction t:transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(),t.getAmount(), t.getDescription() );
            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS,HttpStatus.OK);
    }


    @GetMapping("/transactionId")
    public ResponseEntity<TransactionDTO> findById(@RequestParam String key, @RequestParam Integer transactionId) throws TransactionException, CustomerException{

        Transaction t = transactionService.findByTransactionId(key,transactionId);
        TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(),t.getAmount(), t.getDescription() );


        return new ResponseEntity<TransactionDTO>(transactionDTO, HttpStatus.CREATED);
    }


    @GetMapping("/type")
    public ResponseEntity<List<TransactionDTO>> viewAllTransacationByType(@RequestParam String key, @RequestParam String type) throws TransactionException, CustomerException{

        List<Transaction> transactions = transactionService.findByTransactionType(key,type);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(Transaction t:transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(),t.getAmount(), t.getDescription() );

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.ACCEPTED);

    }


    @GetMapping("/between")
    public ResponseEntity<List<TransactionDTO>> viewByTwoDate(@RequestParam String key, @RequestParam("one") String one, @RequestParam("two")  String two) throws TransactionException, CustomerException{

        LocalDate firstDate= LocalDate.parse(one);
        LocalDate secondDate = LocalDate.parse(two);
        List<Transaction> transactions = transactionService.viewTransactionBetweenDate(key,firstDate, secondDate);

        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(Transaction t:transactions) {

            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(),t.getAmount(), t.getDescription() );

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.ACCEPTED);

    }


    @GetMapping("/all")
    public ResponseEntity<List<TransactionDTO>>  viewAllTransactionByAdmin() throws TransactionException{

        List<Transaction>  transactions = transactionService.viewAllTransaction();

        List<TransactionDTO> transactionDTOS = new ArrayList<>();

        for(Transaction t:transactions) {
            TransactionDTO transactionDTO = new TransactionDTO(t.getTransactionId(), t.getTransactionType(), t.getTransactionDate(),t.getAmount(), t.getDescription() );

            transactionDTOS.add(transactionDTO);
        }
        return new ResponseEntity<List<TransactionDTO>>(transactionDTOS, HttpStatus.OK);
    }


}

