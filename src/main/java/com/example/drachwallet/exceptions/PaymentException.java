package com.example.drachwallet.exceptions;

public class PaymentException extends Exception {
    private static final long serialVersionUID = 1L;

    public PaymentException() {
        super();
    }

    public PaymentException(String message) {
        super(message);
    }
}
