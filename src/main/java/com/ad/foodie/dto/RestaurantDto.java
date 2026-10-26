package com.ad.foodie.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter 
@Setter
@AllArgsConstructor  /// thees feachers wants java-25
@Builder 
@NoArgsConstructor
public class RestaurantDto {
    //Server side->server change, save it in DB, called serializer(obj to JSON) //and client side-> client only read it by serialization, no deserializer(JSON to obj) so client can not write..
    //serializer(obj to JSON)---only give data---
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    private String id;

    private String name;

     
    private String description;

    private String address;

    @JsonFormat (shape = JsonFormat.Shape.STRING , pattern = "hh:mm:ss a")
    private LocalTime openTime;

    @JsonFormat (shape = JsonFormat.Shape.STRING , pattern = "hh:mm:ss a")
    private LocalTime closeTime;

    private boolean open;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) 
    @JsonFormat (shape = JsonFormat.Shape.STRING , pattern = "MM-dd-YYYY hh:mm:ss a")
    private LocalDateTime createDate;

    // //Server side->store in db and no serializer(obj to JSON) so data can not go to client //and client side-> client only write it..by deserializer(JSON to obj)..
    // // //deserializer(JSON to obj)---only receive data---
    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) 
    // private String status;
    

   // @JsonIgnore ///no serializer(obj to JSON) and no deserializer(JSON to obj) and not saved in DB

    private String banner;  ////for restaurant img 

    

    //getter method 
    @JsonProperty
    public String imageUrl(){
        return "http://localhost:8080/images/" + banner;
    } 


    // public RestaurantDto(String id, String name, String description, String address,
    //                      LocalTime openTime, LocalTime closeTime, boolean open,
    //                      LocalDateTime createDate, String banner) {
    //     this.id = id;
    //     this.name = name;
    //     this.description = description;
    //     this.address = address;
    //     this.openTime = openTime;
    //     this.closeTime = closeTime;
    //     this.open = open;
    //     this.createDate = createDate;
    //     this.banner = banner;
    // }

}
