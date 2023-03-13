package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;

import java.util.List;

public interface CustomerAddressBookService {
    CustomerAddressBook getAddressByID(Integer addressID);
    List<CustomerAddressBook> getCustomerAddressBookByUserID(Integer userID);
    CustomerAddressBook createOrUpdateAddress(CustomerAddressBook customerAddressBook);
    boolean deleteCustomerAddressBook(int addressID);
}
