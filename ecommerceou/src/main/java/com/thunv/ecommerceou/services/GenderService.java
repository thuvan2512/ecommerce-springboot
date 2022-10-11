package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.Gender;

import java.util.List;

public interface GenderService {
    Gender getGenderByID(int genderID);
    List<Gender> getAllGender();
    Gender createGender(Gender gender);
    Gender updateGender(Gender gender);
    boolean deleteGender(int genderID);
}
