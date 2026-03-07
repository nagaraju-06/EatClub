package com.alpha.Eatclub.exceptions;

public class CouponNotFoundException extends RuntimeException{
    public CouponNotFoundException(String msg){
        super(msg);
    }
}