package com.jrs.extra;

public class NotFoundException extends RuntimeException{
    public NotFoundException( String mensaje){
        super(mensaje);
    }
}
