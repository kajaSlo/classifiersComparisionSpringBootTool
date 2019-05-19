package com.classifierscomparision.classifierscomparisiontool.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DatasetIdException extends RuntimeException{

    public DatasetIdException(String message) {
        super(message);
    }
}
