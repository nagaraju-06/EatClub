package com.alpha.Eatclub.exceptions;

public class CouponInvalidException extends RuntimeException{
    public CouponInvalidException(String msg){
        super(msg);
    }
}