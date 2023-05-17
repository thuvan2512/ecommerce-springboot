package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.CartDTO;
import com.thunv.ecommerceou.dto.CategoryDTO;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.CategoryService;
import com.thunv.ecommerceou.services.ManageErrorLogService;
import com.thunv.ecommerceou.utils.Utils;
import io.swagger.models.auth.In;
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
@RequestMapping(value = "/api/admin")
public class AdminController {
    @Autowired
    private Utils utils;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ManageErrorLogService manageErrorLogService;


    @GetMapping(value = "/get-all-err-logs")
    public ResponseEntity<ModelResponse> getAllManageErrorLogByType(@RequestParam(defaultValue = "") String logType) {
        String ms;
        String code;
        List<ManageErrorLog> list = null;
        HttpStatus status;
        try {
            list = this.manageErrorLogService.getAllManageErrorLogs(logType);
            ms = "Get all logs successfully";
            code = "200";
            status = HttpStatus.OK;

        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @PatchMapping(value = "/change-status-resolve-problem/{logID}")
    public ResponseEntity<ModelResponse> changeStatusResolveErrorLog(@PathVariable(value = "logID") String logID) {
        String ms;
        String code;
        List<ManageErrorLog> list = null;
        HttpStatus status;
        try {
            ManageErrorLog manageErrorLog = this.manageErrorLogService.getLogByID(Integer.parseInt(logID));
            Integer statusResolve = manageErrorLog.getIsResolve();
            if (statusResolve == 1){
                manageErrorLog.setIsResolve(0);
            }else {
                manageErrorLog.setIsResolve(1);
            }
            this.manageErrorLogService.saveThirdPartyErrorLog(manageErrorLog);
            ms = "Change status resolve logs successfully";
            code = "200";
            status = HttpStatus.OK;

        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/get-all-categories")
    public ResponseEntity<ModelResponse> getAllCategoriesForAdmin() {
        String ms = "Get all categories successfully";
        String code = "200";
        List<Category> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.categoryService.getAllCategoryForAdmin();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }


    @PostMapping(path = "/add-new-category")
    public ResponseEntity<ModelResponse> addNewCategory(@RequestBody @Valid CategoryDTO cateDTO,
                                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Add new category successfully";
        String code = "201";
        Category res = null;
        try {
            if (cateDTO != null){
                Category category = new Category();
                category.setName(cateDTO.getName());
                category.setAvatar(cateDTO.getAvatar());
                category.setNameVi(cateDTO.getNameVi());
                res = this.categoryService.createCategory(category);
            }else {
                ms = "Invalid infomation of category object";
                code = "400";
            }
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code, ms, res)
        );
    }

    @PostMapping(path = "/update-category/{cateID}")
    public ResponseEntity<ModelResponse> updateCategory(@RequestBody CategoryDTO cateDTO,
                                                        @PathVariable(value = "cateID") String cateID,
                                                        BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Update category successfully";
        String code = "200";
        Category res = null;
        try {
            if (cateDTO != null){
                Category category = this.categoryService.getCategoryByID(Integer.parseInt(cateID));
                if (category != null){
                    if (cateDTO.getName() != null){
                        category.setName(cateDTO.getName());
                    }
                    if (cateDTO.getAvatar() != null){
                        category.setAvatar(cateDTO.getAvatar());
                    }
                    if (cateDTO.getActive() != null){
                        category.setActive(cateDTO.getActive());
                    }
                    if (cateDTO.getNameVi() != null){
                        category.setNameVi(cateDTO.getNameVi());
                    }
                    res = this.categoryService.updateCategory(category);
                }
            }else {
                ms = "Invalid infomation of category object";
                code = "400";
            }
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code, ms, res)
        );
    }
}
