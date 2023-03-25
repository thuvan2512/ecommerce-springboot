package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.CartDTO;
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
@RequestMapping(value = "/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private CustomerAddressBookService customerAddressBookService;
    @Autowired
    private Utils utils;
    @Autowired
    private PaymentTypeService paymentTypeService;

    @GetMapping(value = "/get-cart/{userID}")
    public ResponseEntity<ModelResponse> getCartByUserID(@PathVariable(value = "userID") String userID){
        String ms = "Get cart successfully";
        String code = "200";
        Cart res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            res = this.cartService.getCartByUser(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @GetMapping(value = "/get-total-price-in-cart/{userID}")
    public ResponseEntity<ModelResponse> getTotalPriceInCart(@PathVariable(value = "userID") String userID){
        String ms = "Get total price in cart successfully";
        String code = "200";
        double totalPrice = 0;
        HttpStatus status = HttpStatus.OK;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            Cart cart = this.cartService.getCartByUser(user);
            totalPrice = this.cartService.getTotalPriceInCart(cart);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,totalPrice)
        );
    }

    @GetMapping(value = "/get-check-out-info-before-payment/{userID}")
    public ResponseEntity<ModelResponse> getCheckOutInfoBeforePayment(@PathVariable(value = "userID") String userID){
        String ms;
        String code;
        List<Object> mapResult = null;
        HttpStatus status;
        try {
            User user = this.userService.getUserByID(Integer.parseInt(userID));
            mapResult = this.cartService.getCheckOutPayment(user);
            ms = "Get check out successfully !!!";
            status = HttpStatus.OK;
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,mapResult)
        );
    }

    @PostMapping(path = "/add-to-cart")
    public ResponseEntity<ModelResponse> addToCart(@RequestBody @Valid CartDTO cartDTO,
                                                   HttpServletRequest request,
                                                   BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Add item to cart successfully";
        String code = "201";
        List<CartItem> res = null;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            ItemPost itemPost = this.itemService.getItemPostByID(cartDTO.getItemID());
            User user = list.get(0);
            res = this.cartService.addToCart(user,itemPost,cartDTO.getQuantity());
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PostMapping(path = "/payment-cart/{paymentTypeID}/{addressID}")
    public ResponseEntity<ModelResponse> paymentCart(@PathVariable(value = "paymentTypeID") String paymentTypeID,
                                                     @PathVariable(value = "addressID") String addressID,
                                                     @RequestBody Map<Integer, Map<String, String>> mapServiceInfo,
                                                   HttpServletRequest request){
        String ms = "Payment cart successfully";
        String code = "200";
        List<CartItem> res = null;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            PaymentType paymentType = this.paymentTypeService.getPaymentTypeByID(Integer.parseInt(paymentTypeID));
            User user = list.get(0);
            CustomerAddressBook customerAddressBook = this.customerAddressBookService.getAddressByID(Integer.parseInt(addressID));
            if (customerAddressBook.getCustomer().getId() != user.getId()){
                throw new RuntimeException("Invalid address !!!");
            }
            res = this.cartService.paymentCart(user,paymentType, customerAddressBook, mapServiceInfo);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }


    @PostMapping(path = "/get-momo-payment-info")
    public ResponseEntity<ModelResponse> getMomoPaymentInfo(HttpServletRequest request,
                                                            @RequestParam(defaultValue = "0") String amountShipFee){
        String ms = "Get payment info successfully";
        String code = "200";
        Map<String, String> res = null;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            Double shipFee = Double.parseDouble(amountShipFee);
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            User user = list.get(0);
            res = this.cartService.getMomoPaymentInfo(user, shipFee);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PatchMapping(path = "/update-cart")
    public ResponseEntity<ModelResponse> updateCart(@RequestBody @Valid CartDTO cartDTO,
                                                   HttpServletRequest request,
                                                   BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Update successfully";
        String code = "200";
        List<CartItem> res = null;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            ItemPost itemPost = this.itemService.getItemPostByID(cartDTO.getItemID());
            User user = list.get(0);
            res = this.cartService.updateCart(user,itemPost,cartDTO.getQuantity());
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }

    @DeleteMapping(path = "/remove-item/{itemID}")
    public ResponseEntity<ModelResponse> removeItemInCart(@PathVariable(value = "itemID") String itemID,
                                                    HttpServletRequest request){
        String ms = "Remove item in cart successfully";
        String code = "204";
        List<CartItem> res = null;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            ItemPost itemPost = this.itemService.getItemPostByID(Integer.parseInt(itemID));
            User user = list.get(0);
            res = this.cartService.removeItemFromCart(user,itemPost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PutMapping(path = "/clear-cart")
    public ResponseEntity<ModelResponse> clearCart(HttpServletRequest request){
        String ms = "Clear cart successfully";
        String code = "200";
        List<CartItem> res = null;
        try {
            if (request.getHeader("Authorization") == null){
                throw new RuntimeException("Authorization info not found");
            }
            String token = request.getHeader("Authorization").split("\s")[1];
            List<User> list = this.userService.getUserByUsername(jwtTokenUtils.getUsernameFromToken(token));
            if (list.size() == 0){
                throw new RuntimeException("Can not find current user");
            }
            User user = list.get(0);
            res = this.cartService.clearCart(user);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
}
