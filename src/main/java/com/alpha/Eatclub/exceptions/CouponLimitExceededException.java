package com.alpha.Eatclub.exceptions;

public class CouponLimitExceededException extends RuntimeException{
    public CouponLimitExceededException (String msg){
        super(msg);
    }   
}