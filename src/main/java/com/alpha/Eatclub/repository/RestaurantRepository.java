package com.alpha.Eatclub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Restaurant;

@Repository

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByMobno(long mobno);

    List<Restaurant> findByAddress_City(String city);
}

