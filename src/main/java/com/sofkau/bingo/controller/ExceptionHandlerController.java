package com.sofkau.bingo.controller;

import com.sofkau.bingo.utility.exceptions.NotFoundException;
import com.sofkau.bingo.utility.exceptions.RegisterException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author Manuel Aguilera / @aguileradev
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(FeignException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleFeignException(FeignException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(RegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleRegisterException(RegisterException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        logger.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }


}
