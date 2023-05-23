package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import com.thunv.ecommerceou.models.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAddressBookRepository extends JpaRepository<CustomerAddressBook, Integer>, JpaSpecificationExecutor<CustomerAddressBook> {
    List<CustomerAddressBook> findByCustomer(User customer);
}
