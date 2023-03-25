package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import com.thunv.ecommerceou.models.pojo.OrderAgency;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.CustomerAddressBookService;
import com.thunv.ecommerceou.services.OrderService;
import com.thunv.ecommerceou.services.OrderTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/order-tracking")
public class OrderTrackingController {
    @Autowired
    private OrderTrackingService orderTrackingService;
    @Autowired
    private Environment env;
    @Autowired
    private CustomerAddressBookService customerAddressBookService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private OrderService orderService;
    @GetMapping(value = "/ghn/location/get-provinces")
    public ResponseEntity<ModelResponse> getLocationProvincesOfGHNExpress(){
        String ms ;
        String code;
        Map<Object, Object> res = new HashMap<>();
        HttpStatus status;
        try {
            Map<Object, Object> temp = this.orderTrackingService.getLocationProvincesOfGHNExpress();
            if(String.valueOf(temp.get("code")).equals("200")){
                res.put("provinces", temp.get("data"));
                code = "200";
                ms = "Get provinces of Giao Hang Nhanh Express successfully !!!";
                status = HttpStatus.OK;
            }else {
                throw new RuntimeException(temp.get("message").toString());
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }

    @GetMapping(value = "/ghn/location/get-districts")
    public ResponseEntity<ModelResponse> getLocationDistrictsOfGHNExpress(@RequestParam(defaultValue = "0") String provinceID){
        String ms ;
        String code;
        Map<Object, Object> res = new HashMap<>();
        HttpStatus status;
        try {
            int province = Integer.parseInt(provinceID);
            if (province != 0){
                Map<Object, Object> temp = this.orderTrackingService.getLocationDistrictsOfGHNExpress(province);
                if(String.valueOf(temp.get("code")).equals("200")){
                    res.put("districts", temp.get("data"));
                    code = "200";
                    ms = "Get districts of Giao Hang Nhanh Express successfully !!!";
                    status = HttpStatus.OK;
                }else {
                    throw new RuntimeException(temp.get("message").toString());
                }
            }else {
                throw new RuntimeException("ProvinceID not found !!!");
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }

    @GetMapping(value = "/ghn/location/get-wards")
    public ResponseEntity<ModelResponse> getLocationWardsOfGHNExpress(@RequestParam(defaultValue = "0") String districtID){
        String ms ;
        String code;
        Map<Object, Object> res = new HashMap<>();
        HttpStatus status;
        try {
            int district = Integer.parseInt(districtID);
            if (district != 0){
                Map<Object, Object> temp = this.orderTrackingService.getLocationWardsOfGHNExpress(district);
                if(String.valueOf(temp.get("code")).equals("200")){
                    res.put("wards", temp.get("data"));
                    code = "200";
                    ms = "Get wards of Giao Hang Nhanh Express successfully !!!";
                    status = HttpStatus.OK;
                }else {
                    throw new RuntimeException(temp.get("message").toString());
                }
            }else {
                throw new RuntimeException("DistrictID not found !!!");
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }

    @GetMapping(value = "/ghn/order/get-service-package-of-ghn-express")
    public ResponseEntity<ModelResponse> getAvailableServiceShippingOfGHNExpress(@RequestParam(defaultValue = "0") String addressBookID,
                                                                                 @RequestParam(defaultValue = "0") String agencyID){
        String ms ;
        String code;
        Map<Object, Object> res = new HashMap<>();
        HttpStatus status;
        try {
            CustomerAddressBook customerAddressBook = this.customerAddressBookService.getAddressByID(Integer.parseInt(addressBookID));
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            Integer fromDistrict = agency.getDistrictID();
            String fromWard = agency.getWardID();
            Integer toDistrict = customerAddressBook.getDistrictID();
            String toWard = customerAddressBook.getWardID();
            if (fromDistrict != null && toDistrict != null && toWard != null){
                Map<Object, Object> temp = this.orderTrackingService.getAvailableServiceShippingOfGHNExpress(fromDistrict,fromWard, toDistrict, toWard);
                if(String.valueOf(temp.get("code")).equals("200")){
                    res.put("services", temp.get("data"));
                    code = "200";
                    ms = "Get delivery services of Giao Hang Nhanh Express successfully !!!";
                    status = HttpStatus.OK;
                }else {
                    throw new RuntimeException(temp.get("message").toString());
                }
            }else {
                throw new RuntimeException("Input not found !!!");
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }

    @GetMapping(value = "/ghn/order/print-order/{orderAgentID}")
    public ResponseEntity<ModelResponse> printOrderOfGHNExpress(@PathVariable(value = "orderAgentID") String orderAgentID,
                                                                @RequestParam(defaultValue = "1") String printSize){
        String ms ;
        String code;
        Map<Object, Object> res = new HashMap<>();
        HttpStatus status;
        try {
            OrderAgency orderAgency = this.orderService.getOrderAgencyByID(Integer.parseInt(orderAgentID));
            String orderCode = orderAgency.getOrderExpressID();
            if (orderCode == null){
                throw new RuntimeException("Not found order code provided by GHN Express. Please contact to admin to resolve your problem !!!");
            }
            String url;
            switch (printSize){
                case "2":
                    url = env.getProperty("ghn.printOrder.52x70").toString();
                    break;
                case "3":
                    url = env.getProperty("ghn.printOrder.80x80").toString();
                    break;
                default:
                    url = env.getProperty("ghn.printOrder.a5").toString();
                    break;
            }
            Map<Object, Object> temp = this.orderTrackingService.generateTokenToPrintOrderOfGHNExpress(orderCode);
            if(String.valueOf(temp.get("code")).equals("200")){
                String token = ((Map<Object, Object>) temp.get("data")).get("token").toString();
                res.put("urlPrintOrder", String.format("%s%s", url, token));
                code = "200";
                ms = "Get delivery services of Giao Hang Nhanh Express successfully !!!";
                status = HttpStatus.OK;
            }else {
                throw new RuntimeException(temp.get("message").toString());
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }
}
