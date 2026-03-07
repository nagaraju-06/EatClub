package com.alpha.Eatclub.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.Eatclub.entity.Coupon;
import com.alpha.Eatclub.exceptions.CouponInvalidException;
import com.alpha.Eatclub.exceptions.CouponNotFoundException;
import com.alpha.Eatclub.repository.Couponredemptionrepoo;
import com.alpha.Eatclub.repository.Couponrepoo;
import com.alpha.Eatclub.special.ResponseStructure;

@Service
public class Couponservice {
	@Autowired
	private Couponrepoo couponrepoo;
	
	@Autowired
	private Couponredemptionrepoo couponrederepoo;
	
    public ResponseEntity<ResponseStructure<Coupon>> createCoupon(Coupon coupon){

        Coupon savedCoupon = couponrepoo.save(coupon);

        ResponseStructure<Coupon> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.CREATED.value());
        rs.setMessage("Coupon Created Successfully");
        rs.setData(savedCoupon);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }
    
    
 // DELETE COUPON
    public ResponseEntity<ResponseStructure<String>> deleteCoupon(Integer couponId){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        // Check if coupon already used
        if(couponrederepoo.existsByCoupon(coupon)){
            throw new CouponInvalidException("Coupon already used by customers, cannot delete");
        }

        couponrepoo.delete(coupon);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

 // UPDATE COUPON

    public ResponseEntity<ResponseStructure<Coupon>> updateCoupon(
            Integer couponId,
            String expiryDate){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        boolean used = couponrederepoo.existsByCoupon(coupon);

        // If nobody used coupon → extend expiry
        if(!used){
            coupon.setExpiryDate(LocalDate.parse(expiryDate));
        }
        else{
            // If used → reduce maxCoupons
            if(coupon.getMaxCoupons() > 0){
                coupon.setMaxCoupons(coupon.getMaxCoupons() - 1);
            }
        }

        couponrepoo.save(coupon);

        ResponseStructure<Coupon> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon Updated Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }



    //Finding the Coupon



    // FIND COUPON

    public ResponseEntity<ResponseStructure<Coupon>> findCoupon(Integer couponId){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new CouponNotFoundException("Coupon not found"));

        ResponseStructure<Coupon> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon Fetched Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

	

}
