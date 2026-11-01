package com.ad.foodie.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ad.foodie.dto.JwtResponse;
import com.ad.foodie.dto.LoginRequest;
import com.ad.foodie.dto.UserDto;
import com.ad.foodie.repository.UserRepo;
import com.ad.foodie.security.JwtSecurityService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtSecurityService jwtSecurityService;

    private ModelMapper modelMapper;
    private UserRepo userRepo;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
            JwtSecurityService jwtSecurityService, ModelMapper modelMapper, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtSecurityService = jwtSecurityService;
        this.modelMapper = modelMapper;
        this.userRepo = userRepo;
    }


    
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        //created authentication
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password());
        // authenticating 
        authenticationManager.authenticate(authentication);
        // getting token
        String jwtToken = jwtSecurityService.generateToken(loginRequest.email());
        // getting user details from DB ///
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        
        UserDto userDto=modelMapper.map(userRepo.findByEmail(userDetails.getUsername()).get() , UserDto.class);
        

        JwtResponse build=JwtResponse.builder().token(jwtToken).user(userDto).build();

        return ResponseEntity.ok(build);

    }

}
