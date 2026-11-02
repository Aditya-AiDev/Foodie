package com.ad.foodie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ad.foodie.config.AppConstants;



@Configuration 
// // @EnableMethodSecurity (prePostEnabled = true)
public class SecurityConfig {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired 
    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(e->e.disable())
            .authorizeHttpRequests(request->
                request.requestMatchers("/api/v1/auth/login").permitAll()                  //login-create Token --anyone
                        .requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll()      //add User --anyone 
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole(AppConstants.ROLE_ADMIN)         // delete user --only done by ADMIN and need token
                        .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasRole(AppConstants.ROLE_ADMIN)         // update user --only done by ADMIN and need token
                        .requestMatchers(HttpMethod.POST, "/api/v1/restaurants/**").hasRole(AppConstants.ROLE_ADMIN)  // add Restaurants --only done by ADMIN and need token
                        .requestMatchers(HttpMethod.PUT, "/api/v1/restaurants/**").hasRole(AppConstants.ROLE_ADMIN)  // update Restaurants --only done by ADMIN and need token
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurants/**").hasRole(AppConstants.ROLE_ADMIN)  // delete Restaurants --only done by ADMIN and need token
                        // .requestMatchers(HttpMethod.DELETE, "/api/v1/restaurants/**").hasAnyRole(AppConstants.ROLE_ADMIN,AppConstants.ROLE_GUEST)  // delete Restaurants --only done by ADMIN and need token
                        .requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()         //get users and restaurants--any one
                        .anyRequest().authenticated()
            );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //go to jwt-authentication-entry-point file
        // 
        http.exceptionHandling(exception-> 
            exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler((request,response,accessDeniedException)->{
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Access denied\"}");
                        })

        );

        //
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();

    }
    
    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   



    

}
