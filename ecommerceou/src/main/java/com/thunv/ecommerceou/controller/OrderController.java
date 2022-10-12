package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.OrderService;
import com.thunv.ecommerceou.services.OrderStateService;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private UserService userService;
    @GetMapping(value = "/orders-agency/agency/{agencyID}")
    public ResponseEntity<ModelResponse> getOrderAgencyOfAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get orders agency successfully";
        String code = "200";
        List<OrderAgency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.orderService.getListOrderAgencyByAgency(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/orders-agency/user/{userID}")
    public ResponseEntity<ModelResponse> getOrderAgencyOfUser(@PathVariable(value = "userID") String userID){
        String ms = "Get orders agency successfully";
        String code = "200";
        List<OrderAgency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            list = this.orderService.getListOrderAgencyByUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/orders/user/{userID}")
    public ResponseEntity<ModelResponse> getOrderOfUser(@PathVariable(value = "userID") String userID){
        String ms = "Get orders agency successfully";
        String code = "200";
        List<Orders> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            list = this.orderService.getListOrderByUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PatchMapping(value = "/orders-agency/{orderAgencyID}/{stateID}")
    public ResponseEntity<ModelResponse> changeStateOfOrderAgency(@PathVariable(value = "stateID") String stateID,
                                                                  @PathVariable(value = "orderAgencyID") String orderAgencyID){
        String ms = "Change state successfully";
        String code = "200";
        OrderAgency res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            OrderState orderState = this.orderStateService.getOrderStateByID(Integer.parseInt(stateID));
            OrderAgency orderAgency = this.orderService.getOrderAgencyByID(Integer.parseInt(orderAgencyID));
            res = this.orderService.updateStateOfOrdersAgency(orderAgency,orderState);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
}
