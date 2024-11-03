package com.sofkau.bingo.utility.exceptions;

/**
 * @author Manuel Aguilera / @aguileradev
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
