package com.classifierscomparision.classifierscomparisiontool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class DatasetIdExceptionResponseEntityHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> datasetIdExceptionHandler(DatasetIdException datasetException, WebRequest request){
        DatasetIdExceptionRes exceptionRes = new DatasetIdExceptionRes(datasetException.getMessage());

        return new ResponseEntity(exceptionRes, HttpStatus.BAD_REQUEST);
    }
}
