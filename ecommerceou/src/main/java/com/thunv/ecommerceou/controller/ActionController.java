package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.CommentDTO;
import com.thunv.ecommerceou.jwt.JwtTokenUtils;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.*;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/action")
public class ActionController {
    @Autowired
    private LikePostService likePostService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private SalePostService salePostService;
    @Autowired
    private FollowService followService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private Utils utils;

    @GetMapping(value = "/like/{postID}")
    public ResponseEntity<ModelResponse> likePostAction(@PathVariable(value = "postID") String postID,
                                                        HttpServletRequest request) {
        String ms = "Like post successfully";
        String code = "201";
        LikePost res = null;
        User user = null;
        HttpStatus status = HttpStatus.CREATED;
        try {
            if (request.getHeader("Authorization") == null) {
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0) {
                throw new RuntimeException("Can not find current user");
            }
            user = list.get(0);
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            res = this.likePostService.createLikePost(user,salePost);
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }
    @GetMapping(value = "/like/{postID}/count-like")
    public ResponseEntity<ModelResponse> countLikeBySalePost(@PathVariable(value = "postID") String postID){
        String ms = "Get num of like by sale post successfully";
        String code = "200";
        Integer countLike = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            countLike = this.likePostService.countLikeByPost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,countLike)
        );
    }
    @GetMapping(value = "/like/state/{postID}")
    public ResponseEntity<ModelResponse> getLikeStatePostAction(@PathVariable(value = "postID") String postID,
                                                        HttpServletRequest request) {
        String ms = "Get state like post successfully";
        String code = "200";
        boolean state = false;
        User user = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null) {
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0) {
                throw new RuntimeException("Can not find current user");
            }
            user = list.get(0);
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            state = this.likePostService.getLikeStatePostByUser(user,salePost);
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms,state)
        );
    }
    @GetMapping(value = "/follow/{agencyID}")
    public ResponseEntity<ModelResponse> followAgencyAction(@PathVariable(value = "agencyID") String agencyID,
                                                        HttpServletRequest request) {
        String ms = "Follow agency successfully";
        String code = "201";
        FollowAgency res = null;
        User user = null;
        HttpStatus status = HttpStatus.CREATED;
        try {
            if (request.getHeader("Authorization") == null) {
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0) {
                throw new RuntimeException("Can not find current user");
            }
            user = list.get(0);
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            res = this.followService.createFollow(user,agency);
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, res)
        );
    }
    @GetMapping(value = "/follow/state/{agencyID}")
    public ResponseEntity<ModelResponse> getFollowStateAgencyAction(@PathVariable(value = "agencyID") String agencyID,
                                                                HttpServletRequest request) {
        String ms = "Get state follow agency successfully";
        String code = "200";
        boolean state = false;
        User user = null;
        HttpStatus status = HttpStatus.OK;
        try {
            if (request.getHeader("Authorization") == null) {
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0) {
                throw new RuntimeException("Can not find current user");
            }
            user = list.get(0);
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            state = this.followService.getFollowStateAgencyByUser(user,agency);
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms,state)
        );
    }
    @GetMapping(value = "/comment/{commentID}")
    public ResponseEntity<ModelResponse> getCommentByID(@PathVariable(value = "commentID") String commentID){
        String ms = "Get comment successfully";
        String code = "200";
        CommentPost res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            res = this.commentService.getCommentPostByID(Integer.parseInt(commentID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @GetMapping(value = "/comment/all")
    public ResponseEntity<ModelResponse> getAllComment(){
        String ms = "Get all comment successfully";
        String code = "200";
        List<CommentPost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.commentService.getAllCommentPost();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/comment/{postID}/all")
    public ResponseEntity<ModelResponse> getAllCommentBySalePost(@PathVariable(value = "postID") String postID){
        String ms = "Get all comment by sale post successfully";
        String code = "200";
        List<CommentPost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            list = this.commentService.getListCommentByPost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/comment/{postID}/count-comment")
    public ResponseEntity<ModelResponse> countCommentBySalePost(@PathVariable(value = "postID") String postID){
        String ms = "Get num of comment by sale post successfully";
        String code = "200";
        Integer countComment = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            countComment = this.commentService.countCommentByPost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,countComment)
        );
    }
    @PostMapping(path = "/comment/create/{postID}")
    public ResponseEntity<ModelResponse> createCommentPost(@RequestBody @Valid CommentDTO commentDTO,
                                                           @PathVariable(value = "postID") String postID,
                                                           HttpServletRequest request,
                                                           BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Insert comment successfully";
        String code = "201";
        CommentPost res = null;
        try {
            User author;
            if (request.getHeader("Authorization") == null) {
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0) {
                throw new RuntimeException("Can not find current user");
            }
            author = list.get(0);
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            commentDTO.setAuthor(author);
            commentDTO.setPost(salePost);
            res = this.commentService.createCommentPost(commentDTO);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "/comment/{commentID}")
    public ResponseEntity<ModelResponse> deleteComment(@PathVariable(value = "commentID") String commentID){
        String ms = "Delete comment successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.commentService.deleteCommentPost(Integer.parseInt(commentID));
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
