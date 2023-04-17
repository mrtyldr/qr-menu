package com.wusly.backendmenu.error;

public class NotPermittedException extends RuntimeException{
    public NotPermittedException() {
    }

    public NotPermittedException(String message) {
        super(message);
    }
}
