package com.ad.foodie.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ad.foodie.dto.FileData;
import com.ad.foodie.dto.RestaurantDto;
import com.ad.foodie.entity.Restaurant;
import com.ad.foodie.exception.ResourceNotFoundException;
import com.ad.foodie.repository.RestaurantRepo;
import com.ad.foodie.service.FileService;
import com.ad.foodie.service.RestaurantService;
import com.ad.foodie.utils.IDHelper;

@Service 
public class RestaurantServiceImpl implements RestaurantService {

    @Value("${restaurant.file.path}") 
    private String bannerFolderPath;

    private RestaurantRepo restaurantRepo;
    private ModelMapper modelMapper;

    private FileService fileService;
    
    @Autowired
    public RestaurantServiceImpl(RestaurantRepo restaurantRepo, ModelMapper modelMapper, FileService fileService) {
        this.restaurantRepo = restaurantRepo;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
    } 
    
    

    @Override
    public RestaurantDto add(RestaurantDto restaurantDto) {
        //ID:
        restaurantDto.setId(IDHelper.generateRandomId());
        restaurantDto.setCreateDate(LocalDateTime.now());
        // convert DTO to entity via modelMapper
        Restaurant restaurant=modelMapper.map(restaurantDto, Restaurant.class);
        //save the entity
        Restaurant savedEntity= restaurantRepo.save(restaurant);
        // convert  entity to DTO via modelMapper
        return modelMapper.map(savedEntity, RestaurantDto.class);

    }

    

    



    @Override
    public RestaurantDto update(RestaurantDto restaurantDto, String id) {
        Restaurant restaurant=restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found!!"));
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setOpenTime(restaurantDto.getOpenTime());
        restaurant.setCloseTime(restaurantDto.getCloseTime());
        restaurant.setOpen(restaurantDto.isOpen());

        Restaurant savesEntity=restaurantRepo.save(restaurant);
        return modelMapper.map(savesEntity, RestaurantDto.class);
    }

    @Override
    public void delete(String id) {
        Restaurant restaurant=restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found!!"));
        restaurantRepo.delete(restaurant);
    }

    @Override
    public RestaurantDto get(String id) {
        Restaurant restaurant=restaurantRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found!!"));
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    @Override
    public Page<RestaurantDto> getAll(Pageable pageable) {
        Page<Restaurant> pageRestaurant=restaurantRepo.findAll(pageable);
        return  pageRestaurant.map(restaurant -> modelMapper.map(restaurant, RestaurantDto.class));
    }

    @Override
    public List<RestaurantDto> searchByName(String keyword) {

        // return restaurantRepo.findByNameContainingIgnoreCase(keyword)
        // .stream()
        // .map(
        //     restaurant -> modelMapper
        //     .map(restaurant, RestaurantDto.class)
        // ).toList();                                      /// return immutable list

        return restaurantRepo.findByNameContainingIgnoreCase(keyword)
        .stream()
        .map(
            restaurant -> modelMapper
            .map(restaurant, RestaurantDto.class)
        ).collect(Collectors.toList());                        /// return mutable list
    }

    

    @Override
    public Page<RestaurantDto> getOpenRestaurants(Pageable pageable) {
        Page<Restaurant> pageRestaurant=restaurantRepo.findByOpen(true, pageable);
        return pageRestaurant.map(
                                restaurant -> modelMapper.map(restaurant, RestaurantDto.class)
                            );
    }


    @Override
    public RestaurantDto uploadBanner(MultipartFile file, String id){

        //upload file:
        String fileName=file.getOriginalFilename();  //give file name with extension // abc.png
        if(fileName==null || !fileName.contains(".")){
            throw new IllegalArgumentException("Invalid File name or missing extension.");
        }

        String fileExtension=fileName.substring(fileName.lastIndexOf(".")); ///including . in extension
        String newFileName=new Date().getTime() + fileExtension;  ///time + .extension

        ////call fileService//
        FileData fileData=fileService.uploadFile(file, bannerFolderPath + newFileName);   ////folder + (time + .extension)
 
        //
        Restaurant restaurant=restaurantRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Restaurant not found"));
        restaurant.setBanner(fileData.fileName());
        restaurantRepo.save(restaurant); 

        return modelMapper.map(restaurant, RestaurantDto.class);
    }



}
