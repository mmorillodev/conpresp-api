package com.conpresp.conprespapi.exception;

public class NotEqualsException extends Exception{
    public NotEqualsException() { super(); }

    public NotEqualsException(String errorMessage) { super(errorMessage); }

    public NotEqualsException(String errorMessage, Throwable error) { super(errorMessage, error); }
    }
