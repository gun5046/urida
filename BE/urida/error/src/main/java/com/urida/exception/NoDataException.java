package com.urida.exception;

import lombok.Getter;

@Getter
public class NoDataException extends RuntimeException{
    private String message;

    public NoDataException(String message){
        super(message);
        this.message=message;
    }
}
