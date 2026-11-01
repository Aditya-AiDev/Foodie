package com.ad.foodie.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import com.ad.foodie.entity.User;
import com.ad.foodie.exception.ResourceNotFoundException;
import com.ad.foodie.repository.UserRepo;
import com.ad.foodie.security.CustomUserDetail;

@Service 
public class CustomUserDetailsService implements UserDetailsService {

    private  UserRepo userRepo;
    
    
    @Autowired
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
        
    } 
    


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User not found"));

        CustomUserDetail customUserDetail=new CustomUserDetail(user);
        return customUserDetail;
    }

    


}
