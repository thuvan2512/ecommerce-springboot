package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.OrderDetailRepository;
import com.thunv.ecommerceou.repositories.OrdersAgencyRepository;
import com.thunv.ecommerceou.repositories.OrdersRepository;
import com.thunv.ecommerceou.services.ItemService;
import com.thunv.ecommerceou.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrdersAgencyRepository ordersAgencyRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ItemService itemService;

    @Override
    public OrderAgency getOrderAgencyByID(int orderAgencyID)  throws RuntimeException{
        return this.ordersAgencyRepository.findById(orderAgencyID).orElseThrow(() -> new RuntimeException("Can not find order agency with id = " + orderAgencyID));

    }

    @Override
    public List<OrderAgency> getListOrderAgencyByAgency(Agency agency) throws RuntimeException {
        try {
            return this.ordersAgencyRepository.findByAgency(agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<OrderAgency> getListOrderAgencyByUser(User user) throws RuntimeException {
        try {
            return this.ordersAgencyRepository.getListOrderAgencyByUser(user);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Orders> getListOrderByUser(User user) throws RuntimeException{
        try {
            return this.ordersRepository.findByAuthor(user);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<OrderDetail> getListOrderDetailByOrderAgency(OrderAgency orderAgency) throws RuntimeException{
        try {
            return this.orderDetailRepository.findByOrderAgency(orderAgency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public OrderAgency updateStateOfOrdersAgency(OrderAgency orderAgency,OrderState orderState) {
        try {
            orderAgency.setOrderState(orderState);
            return this.ordersAgencyRepository.save(orderAgency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean returnOldQuantityAfterCancelOrder(List<OrderDetail> orderDetailList) {
        try {
            for (OrderDetail orderDetail: orderDetailList){
                ItemPost itemPost = orderDetail.getItemPost();
                int oldQty = itemPost.getInventory() + orderDetail.getQuantity();
                itemPost.setInventory(oldQty);
                this.itemService.updateItemPost(itemPost);
            }
            return true;
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
