package org.LanceOfDestiny.domain.events;

public class IllegalEventInvocationException extends RuntimeException{

    public IllegalEventInvocationException(String errorMessage){
        super(errorMessage);
    }
}
