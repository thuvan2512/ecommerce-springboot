package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/notify")
public class NotifyController {
    @Autowired
    private NotifyService notifyService;
    @GetMapping(value = "/update-seen-status/{recipientID}")
    public ResponseEntity<ModelResponse> updateSeenStatus(@PathVariable(value = "recipientID") String recipientID){
        String ms;
        String code;
        Object res = null;
        HttpStatus status;
        try {
            this.notifyService.updateSeenStatusOfNotify(recipientID);
            status = HttpStatus.OK;
            ms = "Update seen status successfully !!!";
            code = "200";
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
