package com.ad.foodie.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ad.foodie.dto.RestaurantDto;

public interface RestaurantService {
    
    //add 
    RestaurantDto add(RestaurantDto restaurantDto);
    //update
    RestaurantDto update(RestaurantDto restaurantDto, String id);
    //delete
    void  delete( String id);
    
    //get all
    Page<RestaurantDto> getAll(Pageable pageable);

    List<RestaurantDto> searchByName(String keyword);
    
    //get single
    RestaurantDto get(String id);

    // extra service

    Page<RestaurantDto> getOpenRestaurants(Pageable pageable);


    ///upload Banner
    RestaurantDto uploadBanner(MultipartFile file,String id);
    




    


    

    

}
