package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.OrderState;
import com.thunv.ecommerceou.repositories.OrderStateRepository;
import com.thunv.ecommerceou.services.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStateServiceImpl implements OrderStateService {
    @Autowired
    private OrderStateRepository orderStateRepository;
    @Override
    public OrderState getOrderStateByID(int stateID) throws RuntimeException{
        return this.orderStateRepository.findById(stateID).orElseThrow(() ->
                new RuntimeException("Can not find order state with id = " + stateID));
    }

    @Override
    public List<OrderState> getAllOrderState() throws RuntimeException{
        try {
            return this.orderStateRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public OrderState createOrderState(OrderState orderState) throws RuntimeException{
        try {
            return this.orderStateRepository.save(orderState);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public OrderState updateOrderState(OrderState orderState) throws RuntimeException{
        try {
            return this.orderStateRepository.save(orderState);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteOrderState(int stateID) throws RuntimeException{
        try {
            if (this.orderStateRepository.existsById(stateID) == false){
                throw new RuntimeException("State does not exist");
            }
            this.orderStateRepository.deleteById(stateID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
