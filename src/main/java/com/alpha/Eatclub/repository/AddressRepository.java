package com.alpha.Eatclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Address;
@Repository

public interface AddressRepository  extends JpaRepository<Address, Long>{

}
