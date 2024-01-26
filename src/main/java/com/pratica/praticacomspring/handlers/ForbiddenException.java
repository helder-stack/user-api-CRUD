package com.pratica.praticacomspring.handlers;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(){
        super("Forbidden");
    }

    public ForbiddenException(String message){
        super(message);
    }
}
