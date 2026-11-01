package com.ad.foodie.dto;

import java.time.LocalDate;
import java.util.List;

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

    private List<RoleEntityDto> roleEntities;

    

}
