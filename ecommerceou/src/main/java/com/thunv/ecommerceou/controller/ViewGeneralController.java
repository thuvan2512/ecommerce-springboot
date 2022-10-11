package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ViewGeneralController {
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private SellStatusService sellStatusService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AgencyFieldService agencyFieldService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthProviderService authProviderService;
    @Autowired
    private GenderService genderService;

    @GetMapping(value = "/order-state/{stateID}")
    public ResponseEntity<ModelResponse> getOrderStateByID(@PathVariable(value = "stateID") String stateID) {
        String ms = "Get order state successfully";
        String code = "200";
        OrderState orderState = null;
        HttpStatus status = HttpStatus.OK;
        try {
            orderState = this.orderStateService.getOrderStateByID(Integer.parseInt(stateID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, orderState)
        );
    }
    @GetMapping(value = "/order-state/all")
    public ResponseEntity<ModelResponse> getAllOrderState() {
        String ms = "Get all order state successfully";
        String code = "200";
        List<OrderState> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.orderStateService.getAllOrderState();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/payment-type/{paymentID}")
    public ResponseEntity<ModelResponse> getPaymentTypeByID(@PathVariable(value = "paymentID") String paymentID) {
        String ms = "Get payment type successfully";
        String code = "200";
        PaymentType paymentType = null;
        HttpStatus status = HttpStatus.OK;
        try {
            paymentType = this.paymentTypeService.getPaymentTypeByID(Integer.parseInt(paymentID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, paymentID)
        );
    }
    @GetMapping(value = "/payment-type/all")
    public ResponseEntity<ModelResponse> getAllPaymentType() {
        String ms = "Get all payment type successfully";
        String code = "200";
        List<PaymentType> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.paymentTypeService.getAllPaymentType();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/sell-status/{statusID}")
    public ResponseEntity<ModelResponse> getSellStatusByID(@PathVariable(value = "statusID") String statusID) {
        String ms = "Get status successfully";
        String code = "200";
        SellStatus sellStatus = null;
        HttpStatus status = HttpStatus.OK;
        try {
            sellStatus = this.sellStatusService.getSellStatusByID(Integer.parseInt(statusID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, sellStatus)
        );
    }

    @GetMapping(value = "/sell-status/all")
    public ResponseEntity<ModelResponse> getAllSellStatus() {
        String ms = "Get all status successfully";
        String code = "200";
        List<SellStatus> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.sellStatusService.getAllSellStatus();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/category/{categoryID}")
    public ResponseEntity<ModelResponse> getCategoryByID(@PathVariable(value = "categoryID") String categoryID) {
        String ms = "Get category successfully";
        String code = "200";
        Category category = null;
        HttpStatus status = HttpStatus.OK;
        try {
            category = this.categoryService.getCategoryByID(Integer.parseInt(categoryID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, category)
        );
    }

    @GetMapping(value = "/category/all")
    public ResponseEntity<ModelResponse> getAllCategories() {
        String ms = "Get all categories successfully";
        String code = "200";
        List<Category> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.categoryService.getAllCategory();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/agency-field/{fieldID}")
    public ResponseEntity<ModelResponse> getAgentFieldByID(@PathVariable(value = "fieldID") String fieldID) {
        String ms = "Get field successfully";
        String code = "200";
        AgentField agentField = null;
        HttpStatus status = HttpStatus.OK;
        try {
            agentField = this.agencyFieldService.getAgentFieldByID(Integer.parseInt(fieldID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, agentField)
        );
    }

    @GetMapping(value = "/agency-field/all")
    public ResponseEntity<ModelResponse> getAllAgentField() {
        String ms = "Get all fields successfully";
        String code = "200";
        List<AgentField> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.agencyFieldService.getAllAgencyField();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/role/{roleID}")
    public ResponseEntity<ModelResponse> getRoleByID(@PathVariable(value = "roleID") String roleID) {
        String ms = "Get role successfully";
        String code = "200";
        Role role = null;
        HttpStatus status = HttpStatus.OK;
        try {
            role = this.roleService.getRoleByID(Integer.parseInt(roleID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, role)
        );
    }

    @GetMapping(value = "/role/all")
    public ResponseEntity<ModelResponse> getAllRole() {
        String ms = "Get all role successfully";
        String code = "200";
        List<Role> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.roleService.getAllRole();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/gender/{genderID}")
    public ResponseEntity<ModelResponse> getGenderByID(@PathVariable(value = "genderID") String genderID) {
        String ms = "Get gender successfully";
        String code = "200";
        Gender gender = null;
        HttpStatus status = HttpStatus.OK;
        try {
            gender = this.genderService.getGenderByID(Integer.parseInt(genderID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, gender)
        );
    }

    @GetMapping(value = "/gender/all")
    public ResponseEntity<ModelResponse> getAllGender() {
        String ms = "Get all gender successfully";
        String code = "200";
        List<Gender> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.genderService.getAllGender();
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, list)
        );
    }
    @GetMapping(value = "/auth-provider/{authID}")
    public ResponseEntity<ModelResponse> getAuthProviderByID(@PathVariable(value = "authID") String authID) {
        String ms = "Get auth provider successfully";
        String code = "200";
        AuthProvider authProvider = null;
        HttpStatus status = HttpStatus.OK;
        try {
            authProvider = this.authProviderService.getAuthProviderByID(Integer.parseInt(authID));
        } catch (Exception ex) {
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code, ms, authProvider)
        );
    }

    @GetMapping(value = "/auth-provider/all")
    public ResponseEntity<ModelResponse> getAllAuthProvider() {
        String ms = "Get all auth provider successfully";
        String code = "200";
        List<AuthProvider> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.authProviderService.getAllAuthProvider();
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
