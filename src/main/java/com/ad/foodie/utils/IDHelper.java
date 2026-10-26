package com.ad.foodie.utils;

import java.util.UUID;

public class IDHelper {

    public static String generateRandomId(){
        return  UUID.randomUUID().toString();
    }

}
