package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.models.pojo.Category;
import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.LocationService;
import com.thunv.ecommerceou.utils.MomoPaymentUtils;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/test")
public class TestController {
    @Autowired
    private LocationService locationService;
    @Autowired
    private Utils utils;
    @Autowired
    private MomoPaymentUtils momoPaymentUtils;
    @GetMapping(value = "/test")
    public ResponseEntity<ModelResponse> test() {
        String ms;
        String code;
        Map<String, String> res = null;
        HttpStatus status;
        try {
//            res = this.locationService.findNearestLocation(1.0f,1.0f);
            System.out.println("hhhh");
            String id = this.utils.generateUUID();
            res = this.momoPaymentUtils.getPaymentInfo(id, "5000", "hihi haha", id);
            ms = "Thanh cong";
            code = "200";
            status = HttpStatus.OK;
        } catch (Exception ex) {
            System.out.println("loi roi");
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }
}
