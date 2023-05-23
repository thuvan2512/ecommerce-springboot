package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.CustomerAddressBookRepository;
import com.thunv.ecommerceou.services.CustomerAddressBookService;
import com.thunv.ecommerceou.services.UserService;
import com.thunv.ecommerceou.specifications.CustomerAddressBookSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerAddressBookServiceImpl implements CustomerAddressBookService {
    @Autowired
    private CustomerAddressBookRepository customerAddressBookRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerAddressBookSpecification customerAddressBookSpecification;

    @Override
    public CustomerAddressBook getAddressByID(Integer addressID) throws RuntimeException{
        return this.customerAddressBookRepository.findById(addressID).orElseThrow(() ->
                new RuntimeException("Can not find address with id = " + addressID));
    }

    @Override
    public List<CustomerAddressBook> getCustomerAddressBookByUserID(Integer userID) throws RuntimeException{
        try {
            User user = this.userService.getUserByID(userID);
            List<CustomerAddressBook> listResults = this.customerAddressBookRepository.findAll(this.customerAddressBookSpecification.getAddressBookByUser(user));
            return listResults;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            System.out.println(error_ms);
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public CustomerAddressBook createOrUpdateAddress(CustomerAddressBook customerAddressBook) throws  RuntimeException{
        try {
            return this.customerAddressBookRepository.save(customerAddressBook);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteCustomerAddressBook(int addressID) throws RuntimeException{
        try {
            if (this.customerAddressBookRepository.existsById(addressID) == false){
                throw new RuntimeException("Address does not exist");
            }
            CustomerAddressBook customerAddressBook = getAddressByID(addressID);
            customerAddressBook.setUpdatedDate(null);
            this.customerAddressBookRepository.save(customerAddressBook);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
