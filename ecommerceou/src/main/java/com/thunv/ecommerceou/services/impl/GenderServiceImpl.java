package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Gender;
import com.thunv.ecommerceou.repositories.GenderRepository;
import com.thunv.ecommerceou.services.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {
    @Autowired
    private GenderRepository genderRepository;
    @Override
    public Gender getGenderByID(int genderID) throws RuntimeException{
        return this.genderRepository.findById(genderID).orElseThrow(() ->
                new RuntimeException("Can not find gender with id = " + genderID));
    }

    @Override
    public List<Gender> getAllGender() throws RuntimeException{
        try {
            return this.genderRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Gender createGender(Gender gender) throws RuntimeException{
        try {
            return this.genderRepository.save(gender);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Gender updateGender(Gender gender) throws RuntimeException{
        try {
            return this.genderRepository.save(gender);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteGender(int genderID) throws RuntimeException{
        try {
            if (this.genderRepository.existsById(genderID) == false){
                throw new RuntimeException("Gender does not exist");
            }
            this.genderRepository.deleteById(genderID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
