package com.dasom.gongtalk.exception;

public class BaseException extends RuntimeException{

    public BaseException(String message){
        super(message);
        System.out.println(String.format("[Exception] %s", message));
    }
}