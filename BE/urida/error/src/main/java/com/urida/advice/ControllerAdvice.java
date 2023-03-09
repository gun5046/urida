package com.urida.advice;

import com.urida.exception.InputException;
import com.urida.exception.NoDataException;
import com.urida.exception.SaveException;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(value = NoDataException.class)
    public ResponseEntity<?> throwSqlException(NoDataException e){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Error",e.getMessage());
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SaveException.class)
    public ResponseEntity<?> throwSaveException(SaveException e){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Error",e.getMessage());
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InputException.class)
    public ResponseEntity<?> throwInputException(InputException e){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Error",e.getMessage());
        return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(value = RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public void runtimeException(){
//
//    }
//    @ExceptionHandler(value = RuntimeException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public void runtimeException(){
//
//    }
}
