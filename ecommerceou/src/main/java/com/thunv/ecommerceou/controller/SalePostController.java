package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.*;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.res.SearchResponse;
import com.thunv.ecommerceou.services.*;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/sale-post")
public class SalePostController {
    @Autowired
    private SalePostService salePostService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private SellStatusService sellStatusService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private Utils utils;
    @Autowired
    private Environment env;
    @GetMapping(value = "/{postID}")
    public ResponseEntity<ModelResponse> getSalePostByID(@PathVariable(value = "postID") String postID){
        String ms = "Get sale post successfully";
        String code = "200";
        SalePost res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            res = this.salePostService.getSalePostByID(Integer.parseInt(postID));
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
    public ResponseEntity<ModelResponse> getAllSalePost(){
        String ms = "Get all sale post successfully";
        String code = "200";
        List<SalePost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.salePostService.getAllSalePost();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }

    @GetMapping(value = "/get-all-sale-post-by-agency-id/{agencyID}")
    public ResponseEntity<ModelResponse> getAllSalePostByAgencyID(@PathVariable(value = "agencyID") String agencyID){
        String ms;
        String code;
        List<SalePost> list = null;
        HttpStatus status;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getAllSalePostByAgency(agency);
            ms = "Get all sale post by agency id successfully";
            code = "200";
            status = HttpStatus.OK;
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }

    @GetMapping(value = "/published/{agencyID}/all")
    public ResponseEntity<ModelResponse> getAllSalePostPublished(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get all sale post published successfully";
        String code = "200";
        List<SalePost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getListSalePostPublished(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/un-published/{agencyID}/all")
    public ResponseEntity<ModelResponse> getAllSalePostUnPublished(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get all sale post un published successfully";
        String code = "200";
        List<SalePost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getListSalePostUnpublished(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/get-star-average-rate/{postID}")
    public ResponseEntity<ModelResponse> getStarAverage(@PathVariable(value = "postID") String postID){
        String ms = "Get star average rate successfully";
        String code = "200";
        HttpStatus status = HttpStatus.OK;
        double starAvg;
        try {
            starAvg = this.salePostService.getAverageStarRateOfSalePost(Integer.parseInt(postID));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            starAvg = 0;
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,starAvg)
        );
    }
    @GetMapping(value = "/stats-by-category")
    public ResponseEntity<ModelResponse> getStatsByCategory(){
        String ms = "Get stats by category successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.salePostService.getStatsSalePostByCategory();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/stats-by-category/{agencyID}")
    public ResponseEntity<ModelResponse> getStatsByCategoryByAgency(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get stats by category successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getStatsSalePostByCategory(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/stats-revenue-month-by-year/{agencyID}/{year}")
    public ResponseEntity<ModelResponse> getStatsRevenueMonthByYear(@PathVariable(value = "agencyID") String agencyID,
                                                               @PathVariable(value = "year") String year){
        String ms = "Get stats revenue successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getStatsRevenueMonthByYear(agency,Integer.parseInt(year));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/stats-revenue-quarter-by-year/{agencyID}/{year}")
    public ResponseEntity<ModelResponse> getStatsRevenueQuarterByYear(@PathVariable(value = "agencyID") String agencyID,
                                                               @PathVariable(value = "year") String year){
        String ms = "Get stats revenue successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getStatsRevenueQuarterByYear(agency,Integer.parseInt(year));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/stats-revenue-by-year/{agencyID}/")
    public ResponseEntity<ModelResponse> getStatsRevenueByYear(@PathVariable(value = "agencyID") String agencyID){
        String ms = "Get stats revenue successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            Agency agency = this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            list = this.salePostService.getStatsRevenueYear(agency);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/wish-list/{userID}")
    public ResponseEntity<ModelResponse> getWishListByUserID(@PathVariable(value = "userID") String userID){
        String ms = "Get all sale post like by user successfully";
        String code = "200";
        List<SalePost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            list = this.salePostService.getListSalePostLikeByUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PatchMapping(path = "/published/{postID}")
    public ResponseEntity<ModelResponse> publishSalePost(@PathVariable(value = "postID") String postID){
        String ms = "Publish sale post successfully";
        String code = "200";
        SalePost res = null;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            res = this.salePostService.publishSalePost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PostMapping(path = "/create/{agencyID}")
    public ResponseEntity<ModelResponse> createSalePost(@PathVariable(value = "agencyID")  String agencyID,
            @RequestBody @Valid SalePostCreateDTO salePostCreateDTO,
                                                        BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Create sale post successfully";
        String code = "201";
        SalePost res = null;
        try {
            Agency agency =  this.agencyService.getAgencyByID(Integer.parseInt(agencyID));
            salePostCreateDTO.setAgency(agency);
            res = this.salePostService.createSalePost(salePostCreateDTO);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PostMapping(value = "/search")
    public ResponseEntity<ModelResponse> searchSalePost(@RequestBody @Valid SearchSalePostDTO searchSalePostDTO,
                                                        BindingResult result){
        try {
            if (result.hasErrors()) {
                Map<String, String> errors = this.utils.getAllErrorValidation(result);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ModelResponse("400", "Invalid information", errors)
                );
            }
            String ms = "Search sale post successfully";
            String code = "200";
            List<SalePost> list = new ArrayList<>();
            HttpStatus status = HttpStatus.OK;
            try {
                list = this.salePostService.searchSalePost(searchSalePostDTO);
            }catch (Exception ex){
                ms = ex.getMessage();
                code = "400";
                status = HttpStatus.BAD_REQUEST;
            }
            Integer total;
            Integer totalPage;
            total = this.salePostService.countSalePost();
            totalPage = this.salePostService.getTotalPageSalePost(total);
            SearchResponse searchResponse = new SearchResponse(totalPage,
                    Integer.parseInt(this.env.getProperty("post.paginate.size")),
                    total,
                    searchSalePostDTO.getPage(),list.size(), Arrays.asList(list.toArray()));
            return ResponseEntity.status(status).body(
                    new ModelResponse(code,ms,searchResponse)
            );
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ModelResponse("400","Failed", null)
        );
    }
    @GetMapping(value = "/get-keywords-suggest-for-search")
    public ResponseEntity<ModelResponse> getSuggestKeywordForSearch(@RequestParam(defaultValue = "") String keyword){
        String ms;
        String code;
        List<Object> res = null;
        HttpStatus status;
        try {
            res = this.salePostService.getSuggestForSearchProducts(keyword);
            ms = "Get suggest successfully !!!";
            code = "200";
            status = HttpStatus.OK;
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PutMapping(path = "/{postID}")
    public ResponseEntity<ModelResponse> updateSalePost(@RequestBody @Valid SalePostUpdateDTO salePostUpdateDTO,
                                                        @PathVariable(value = "postID") String postID,
                                                        BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Update sale post successfully";
        String code = "200";
        SalePost res = null;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            if (salePostUpdateDTO.getCategoryID() != null){
                salePost.setCategory(this.categoryService.getCategoryByID(salePostUpdateDTO.getCategoryID()));
            }
            if (salePostUpdateDTO.getSellStatusID() != null){
                salePost.setSellStatus(this.sellStatusService.getSellStatusByID(salePostUpdateDTO.getSellStatusID()));
            }
            salePost = salePostUpdateDTO.loadSalePostFromDTO(salePost);
            res = this.salePostService.updateSalePost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(path = "/un-published/{postID}")
    public ResponseEntity<ModelResponse> unPublishSalePost(@PathVariable(value = "postID") String postID){
        String ms = "Un publish sale post successfully";
        String code = "200";
        SalePost res = null;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            res = this.salePostService.unPublishSalePost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "/{postID}")
    public ResponseEntity<ModelResponse> deleteSalePost(@PathVariable(value = "postID") String postID){
        String ms = "Delete sale post successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.salePostService.deleteSalePost(Integer.parseInt(postID));
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
