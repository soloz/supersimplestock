package com.jpmorgan.interviews.codes.exception;

import com.jpmorgan.interviews.codes.api.exception.SimpleStocksException;

/**
 * Exception implementation for Super Simple Stock
 * Created by sadebayo on 28/10/2015.
 */
public class SimpleStocksExceptionImpl extends SimpleStocksException {

    private String message = null;

public SimpleStocksExceptionImpl (String message){
    super(message);
    this.message = message;
}
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {

    }
}
