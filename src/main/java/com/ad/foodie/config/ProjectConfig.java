package com.ad.foodie.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;






@Configuration 
public class ProjectConfig {

    @Bean 
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    

    @Bean
    public UserDetailsService userDetailsService() {
    
        UserDetails user1 = User.withUsername("user")
                .password("{noop}user")
                .roles("USER")
                .build();
    
        return new InMemoryUserDetailsManager(user1);
    }



    

}
