package com.example.drachwallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(LoginException loginException, WebRequest request){

        ErrorMessage err=new ErrorMessage(LocalDateTime.now(), loginException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(err, HttpStatus.BAD_REQUEST);

    }

    /* --------------------------------------   BankAccount Exception    ----------------------------------------------*/

    @ExceptionHandler(BankAccountException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(BankAccountException bankAccountException, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), bankAccountException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    /* --------------------------------------   Beneficiary Exception    ----------------------------------------------*/

    @ExceptionHandler(BeneficiaryException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(BeneficiaryException beneficiaryException , WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), beneficiaryException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    /* --------------------------------------   BillPayment Exception    ----------------------------------------------*/

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(PaymentException PaymentException, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), PaymentException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    /* --------------------------------------   Customer Exception    ----------------------------------------------*/

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(CustomerException customerException, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), customerException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /* --------------------------------------   BankAccount Exception    ----------------------------------------------*/

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(TransactionException transactionException, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), transactionException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    /* --------------------------------------   Wallet Exception    ----------------------------------------------*/

    @ExceptionHandler(WalletException.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(WalletException walletException, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), walletException.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }


    /* --------------------------------------   Exception    ----------------------------------------------*/

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> DrachwalletExceptionHandler(Exception exception, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), exception.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
