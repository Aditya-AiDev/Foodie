package com.ad.foodie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@Data 
@Builder 
@NoArgsConstructor 
@AllArgsConstructor 
public class RefreshTokenRequest {

    private String refreshToken;

}
