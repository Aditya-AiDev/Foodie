package com.ad.foodie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data 
@Setter 
@Getter 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class RoleEntityDto {

    private int id;
    private String name;


}
