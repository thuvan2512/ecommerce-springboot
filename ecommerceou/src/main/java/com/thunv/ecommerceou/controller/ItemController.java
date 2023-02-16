package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.dto.ItemCreateDTO;
import com.thunv.ecommerceou.dto.ItemUpdateDTO;
import com.thunv.ecommerceou.models.pojo.ItemPost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.ItemService;
import com.thunv.ecommerceou.services.SalePostService;
import com.thunv.ecommerceou.utils.Utils;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping(value = "/api/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private SalePostService salePostService;
    @Autowired
    private Utils utils;
    @GetMapping(value = "/{itemID}")
    public ResponseEntity<ModelResponse> getItemByID(@PathVariable(value = "itemID") String itemID){
        String ms = "Get item successfully";
        String code = "200";
        ItemPost res = null;
        HttpStatus status = HttpStatus.OK;
        try {
            res = this.itemService.getItemPostByID(Integer.parseInt(itemID));
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
    public ResponseEntity<ModelResponse> getAllItem(){
        String ms = "Get all items successfully";
        String code = "200";
        List<ItemPost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.itemService.getAllItemPost();
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/top-seller/{top}")
    public ResponseEntity<ModelResponse> getTopSellerItem(@PathVariable(value = "top") String top){
        String ms = "Get top seller items successfully";
        String code = "200";
        List<Object[]> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            list = this.salePostService.getTopSeller(Integer.parseInt(top));
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @GetMapping(value = "/{postID}/all")
    public ResponseEntity<ModelResponse> getAllItemOfSalePost(@PathVariable(value = "postID") String postID){
        String ms = "Get all items of post successfully";
        String code = "200";
        List<ItemPost> list = null;
        HttpStatus status = HttpStatus.OK;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            list = this.itemService.getListItemBySalePost(salePost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,list)
        );
    }
    @PostMapping(path = "/create/{postID}")
    public ResponseEntity<ModelResponse> createItem(@RequestBody @Valid ItemCreateDTO itemCreateDTO,
                                                    @PathVariable(value = "postID") String postID,
                                                    BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Insert item successfully";
        String code = "201";
        ItemPost res = null;
        try {
            SalePost salePost = this.salePostService.getSalePostByID(Integer.parseInt(postID));
            itemCreateDTO.setSalePost(salePost);
            res = this.itemService.createItemPost(itemCreateDTO);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PutMapping(path = "/{itemID}")
    public ResponseEntity<ModelResponse> updateItem(@RequestBody @Valid ItemUpdateDTO itemUpdateDTO,
                                                    @PathVariable(value = "itemID") String itemID,
                                                    BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms = "Update item successfully";
        String code = "200";
        ItemPost res = null;
        try {
            ItemPost itemPost = this.itemService.getItemPostByID(Integer.parseInt(itemID));
            itemUpdateDTO.loadItemFromDTO(itemPost);
            res = this.itemService.updateItemPost(itemPost);
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "/{itemID}")
    public ResponseEntity<ModelResponse> deletePicturePost(@PathVariable(value = "itemID") String itemID){
        String ms = "Delete item successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.itemService.deleteItemPost(Integer.parseInt(itemID));
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
