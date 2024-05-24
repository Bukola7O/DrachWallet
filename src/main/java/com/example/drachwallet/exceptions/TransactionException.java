package com.example.drachwallet.exceptions;

public class TransactionException extends Exception {
    private static final long serialVersionUID = 1L;

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

}
