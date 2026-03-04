package com.alpha.Eatclub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.Eatclub.entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    Optional<Item> findByName(String name);
}
