package com.alpha.Eatclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Coupon;

@Repository
public interface Couponrepoo extends JpaRepository<Coupon, Integer> {
	
	 List<Coupon> findByStatus(String status);

}
