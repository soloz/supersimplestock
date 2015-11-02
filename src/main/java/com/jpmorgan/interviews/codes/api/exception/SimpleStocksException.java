package com.jpmorgan.interviews.codes.api.exception;

/**
 * Created by sadebayo on 28/10/2015.
 */
public abstract class SimpleStocksException extends Exception {
    private String message = null;

    public SimpleStocksException(String message){
        this.message = message;
    }

    public abstract String getMessage();
    public abstract void setMessage(String message);
}
