package com.igorfood.exception;


public class NegocioException extends RuntimeException{
    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message,Throwable causa){
        super(message,causa);
    }
}
