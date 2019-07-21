package com.thoughtworks.parking_lot.advice;

import com.thoughtworks.parking_lot.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = GlobalException.class)
    public ResponseEntity exceptionHandler(GlobalException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrMessage());
    }
}
