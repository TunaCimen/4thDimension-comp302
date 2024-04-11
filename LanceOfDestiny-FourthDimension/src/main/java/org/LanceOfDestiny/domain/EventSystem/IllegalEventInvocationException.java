package org.LanceOfDestiny.domain.EventSystem;

public class IllegalEventInvocationException extends RuntimeException{

    public IllegalEventInvocationException(String errorMessage){
        super(errorMessage);
    }
}
