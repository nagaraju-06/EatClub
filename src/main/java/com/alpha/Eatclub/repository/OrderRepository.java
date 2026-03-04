package com.alpha.Eatclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Order;
@Repository

public interface OrderRepository extends JpaRepository<Order, Long> {
	 Optional<Order> findByOtp(String otp);
}
