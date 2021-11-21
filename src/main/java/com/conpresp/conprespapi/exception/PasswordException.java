package com.conpresp.conprespapi.exception;

public class PasswordException extends Exception{
    public PasswordException() { super(); }

    public PasswordException(String errorMessage) { super(errorMessage); }

    public PasswordException(String errorMessage, Throwable error) { super(errorMessage, error); }
    }
