package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.*;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.res.SearchResponse;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.CensorshipAgencyService;
import com.thunv.ecommerceou.services.CommentService;
import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.utils.TwilioSendSMSUtils;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/agency")
public class AgencyController {
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private UserService userService;
    @Autowired
    private CensorshipAgencyService censorshipAgencyService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private Environment env;
    @Autowired
    private Utils utils;
    @Autowired
    private TwilioSendSMSUtils twilioSendSMSUtils;
    @GetMapping(value = "/{agencyID}")
    public ResponseEntity<ModelResponse> getAgencyByID(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get agency successfully";
        String code = "200";
        Agency agency = null;
        HttpStatus status = HttpStatus.OK;
        try {
            agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,agency)
        );
    }
    @GetMapping(value = "/all")
    public ResponseEntity<ModelResponse> getAllAgency(){
        String ms = "Get all agency successfully";
        String code = "200";
        List<Agency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.agencyService.getAllAgency();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/top-agency/{top}")
    public ResponseEntity<ModelResponse> getTopAgency(@PathVariable(value = "top") String top){
        String ms = "Get top agency successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.agencyService.getTopAgency(Integer.parseInt(top));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/star-average/{agencyID}")
    public ResponseEntity<ModelResponse> getStarAverageByAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get avg star by agency successfully";
        String code = "200";
        double starAverage = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            starAverage = this.commentService.getAverageStarByAgency(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,starAverage)
        );
    }
    @PostMapping(value = "/search")
    public ResponseEntity<ModelResponse> searchAgency(@RequestBody @Valid SearchAgencyDTO searchAgencyDTO,
                                                      BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Search agency successfully";
        String code = "200";
        List<Agency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.agencyService.searchAgency(searchAgencyDTO);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        SearchResponse searchResponse = new SearchResponse(this.agencyService.getTotalPageAgency(),
                Integer.parseInt(this.env.getProperty("page.size")),
                this.agencyService.countAgency(),
                searchAgencyDTO.getPage(),list.size(), Arrays.asList(list.toArray()));
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,searchResponse)
        );
    }
    @PostMapping(path = "/register")
    public ResponseEntity<ModelResponse> registerAgency(@RequestBody @Valid AgencyRegisterDTO agencyRegisterDTO,
                                                        BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Register agency successfully";
        String code = "201";
        Agency res = null;
        try {
            if (this.utils.checkPhoneNumberIsValid(agencyRegisterDTO.getHotline()) == false){
                throw new RuntimeException("Invalid phone number !!!");
            }
            res = this.agencyService.createAgency(agencyRegisterDTO);
            this.censorshipAgencyService.createCensorshipAgency(res);
            this.twilioSendSMSUtils.sendSMSUsingTwilio("+84877158491", String.format("Notice of receipt of a new moderation request from agent '%s'", res.getName()));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @GetMapping(value = "/follow-list/{userID}")
    public ResponseEntity<ModelResponse> getFollowListByUserID(@PathVariable(value = "userID") String userID){
        String ms = "Get all agency follow by user successfully";
        String code = "200";
        List<Agency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            list = this.agencyService.getListAgencyFollowByUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PutMapping(path = "/{agencyID}")
    public ResponseEntity<ModelResponse> updateAgencyInfo(@RequestBody AgencyUpdateDTO agencyUpdateDTO,
                                                        @PathVariable(value = "agencyID") String agencyID,
                                                        HttpServletRequest request){
        String ms = "Update agency info successfully";
        String code = "200";
        Agency res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (agencyUpdateDTO.getHotline() != null){
                if (this.utils.checkPhoneNumberIsValid(agencyUpdateDTO.getHotline()) == false){
                    throw new RuntimeException("Invalid phone number !!!");
                }
            }
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            agencyUpdateDTO.loadAgencyFromAgencyDTO(agency);
            res = this.agencyService.updateAgency(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(path = "/ban/{agencyID}")
    public ResponseEntity<ModelResponse> banAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Ban agency successfully";
        String code = "200";
        Agency res = null;
        try {
            res = this.agencyService.banAgency(Integer.parseInt(agencyID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(path = "/unban/{agencyID}")
    public ResponseEntity<ModelResponse> unBanAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Un ban agency successfully";
        String code = "200";
        Agency res = null;
        try {
            res = this.agencyService.unBanAgency(Integer.parseInt(agencyID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "/{agencyID}")
    public ResponseEntity<ModelResponse> deleteAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Delete agency successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.agencyService.deleteAgency(Integer.parseInt(agencyID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,null)
        );
    }


}
