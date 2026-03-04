package com.alpha.Eatclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Address;
@Repository

public interface AddressRepository  extends JpaRepository<Address, Long>{
	   Optional<Address> findByStreet(String street);
}
