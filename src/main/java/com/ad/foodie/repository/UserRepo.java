package com.ad.foodie.repository;

import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ad.foodie.entity.User;
import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User, String> {


    //custom query method//

    List<User> findByName(String name);

    Optional<User> findByEmail(String email);

    List<User> findByNameContaining(String keyword);

    Page<User> findAll(Pageable pageable);


}
