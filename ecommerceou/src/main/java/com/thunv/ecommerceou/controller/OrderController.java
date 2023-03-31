package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private OrderTrackingService orderTrackingService;
    @Autowired
    private MailService mailService;
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

    @GetMapping(value = "/order-detail/get-orders-detail-by-order-agency/{orderAgencyID}")
    public ResponseEntity<ModelResponse> getOrderDetailByOrderAgency(@PathVariable(value = "orderAgencyID") String orderAgencyID){
        String ms;
        String code;
        List<OrderDetail> res = null;
        HttpStatus status;
        try {
            OrderAgency orderAgency = this.orderService.getOrderAgencyByID(Integer.parseInt(orderAgencyID));
            res = this.orderService.getListOrderDetailByOrderAgency(orderAgency);
            ms = "Get order detail successfully";
            code = "200";
            status = HttpStatus.OK;
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
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
            if (orderState.getId() == 6){
                throw new RuntimeException("Can not change order state to 'canceled' !!!");
            }
            OrderAgency orderAgency = this.orderService.getOrderAgencyByID(Integer.parseInt(orderAgencyID));
            if (orderAgency.getOrderState().getId() == 6){
                throw new RuntimeException("This order has been canceled !!!");
            }
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

    @PatchMapping(value = "/orders-agency/cancel-order/{orderAgencyID}")
    public ResponseEntity<ModelResponse> cancelOrderAgency(@PathVariable(value = "orderAgencyID") String orderAgencyID){
        String ms;
        String code;
        Object res = null;
        HttpStatus status;
        try {
            boolean flagCancel = false;
            OrderAgency orderAgency = this.orderService.getOrderAgencyByID(Integer.parseInt(orderAgencyID));
            if (orderAgency.getOrderState().getId() == 6){
                throw new RuntimeException("Order has been canceled before !!!");
            }
            if (orderAgency.getOrderExpressID() != null){
                Map<Object, Object> temp = this.orderTrackingService.cancelOrderOfGHNExpress(orderAgency.getOrderExpressID());
                if(String.valueOf(temp.get("code")).equals("200")){
                    flagCancel = true;
                    User user = orderAgency.getOrders().getAuthor();
                    ms = "Cancel order successfully !!!";
                    String mailTo = user.getEmail();
                    String subject = "Your order has been canceled";
                    String title = String.format("Dear %s,", user.getUsername());
                    String content = String.format("We have canceled child-order from agency '%s' in your order (order id = %d). Please check your orders in the order tracking section on our website.\n" +
                            "Contact if you need support.", orderAgency.getAgency().getName(), orderAgency.getOrders().getId());
                    String mailTemplate = "reset-password";
                    String items = "";
                    this.mailService.sendMail(mailTo,subject,title,content,items,mailTemplate);
                }else {
                    ms = temp.get("message").toString();
                }
            }else {
                flagCancel = true;
                ms = "Cancel order successfully !!!";
            }
            if (flagCancel == true){
                OrderState orderState = this.orderStateService.getOrderStateByID(6);
                this.orderService.updateStateOfOrdersAgency(orderAgency, orderState);
                status = HttpStatus.OK;
                code = "200";
            }else {
                status = HttpStatus.BAD_REQUEST;
                code = "400";
            }
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
