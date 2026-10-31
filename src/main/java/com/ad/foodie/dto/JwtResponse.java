package com.ad.foodie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data 
@Getter 
@Setter 
@Builder 
@AllArgsConstructor 
@NoArgsConstructor 
public class JwtResponse {
    private String token;
    
}
