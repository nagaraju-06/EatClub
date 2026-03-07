package com.alpha.Eatclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Coupon;
import com.alpha.Eatclub.entity.CouponRedmaption;
import com.alpha.Eatclub.entity.Customer;

@Repository
public interface Couponredemptionrepoo extends JpaRepository<CouponRedmaption, Integer> {
	
	Optional<CouponRedmaption>findByCouponAndCustomer(Coupon coupon,Customer customer);
	
	boolean existsByCoupon(Coupon coupon);

}
