package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.jwt.JwtTokenUtils;
import com.thunv.ecommerceou.models.pojo.CensorshipAgency;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.CensorshipAgencyService;
import com.thunv.ecommerceou.services.NotifyService;
import com.thunv.ecommerceou.services.RenewalService;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/censorship")
public class CensorshipAgencyController {
    @Autowired
    private CensorshipAgencyService censorshipAgencyService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private RenewalService renewalService;
    @GetMapping(value = "/{censorshipID}")
    public ResponseEntity<ModelResponse> getCensorByID(@PathVariable(value = "censorshipID") String censorshipID){
        String ms = "Get censorship agency successfully";
        String code = "200";
        CensorshipAgency res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            res = this.censorshipAgencyService.getCensorshipByID(Integer.parseInt(censorshipID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @GetMapping(value = "/all")
    public ResponseEntity<ModelResponse> getAllCensorshipAgency(){
        String ms = "Get all censorship agency successfully";
        String code = "200";
        List<CensorshipAgency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.censorshipAgencyService.getAllCensorshipAgency();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/uncensored")
    public ResponseEntity<ModelResponse> getUncensored(){
        String ms = "Get all uncensored successfully";
        String code = "200";
        List<CensorshipAgency> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.censorshipAgencyService.getUncensored();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PatchMapping(value = "/denied/{censorshipID}")
    public ResponseEntity<ModelResponse> deniedCensorshipAgency(@PathVariable(value = "censorshipID") String censorshipID,
                                                          HttpServletRequest request){
        String ms = "Agency censored";
        String code = "200";
        CensorshipAgency res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            User user = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(
                    request.getHeader("Authorization")
                            .split("\s")[1])).get(0);
            if (user.getRole().getId() != 1){
                code = "403";
                status = HttpStatus.FORBIDDEN;
                throw new RuntimeException("No admin permission");
            }
            CensorshipAgency censorshipAgency = this.censorshipAgencyService.getCensorshipByID(Integer.parseInt(censorshipID));
            res = this.censorshipAgencyService.censorAgency(user,censorshipAgency,false);
            String recipient = String.format("user-%s", censorshipAgency.getManager().getId());
            String title = "Your agency moderation request denied";
            String detail = "Your agency moderation request denied. Please contact us for more information.";
            String type = "Request Denied";
            this.notifyService.pushNotify(recipient, censorshipAgency.getAgency().getAvatar(), title, detail, type);
        }catch (Exception ex){
            ms = ex.getMessage();
            if (code.equals("200")){
                code = "400";
                status = HttpStatus.BAD_REQUEST;
            }
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(value = "/accepted/{censorshipID}")
    public ResponseEntity<ModelResponse> acceptedCensorshipAgency(@PathVariable(value = "censorshipID") String censorshipID,
                                                          HttpServletRequest request){
        String ms = "Agency censored";
        String code = "200";
        CensorshipAgency res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            User user = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(
                    request.getHeader("Authorization")
                            .split("\s")[1])).get(0);
            if (user.getRole().getId() != 1){
                code = "403";
                status = HttpStatus.FORBIDDEN;
                throw new RuntimeException("No admin permission");
            }
            CensorshipAgency censorshipAgency = this.censorshipAgencyService.getCensorshipByID(Integer.parseInt(censorshipID));
            res = this.censorshipAgencyService.censorAgency(user,censorshipAgency,true);
            this.renewalService.initialTrialForNewAgency(censorshipAgency.getAgency().getId());
            String recipientAgency = String.format("agency-%s", censorshipAgency.getAgency().getId());
            String titleAgency = "You have received the trial package";
            String detailAgency = "You have received the trial package. You can go to your agent management page now !!!";
            String typeAgency = "Receive trial package";
            this.notifyService.pushNotify(recipientAgency, censorshipAgency.getAgency().getAvatar(), titleAgency, detailAgency, typeAgency);
            String recipient = String.format("user-%s", censorshipAgency.getManager().getId());
            String title = "Your agency moderation request accepted";
            String detail = "Your agency moderation request accepted. You can go to your agent management page now !!!";
            String type = "Request Accepted";
            this.notifyService.pushNotify(recipient, censorshipAgency.getAgency().getAvatar(), title, detail, type);
        }catch (Exception ex){
            ms = ex.getMessage();
            if (code.equals("200")){
                code = "400";
                status = HttpStatus.BAD_REQUEST;
            }
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "/{censorshipID}")
    public ResponseEntity<ModelResponse> deleteCensorshipAgency(@PathVariable(value = "censorshipID") String censorshipID){
        String ms = "Delete censorship agency successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.censorshipAgencyService.deleteCensorshipAgency(Integer.parseInt(censorshipID));
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
