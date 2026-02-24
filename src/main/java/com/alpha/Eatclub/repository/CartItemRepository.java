package com.alpha.Eatclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.CartItem;
import com.alpha.Eatclub.entity.Customer;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	List<CartItem> findByCustomer(Customer customer);

}
