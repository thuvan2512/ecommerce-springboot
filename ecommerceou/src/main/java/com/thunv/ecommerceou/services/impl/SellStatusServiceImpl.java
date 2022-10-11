package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.SellStatus;
import com.thunv.ecommerceou.repositories.SellStatusRepository;
import com.thunv.ecommerceou.services.SellStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellStatusServiceImpl implements SellStatusService {
    @Autowired
    private SellStatusRepository sellStatusRepository;
    @Override
    public SellStatus getSellStatusByID(int statusID) throws RuntimeException{
        return this.sellStatusRepository.findById(statusID).orElseThrow(() ->
                new RuntimeException("Can not find sell status with id = " + statusID));
    }

    @Override
    public List<SellStatus> getAllSellStatus() throws RuntimeException{
        try {
            return this.sellStatusRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public SellStatus createSellStatus(SellStatus sellStatus) throws RuntimeException{
        try {
            return this.sellStatusRepository.save(sellStatus);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public SellStatus updateSellStatus(SellStatus sellStatus) throws RuntimeException{
        try {
            return this.sellStatusRepository.save(sellStatus);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteSellStatus(int statusID) throws RuntimeException {
        try {
            if (this.sellStatusRepository.existsById(statusID) == false){
                throw new RuntimeException("Sell status does not exist");
            }
            this.sellStatusRepository.deleteById(statusID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
