package com.alpha.Eatclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.DeliveryPartner;
@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long>{
	

DeliveryPartner findByPhone(String phone);


	void deleteByPhone(String phone);
}
