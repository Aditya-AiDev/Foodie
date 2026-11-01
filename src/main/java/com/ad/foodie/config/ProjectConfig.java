package com.ad.foodie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

import com.ad.foodie.entity.RoleEntity;
import com.ad.foodie.repository.RoleEntityRepo;




@Configuration 
public class ProjectConfig {

    @Bean 
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


    // ROLE_GUEST and ROLE_ADMIN are fixed application roles, seed them automatically at startup

    @Bean
    CommandLineRunner initializeRoles(RoleEntityRepo roleRepo) {
        return args -> {
            createIfMissing(roleRepo, AppConstants.getRoleGuest());
            createIfMissing(roleRepo, AppConstants.getRoleAdmin());
        };
    }

    private void createIfMissing(RoleEntityRepo roleRepo, String roleName) {
        if (roleRepo.findByName(roleName).isEmpty()) {
            RoleEntity role = new RoleEntity();
            role.setName(roleName);
            roleRepo.save(role);
        }
    }

    
    

    // @Bean
    // public UserDetailsService userDetailsService() {
    
    //     UserDetails user1 = User.withUsername("user")
    //             .password("{noop}user")
    //             .roles("USER")
    //             .build();
    //     UserDetails user2 = User.withUsername("admin")
    //             .password("{noop}admin")
    //             .roles("ADMIN")
    //             .build();
    
    //     return new InMemoryUserDetailsManager(user1,user2);     // InMemoryUserDetailsManager -->> it return username and password to UserDetailsService
    //                                                         // UserDetailsService also have --->> loadUserByUsername which fetch username and password from DB
    // }



    

}
