package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.jwt.JwtTokenUtils;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.RenewalPackage;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.RenewalService;
import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.utils.MomoPaymentUtils;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/renewal")
public class RenewalController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private MomoPaymentUtils momoPaymentUtils;
    @Autowired
    private RenewalService renewalService;
    @Autowired
    private Utils utils;

    @GetMapping(path = "/get-momo-payment-info/{packageID}")
    public ResponseEntity<ModelResponse> getMomoPaymentInfo(@PathVariable(value = "packageID") String packageID){
        Map<String, String> result;
        String ms = "Get payment info successfully";
        String code = "200";
        Map<String, String> res = new HashMap<>();
        try {
            int intPackageID = Integer.parseInt(packageID);
            if (intPackageID == 1){
                throw new RuntimeException("Can not choose package trial !!!");
            }
            RenewalPackage renewalPackage = this.renewalService.getRenewalPackageByID(intPackageID);
            String orderID = this.utils.generateUUID();
            String orderInfo = String.format("Renewal '%s'", renewalPackage.getPackageName());
            result = this.momoPaymentUtils.getPaymentInfoForRenewal(orderID,
                    String.valueOf(renewalPackage.getDiscountPrice()),
                    orderInfo,
                    orderID);
            if (String.valueOf(result.get("resultCode")).equals("0")){
                res.put("payUrl", result.get("payUrl"));
                res.put("message", "Create payment info successfully!!!");
            }
            else {
                res.put("payUrl", null);
                res.put("message", "Create payment info failed !!!");
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @GetMapping(path = "/get-list-renewal-package")
    public ResponseEntity<ModelResponse> getListRenewalPackage(){
        String ms = "Get list package successfully";
        String code = "200";
        Object res = null;
        try {
            res = this.renewalService.getListRenewalPackage();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }


    @GetMapping(path = "/get-renewal-manage-by-agency-id/{agencyID}")
    public ResponseEntity<ModelResponse> getListRenewalManageByAgencyID(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get renewal manage by agency id successfully";
        String code = "200";
        Object res = null;
        try {
            res = this.renewalService.getRenewalManageByAgencyID(Integer.parseInt(agencyID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @PostMapping(path = "/create-order-renewal/{agencyID}/{packageID}")
    public ResponseEntity<ModelResponse> createOrderRenewal(@PathVariable(value = "agencyID") String agencyID,
                                                            @PathVariable(value = "packageID") String packageID){
        String ms = "Get create order renewal successfully";
        String code = "200";
        Object res = null;
        try {
            this.renewalService.createOrderRenewal(Integer.parseInt(agencyID), Integer.parseInt(packageID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/stats-revenue-by-year")
    public ResponseEntity<ModelResponse> getStatsRevenueByYear(){
        String ms = "Get stats revenue successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.renewalService.getStatsRevenueYear();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }

    @GetMapping(value = "/stats-revenue-quarter-by-year/{year}")
    public ResponseEntity<ModelResponse> getStatsRevenueQuarterByYear(@PathVariable(value = "year") String year){
        String ms = "Get stats revenue successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.renewalService.getStatsRevenueQuarterByYear(Integer.parseInt(year));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }

    @GetMapping(value = "/stats-revenue-month-by-year/{year}")
    public ResponseEntity<ModelResponse> getStatsRevenueMonthByYear(@PathVariable(value = "year") String year){
        String ms = "Get stats revenue successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.renewalService.getStatsRevenueMonthByYear(Integer.parseInt(year));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
}
