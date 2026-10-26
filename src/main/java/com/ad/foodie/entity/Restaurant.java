package com.ad.foodie.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "foodie_restaurant")
@Setter
@Getter
public class Restaurant {

    @Id
    private String id;

    private String name;

    @Lob 
    private String description;

    private String address;

    private LocalTime openTime;

    private LocalTime closeTime;

    private boolean open = true;



    private String banner;  ////for restaurant img

    private LocalDateTime createDate;

    // private String status;

    @ManyToOne
    private User user;

}
