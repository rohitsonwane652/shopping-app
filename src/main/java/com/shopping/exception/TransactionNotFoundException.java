package com.shopping.exception;

import java.io.Serializable;

public class TransactionNotFoundException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(String message){
        super(message);
    }
}
