package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.CommentService;
import com.thunv.ecommerceou.services.FollowService;
import com.thunv.ecommerceou.services.LikePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/general-stats")
public class GeneralStatsController {
    @Autowired
    private FollowService followService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private LikePostService likePostService;
    @Autowired
    private CommentService commentService;
    @GetMapping(value = "/view-agency/{agencyID}")
    public ResponseEntity<ModelResponse> getGeneralInfoOfAgency(@PathVariable(value = "agencyID") String agencyID) {
        String ms = "Successfully !!!";
        String code = "200";
        Map<Object, Object> list = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            int followByAgency = this.followService.countFollowByAgency(agency);
            int likeByAgency = this.likePostService.countLikeByAgency(agency);
            double avgStarByAgency = this.commentService.getAverageStarByAgency(agency);
            int commentByAgency = this.commentService.countCommentByAgency(agency);
            list.put("numOfFollow", followByAgency);
            list.put("numOfLike", likeByAgency);
            list.put("averageStar", avgStarByAgency);
            list.put("numOfComment", commentByAgency);
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }

    @GetMapping(value = "/view-admin")
    public ResponseEntity<ModelResponse> getGeneralInfoOfAdmin() {
        String ms = "Successfully !!!";
        String code = "200";
        Map<Object, Object> list = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.agencyService.getGeneralStatsForAdminView();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
}
