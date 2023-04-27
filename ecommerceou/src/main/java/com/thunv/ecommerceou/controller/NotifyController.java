package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.AgencyRegisterDTO;
import com.thunv.ecommerceou.dto.NotifyDTO;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.NotifyService;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/notify")
public class NotifyController {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private Utils utils;
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

    @PostMapping(value = "/send-notify")
    public ResponseEntity<ModelResponse> updateSeenStatus(@RequestBody @Valid NotifyDTO notifyDTO,
                                                          BindingResult result){

        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms;
        String code;
        Object res = null;
        HttpStatus status;
        try {
            this.notifyService.pushNotify(notifyDTO.getRecipientID(),
                    notifyDTO.getImage(),
                    notifyDTO.getTitle(),
                    notifyDTO.getDetails(),
                    notifyDTO.getType());
            status = HttpStatus.OK;
            ms = "Push notify successfully !!!";
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
