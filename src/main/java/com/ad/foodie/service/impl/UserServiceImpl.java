package com.ad.foodie.service.impl;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ad.foodie.dto.UserDto;
import com.ad.foodie.entity.User;
import com.ad.foodie.exception.ResourceNotFoundException;
import com.ad.foodie.repository.UserRepo;
import com.ad.foodie.service.UserService;
import com.ad.foodie.utils.IDHelper;



@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Autowired 
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }



    @Override
    public UserDto saveUser(UserDto userDto) {

        ///generate id for user
        userDto.setId(IDHelper.generateRandomId());
        User user=convertUserDtoToUser(userDto);

        ///save user to DB
        User saveUser=userRepo.save(user);

        return convertUserToUserDto(saveUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
       User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
       user.setName(userDto.getName());
       user.setEmail(userDto.getEmail());
       user.setPassword(userDto.getPassword());

       //save update user
       User updatedUser=userRepo.save(user);

       return convertUserToUserDto(updatedUser);

    }

    // @Override
    // public List<UserDto> getAll() {
    //    List<User> users=userRepo.findAll();

    //     //    // by for each method
    //     //    List<UserDto> userDtos= new ArrayList<>();
    //     //    for(User user : users){
    //     //     userDtos.add(convertUserToUserDto(user));
    //     //    }

    //     //By stream
    //     List<UserDto> userDtos= users.stream().map((user) -> convertUserToUserDto(user)).toList();
    //     return userDtos;
    // }

    //
    //getAll() by paging and sorting --pageable---
    @Override
    public Page<UserDto> getAll(Pageable pageable) {
       Page<User> userPage=userRepo.findAll(pageable);

       return userPage.map(user -> convertUserToUserDto(user) );
       
    }



    @Override
    public List<UserDto> getUserByName(String userName) {
        return  userRepo.findByName(userName)
                .stream()
                .map(
                    (user) -> convertUserToUserDto(user)
                    )
                .toList();
        
    }

    @Override
    public UserDto getUserByemail(String email) {
       User user=userRepo.findByEmail(email).orElseThrow(
            () -> new ResourceNotFoundException("User not found!!")
        );
        return convertUserToUserDto(user);
        
    }

    @Override
    public UserDto getUserById(String userId) {
        User user=userRepo.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found!!")
        );
        return convertUserToUserDto(user);
    }

    @Override
    public void deleteUser(String userId) {
       User user=userRepo.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found!!")
        );
        userRepo.delete(user);
    }

    @Override
    public List<UserDto> searchUserName(String keyword) {
       List<User> userList=userRepo.findByNameContaining(keyword);
       return userList.stream().map((user)-> convertUserToUserDto(user)).toList();
    }


    ////userDto to user conversion
    
    private User convertUserDtoToUser(UserDto userDto){
        User user=new User();
        
        BeanUtils.copyProperties(userDto, user);
        //
        //other way-->
        // user.setId(userDto.getId());  
        // user.setName(userDto.getName());  
        // user.setEmail(userDto.getEmail());  
        // user.setPassword(userDto.getPassword());  
        // user.setPhoneNumber(userDto.getPhoneNumber());  
        // user.setAddress(userDto.getAddress());  

        return user;


    }

    ////user to userDto conversion
    private UserDto convertUserToUserDto(User user){
        UserDto userDto=new UserDto();
        
        BeanUtils.copyProperties(user, userDto);
        //
        //other way-->
        // userDto.setId(user.getId());  ///like that set all fields........

        return userDto;


    }

    

}
