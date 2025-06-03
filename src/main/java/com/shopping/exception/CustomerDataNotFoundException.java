package com.shopping.exception;

public class CustomerDataNotFoundException extends RuntimeException{

    public CustomerDataNotFoundException(String message){
        super(message);
    }

}
