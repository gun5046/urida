package com.urida.exception;

import lombok.Getter;

@Getter
public class SaveException extends RuntimeException{
    private String message;

    public SaveException(String message){
        super(message);
        this.message=message;
    }
}
