package com.ad.foodie.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
 
public class UserDto {

    private String id;

    private String name;

    private String email;

    private String password;

    private String address;

    private String phoneNumber;

    private LocalDate createdDate;

    // ///AllArgConstructor
    // public UserDto(String id, String name, String email, String password, String address,
    //         String phoneNumber, LocalDate createdDate) {
    //     this.id = id;
    //     this.name = name;
    //     this.email = email;
    //     this.password = password;
    //     this.address = address;
    //     this.phoneNumber = phoneNumber;
    //     this.createdDate = createdDate;
    // }

}
