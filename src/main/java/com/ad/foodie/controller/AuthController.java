package com.ad.foodie.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.ad.foodie.dto.RefreshTokenRequest;
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
        
        
        // getting user details from DB ///
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        
        UserDto userDto=modelMapper.map(userRepo.findByEmail(userDetails.getUsername()).get() , UserDto.class);

        // getting token
        String jwtToken = jwtSecurityService.generateToken(userDto.getEmail(),true);
        
        String refreshToken = jwtSecurityService.generateToken(userDto.getEmail(),false);
        

        JwtResponse build=JwtResponse.builder()
                                    .accessToken(jwtToken)
                                    .refreshToken(refreshToken)
                                    .user(userDto)
                                    .build();

        return ResponseEntity.ok(build);

    }

    //// Refresh Token API
    
    @PostMapping ("/refresh-token")
    public ResponseEntity<?> refreshToken( @RequestBody RefreshTokenRequest refreshTokenRequest){

        // Validating the Refresh token
        if(jwtSecurityService.validateToken(refreshTokenRequest.getRefreshToken()) && jwtSecurityService.isRefreshToken(refreshTokenRequest.getRefreshToken())){

            // getting username from Refresh token
            String usernameFromRefreshToken = jwtSecurityService.getUsername(refreshTokenRequest.getRefreshToken());

            // getting userDto data from DB for --usernameFromRefreshToken
            UserDto userDto= modelMapper.map(userRepo.findByEmail(usernameFromRefreshToken).get() , UserDto.class);

            // generating new access token
            String accessToken = jwtSecurityService.generateToken(userDto.getEmail(),true);
            String newRefreshToken = jwtSecurityService.generateToken(userDto.getEmail(),false);

            JwtResponse response = JwtResponse.builder()
                                            .accessToken(accessToken)
                                            .refreshToken(newRefreshToken)
                                            .user(userDto)
                                            .build();

            return ResponseEntity.ok(response);


        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Refresh Token!!");
        }


    }

}
