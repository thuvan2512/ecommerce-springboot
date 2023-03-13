package com.thunv.ecommerceou.controller;


import com.thunv.ecommerceou.dto.AddressBookCreateDTO;
import com.thunv.ecommerceou.dto.AgencyRegisterDTO;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.services.*;
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
@RequestMapping(value = "/api/location")
public class LocationController {
    @Autowired
    private LocationProvincesService locationProvincesService;
    @Autowired
    private LocationDistrictsService locationDistrictsService;
    @Autowired
    private LocationWardsService locationWardsService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerAddressBookService customerAddressBookService;
    @Autowired
    private Utils utils;
    @GetMapping(value = "/get-full-address/{wardID}")
    public ResponseEntity<ModelResponse> getFullAddressByWardID(@PathVariable(value = "wardID") String wardID){
        String ms;
        String code;
        String res = null;
        HttpStatus status;
        try {
            res = this.locationService.getFullAddressByWardID(wardID);
            status = HttpStatus.OK;
            ms = "Get full address successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/get-nearest-location")
    public ResponseEntity<ModelResponse> getNearestLocation(@RequestParam(defaultValue = "0") String latitude,
                                                            @RequestParam(defaultValue = "0") String longitude){
        String ms;
        String code;
        String res = null;
        HttpStatus status;
        try {
            Double lat = Double.parseDouble(latitude);
            Double lng = Double.parseDouble(longitude);
            List<Object[]> wardNearest = this.locationWardsService.getNearestLocationWard(lat, lng);
            if (wardNearest != null && wardNearest.size() > 0){
                res = this.locationService.getFullAddressByWardID(wardNearest.get(0)[0].toString());
                status = HttpStatus.OK;
                ms = "Get nearest locaton successfully";
                code = "200";
            }else {
                throw new RuntimeException("Not found nearest location");
            }
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/provinces/{provinceID}")
    public ResponseEntity<ModelResponse> getProvinceByID(@PathVariable(value = "provinceID") String provinceID){
        String ms;
        String code;
        LocationProvinces res = null;
        HttpStatus status;
        try {
            res = this.locationProvincesService.getLocationProvincesByID(provinceID);
            status = HttpStatus.OK;
            ms = "Get province successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/provinces/all")
    public ResponseEntity<ModelResponse> getAllProvinces() {
        String ms;
        String code;
        List<LocationProvinces> list = null;
        HttpStatus status;
        try {
            list = this.locationProvincesService.getListLocationProvinces();
            ms = "Get all province successfully";
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
    @GetMapping(value = "/districts/{districtID}")
    public ResponseEntity<ModelResponse> getDistrictByID(@PathVariable(value = "districtID") String districtID){
        String ms;
        String code;
        LocationDistricts res = null;
        HttpStatus status;
        try {
            res = this.locationDistrictsService.getLocationDistrictsByID(districtID);
            status = HttpStatus.OK;
            ms = "Get district successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/districts/all")
    public ResponseEntity<ModelResponse> getAllDistricts() {
        String ms;
        String code;
        List<LocationDistricts> list = null;
        HttpStatus status;
        try {
            list = this.locationDistrictsService.getAllLocationProvinces();
            ms = "Get all districts successfully";
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
    @GetMapping(value = "/districts/get-districts-by-province-id/{provinceID}")
    public ResponseEntity<ModelResponse> getDistrictByProvinceID(@PathVariable(value = "provinceID") String provinceID){
        String ms;
        String code;
        List<LocationDistricts> res = null;
        HttpStatus status;
        try {
            res = this.locationDistrictsService.getListLocationProvincesByProvinceID(provinceID);
            status = HttpStatus.OK;
            ms = "Get districts by province id successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @GetMapping(value = "/wards/{wardID}")
    public ResponseEntity<ModelResponse> getWardByID(@PathVariable(value = "wardID") String wardID){
        String ms;
        String code;
        LocationWards res = null;
        HttpStatus status;
        try {
            res = this.locationWardsService.getLocationWardsByID(wardID);
            status = HttpStatus.OK;
            ms = "Get ward successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/wards/all")
    public ResponseEntity<ModelResponse> getAllWards() {
        String ms;
        String code;
        List<LocationWards> list = null;
        HttpStatus status;
        try {
            list = this.locationWardsService.getAllLocationWards();
            ms = "Get all wards successfully";
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
    @GetMapping(value = "/wards/get-wards-by-province-id/{provinceID}")
    public ResponseEntity<ModelResponse> getWardsByProvinceID(@PathVariable(value = "provinceID") String provinceID){
        String ms;
        String code;
        List<LocationWards> res = null;
        HttpStatus status;
        try {
            res = this.locationWardsService.getListLocationWardsByProvinceID(provinceID);
            status = HttpStatus.OK;
            ms = "Get wards by province id successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "/wards/get-wards-by-district-id/{districtID}")
    public ResponseEntity<ModelResponse> getWardsByDistrictID(@PathVariable(value = "districtID") String districtID){
        String ms;
        String code;
        List<LocationWards> res = null;
        HttpStatus status;
        try {
            res = this.locationWardsService.getListLocationWardsByDistrictID(districtID);
            status = HttpStatus.OK;
            ms = "Get wards by district id successfully";
            code = "200";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }

    @GetMapping(value = "address-book/get-address-book-by-user-id/{userID}")
    public ResponseEntity<ModelResponse> getAddressBookByCustomerID(@PathVariable(value = "userID") String userID){
        String ms;
        String code;
        List<CustomerAddressBook> res = null;
        HttpStatus status;
        try {
            res = this.customerAddressBookService.getCustomerAddressBookByUserID(Integer.parseInt(userID));
            code = "200";
            status = HttpStatus.OK;
            ms = "Get address book successfully !!!";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(
                new ModelResponse(code,ms,res)
        );
    }
    @PostMapping(path = "address-book/create")
    public ResponseEntity<ModelResponse> createCustomerAddressBook(@RequestBody @Valid AddressBookCreateDTO addressBookCreateDTO,
                                                        BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = this.utils.getAllErrorValidation(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ModelResponse("400", "Invalid information", errors)
            );
        }
        String ms;
        String code;
        CustomerAddressBook res = null;
        try {
            User user = userService.getUserByID(addressBookCreateDTO.getCustomerID());
            CustomerAddressBook obj = new CustomerAddressBook();
            obj = addressBookCreateDTO.loadCustomerAddressDTO(obj);
            obj.setCreatedDate(new Date());
            obj.setUpdatedDate(new Date());
            obj.setCustomer(user);
            this.customerAddressBookService.createOrUpdateAddress(obj);
            res = obj;
            ms = "Create new address into address book successfully";
            code = "201";
        }catch (Exception ex){
            ms = ex.getMessage();
            code = "400";
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ModelResponse(code,ms,res)
        );
    }
    @DeleteMapping(path = "address-book/delete-by-id/{addressID}")
    public ResponseEntity<ModelResponse> deleteAddressByID(@PathVariable(value = "addressID") String addressID){
        String ms = "Delete address successfully";
        String code = "204";
        HttpStatus status = HttpStatus.OK;
        try {
            this.customerAddressBookService.deleteCustomerAddressBook(Integer.parseInt(addressID));
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
