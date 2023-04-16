package com.wusly.backendmenu.error;

public class WrongUserNamePasswordException extends RuntimeException{
    public WrongUserNamePasswordException(String message) {
        super(message);
    }
}
