package com.ad.foodie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.foodie.entity.RoleEntity;


@Repository 
public interface RoleEntityRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);

}
