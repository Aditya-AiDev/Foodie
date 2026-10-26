package com.ad.foodie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.foodie.entity.Restaurant;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, String> {

    List<Restaurant> findByNameContainingIgnoreCase(String keyword);

    Page<Restaurant> findByOpen(boolean flag,Pageable pageable);

}
