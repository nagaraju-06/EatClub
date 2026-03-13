package com.alpha.Eatclub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.Eatclub.entity.Coupon;
import com.alpha.Eatclub.service.Couponservice;
import com.alpha.Eatclub.special.ResponseStructure;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    private Couponservice couponservice;

    
    @PostMapping("/createcupon")
    public ResponseEntity<ResponseStructure<Coupon>> createCoupon(@RequestBody Coupon coupon){
        return couponservice.createCoupon(coupon);
    }

    
    @DeleteMapping("/delete/{couponId}")
    public ResponseEntity<ResponseStructure<String>> deleteCoupon(@PathVariable Integer couponId){
        return couponservice.deleteCoupon(couponId);
    }

    
    @PatchMapping("/update/{couponId}")
    public ResponseEntity<ResponseStructure<Coupon>> updateCoupon(
            @PathVariable Integer couponId,
            @RequestParam String expiryDate){

        return couponservice.updateCoupon(couponId, expiryDate);
    }

    
    @GetMapping("/find/{couponId}")
    public ResponseEntity<ResponseStructure<Coupon>> findCoupon(@PathVariable Integer couponId){
        return couponservice.findCoupon(couponId);
    }
}