package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.PaymentType;
import com.thunv.ecommerceou.repositories.PaymentTypeRepository;
import com.thunv.ecommerceou.services.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {
    @Autowired
    private PaymentTypeRepository paymentTypeRepository;
    @Override
    public PaymentType getPaymentTypeByID(int typeID) throws RuntimeException{
        return this.paymentTypeRepository.findById(typeID).orElseThrow(() ->
                new RuntimeException("Can not find payment type with id = " + typeID));
    }

    @Override
    public List<PaymentType> getAllPaymentType()  throws RuntimeException{
        try {
            return this.paymentTypeRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public PaymentType createPaymentType(PaymentType paymentType)  throws RuntimeException{
        try {
            return this.paymentTypeRepository.save(paymentType);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public PaymentType updatePaymentType(PaymentType paymentType)  throws RuntimeException{
        try {
            return this.paymentTypeRepository.save(paymentType);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deletePaymentType(int typeID)  throws RuntimeException{
        try {
            if (this.paymentTypeRepository.existsById(typeID) == false){
                throw new RuntimeException("Type does not exist");
            }
            this.paymentTypeRepository.deleteById(typeID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
