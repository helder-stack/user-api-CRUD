package com.pratica.praticacomspring.handlers;

public class BadRequestException extends RuntimeException{
    public BadRequestException(){
        super("Bad request");
    }

    public BadRequestException(String message){
        super(message);
    }
}
