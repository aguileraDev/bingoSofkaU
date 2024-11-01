package com.sofkau.bingo.utility.http;

import org.springframework.stereotype.Component;

/**
 * @author Manuel Aguilera / @aguileradev
 */
@Component
public class Response {

    public boolean error;
    public String message;
    public Object data;


    public Response(){
        this.error = false;
        this.message = "";
        this.data = null;
    }

    public void restart(){
        error = false;
        message = "";
        data = null;
    }
}
