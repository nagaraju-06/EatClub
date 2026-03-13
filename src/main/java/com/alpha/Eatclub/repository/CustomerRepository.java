package com.alpha.Eatclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.CartItem;
import com.alpha.Eatclub.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	

	Optional<Customer> findByMobno(long mobno);

    

}
