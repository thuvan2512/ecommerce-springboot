package com.thunv.ecommerceou.dto;

import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class AddressBookCreateDTO {
    @NotNull(message = "CustomerID can't be not null")
    @Getter
    @Setter
    private Integer customerID;

    @NotNull(message = "Full address can't be not null")
    @Getter @Setter
    private String fullAddress;

    @NotNull(message = "Customer name can't be not null")
    @Getter @Setter
    private String customerName;

    @NotNull(message = "Address type can't be not null")
    @Getter @Setter
    private String addressType;

    @NotNull(message = "Delivery phone can't be not null")
    @Getter @Setter
    private String deliveryPhone;

    @Getter @Setter
    private String description;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String toProvinceName;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private Integer provinceID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String toDistrictName;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private Integer districtID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String toWardName;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String wardID;

    @NotNull(message = "Can't be not null")
    @Getter @Setter
    private String toAddress;


    public CustomerAddressBook loadCustomerAddressDTO(CustomerAddressBook customerAddressBook) throws RuntimeException{
        customerAddressBook.setFullAddress(this.getFullAddress());
        customerAddressBook.setCustomerName(this.getCustomerName());
        customerAddressBook.setAddressType(this.getAddressType());
        customerAddressBook.setDeliveryPhone(this.getDeliveryPhone());
        customerAddressBook.setDescription(this.getDescription());
        customerAddressBook.setToProvinceName(this.getToProvinceName());
        customerAddressBook.setProvinceID(this.getProvinceID());
        customerAddressBook.setToDistrictName(this.getToDistrictName());
        customerAddressBook.setDistrictID(this.getDistrictID());
        customerAddressBook.setToWardName(this.getToWardName());
        customerAddressBook.setWardID(this.getWardID());
        customerAddressBook.setToAddress(this.getToAddress());
        return customerAddressBook;
    }
}
