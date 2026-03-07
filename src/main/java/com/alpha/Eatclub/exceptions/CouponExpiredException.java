package com.alpha.Eatclub.exceptions;

public class CouponExpiredException extends RuntimeException{
    public CouponExpiredException(String msg){
        super(msg);
    } 

}