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

    @NotNull(message = "Address type can't be not null")
    @Getter @Setter
    private String addressType;

    @NotNull(message = "Delivery phone can't be not null")
    @Getter @Setter
    private String deliveryPhone;

    @Getter @Setter
    private String description;

    public CustomerAddressBook loadCustomerAddressDTO(CustomerAddressBook customerAddressBook) throws RuntimeException{
        customerAddressBook.setFullAddress(this.getFullAddress());
        customerAddressBook.setAddressType(this.getAddressType());
        customerAddressBook.setDeliveryPhone(this.getDeliveryPhone());
        customerAddressBook.setDescription(this.getDescription());
        return customerAddressBook;
    }
}
