package com.ad.foodie.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ad.foodie.dto.UserDto;

public interface UserService {

    UserDto saveUser(UserDto userDto);

    UserDto updateUser(UserDto userDto,String userId);

    // List<UserDto> getAll();
    //getAll() by paging and sorting --pageable---
    Page<UserDto> getAll(Pageable pageable);
    

    List<UserDto> getUserByName(String userName);

    UserDto getUserByemail(String email);

    UserDto getUserById(String userId);

    void deleteUser(String userId);

    List<UserDto> searchUserName(String keyword);

    
    
}
