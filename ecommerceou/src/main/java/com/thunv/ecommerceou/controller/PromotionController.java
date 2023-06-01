package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.PromotionCodeDTO;
import com.thunv.ecommerceou.dto.PromotionProgramCreateDTO;
import com.thunv.ecommerceou.dto.PromotionProgramUpdateDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.PromotionCode;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.PromotionService;
import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private Utils utils;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/get-promotion-program-by-list-agency-id/{top}")
    public ResponseEntity<ModelResponse> getProgramPromotionByListAgencyID(@PathVariable(value = "top") String top,
                                                                           @RequestBody Map<String, List<Integer>> mapInput){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            Integer topProgram = Integer.parseInt(top);
            List<Integer> listAgencyID = mapInput.get("listAgencyID");
            if (listAgencyID == null){
                throw new RuntimeException("Invalid input. This is input template ---listAgencyID:[1, 2, 3]--");
            }
            res = this.promotionService.getListProgramByListAgencyID(listAgencyID, topProgram);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(path = "/get-all-promotion-program-by-agency/{agencyID}")
    public ResponseEntity<ModelResponse> getAllProgramPromotionByAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            res = this.promotionService.getALLProgramByAgency(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(path = "/get-all-publish-promotion-program")
    public ResponseEntity<ModelResponse> getAllPublishProgramPromotion(){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            res = this.promotionService.getAllPublishProgram();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @PostMapping(path = "/create-promotion-program/{agencyID}")
    public ResponseEntity<ModelResponse> createPromotionProgram(@PathVariable(value = "agencyID") String agencyID,
                                                                @RequestBody @Valid PromotionProgramCreateDTO promotionProgramCreateDTO,
                                                                BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            PromotionProgram promotionProgram = new PromotionProgram();
            promotionProgram.setAgency(agency);
            promotionProgram = promotionProgramCreateDTO.loadPromotionProgram(promotionProgram);
            this.promotionService.createPromotionProgram(promotionProgram);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @PutMapping(path = "/update-promotion-program/{programID}")
    public ResponseEntity<ModelResponse> createPromotionProgram(@PathVariable(value = "programID") String programID,
                                                                @RequestBody @Valid PromotionProgramUpdateDTO promotionProgramUpdateDTO,
                                                                BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionProgram promotionProgram = this.promotionService.getPromotionProgramByID(Integer.parseInt(programID));
            promotionProgram = promotionProgramUpdateDTO.loadPromotionProgram(promotionProgram);
            this.promotionService.createPromotionProgram(promotionProgram);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @PostMapping(path = "/generate-promotion-code/{programID}")
    public ResponseEntity<ModelResponse> generatePromotionCode(@PathVariable(value = "programID") String programID,
                                                                @RequestBody @Valid PromotionCodeDTO promotionCodeDTO,
                                                                BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionProgram promotionProgram = this.promotionService.getPromotionProgramByID(Integer.parseInt(programID));
            PromotionCode promotionCode = new PromotionCode();
            promotionCode.setId(this.utils.generateUUID());
            promotionCode.setProgram(promotionProgram);
            promotionCode = promotionCodeDTO.loadPromotionCode(promotionCode);
            String prefix = promotionCodeDTO.getPrefix();
            if (prefix == null || prefix.isBlank()){
                prefix = "PROMOTION_CODE_";
            }else {
                prefix = prefix.replace(" ", "").toUpperCase();
            }
            String promotionCodeStr = this.utils.generatePromotionCode(prefix);
            int count = 0;
            while (this.promotionService.checkExistCode(promotionCodeStr)){
                promotionCodeStr = this.utils.generatePromotionCode(prefix);
                count += 1;
                if (count == 10){
                    throw new RuntimeException("Please change your prefix");
                }
            }
            promotionCode.setCode(promotionCodeStr);
            this.promotionService.createPromotionCode(promotionCode);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @DeleteMapping(path = "/delete-promotion-code/{codeID}")
    public ResponseEntity<ModelResponse> deletePromotionCode(@PathVariable(value = "codeID") String codeID){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionCode promotionCodeObj = this.promotionService.getPromotionCodeByID(codeID);
            promotionCodeObj.setState(0);
            this.promotionService.createPromotionCode(promotionCodeObj);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(path = "/publish-promotion-code/{codeID}")
    public ResponseEntity<ModelResponse> publishPromotionCode(@PathVariable(value = "codeID") String codeID){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionCode promotionCodeObj = this.promotionService.getPromotionCodeByID(codeID);
            promotionCodeObj.setIsPublic(1);
            this.promotionService.createPromotionCode(promotionCodeObj);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @PatchMapping(path = "/un-publish-promotion-code/{codeID}")
    public ResponseEntity<ModelResponse> unPublishPromotionCode(@PathVariable(value = "codeID") String codeID){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionCode promotionCodeObj = this.promotionService.getPromotionCodeByID(codeID);
            promotionCodeObj.setIsPublic(0);
            this.promotionService.createPromotionCode(promotionCodeObj);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(path = "/get-promotion-code-by-program-id/{programID}")
    public ResponseEntity<ModelResponse> getListPublishPromotionCode(@PathVariable(value = "programID") String programID){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionProgram promotionProgram = this.promotionService.getPromotionProgramByID(Integer.parseInt(programID));
            res = this.promotionService.getListPublishPromotionCodeByProgramID(promotionProgram);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(path = "/get-promotion-code-for-manage-by-program-id/{programID}")
    public ResponseEntity<ModelResponse> getAllPromotionCodeByProgramID(@PathVariable(value = "programID") String programID){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            PromotionProgram promotionProgram = this.promotionService.getPromotionProgramByID(Integer.parseInt(programID));
            res = this.promotionService.getAllPromotionCodeByProgramID(promotionProgram);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(path = "/preview-discount-by-voucher/{userID}/{voucherCode}")
    public ResponseEntity<ModelResponse> previewDiscountByVoucher(@PathVariable(value = "userID") String userID
            ,@PathVariable(value = "voucherCode") String voucherCode){
        String ms = "successfully !!!";
        String code = "200";
        Object res = null;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            PromotionCode promotionCode = this.promotionService.checkAvailablePromotionCode(voucherCode);
            res = this.promotionService.previewDiscountByVoucher(user, promotionCode);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
}
