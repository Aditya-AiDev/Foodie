package com.ad.foodie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.ad.foodie.dto.UserDto;
import com.ad.foodie.service.UserService;

@RestController 
@RequestMapping ("/api/v1/users")
public class UserController {

    private UserService userService;

    @Autowired 
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Create User
    @PostMapping 
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto){
        UserDto userDtoResult=userService.saveUser(userDto);

        // return new ResponseEntity<>(userDtoResult,HttpStatus.CREATED);

        //or
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoResult);

    }

    // //get all users
    // @GetMapping 
    // public ResponseEntity<List<UserDto>> findAll(){
    //     return ResponseEntity.ok(userService.getAll());
        
    // }

    //get all users ----by paging and sorting ---
    @GetMapping 
    public ResponseEntity<Page<UserDto>> findAll(
        @RequestParam (value = "page", required = false, defaultValue = "0") int page,
        @RequestParam (value = "sortBy", required = false, defaultValue = "createdDate") String sortBy ,
        @RequestParam (value = "size", required = false, defaultValue = "10") int size,
        @RequestParam (value = "sortDir", required = false, defaultValue = "desc") String sortDir

    ){

        Sort sort=sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable=PageRequest.of(page, size, sort);
        return ResponseEntity.ok(userService.getAll(pageable));
        
    }

    //get user by id
    @GetMapping ("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable("userId") String id ){
        return ResponseEntity.ok(userService.getUserById(id));
    }





    

}
