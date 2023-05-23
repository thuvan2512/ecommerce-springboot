package com.thunv.ecommerceou.specifications;

import com.thunv.ecommerceou.models.pojo.CustomerAddressBook;
import com.thunv.ecommerceou.models.pojo.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerAddressBookSpecification {
    public Specification<CustomerAddressBook> getAddressBookByUser(User user) {
        return new Specification<CustomerAddressBook>() {
            @Override
            public Predicate toPredicate(Root<CustomerAddressBook> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList.add(criteriaBuilder.equal(root.get("customer").get("id").as(Integer.class), user.getId()));
                predicatesList.add(criteriaBuilder.isNotNull(root.get("updatedDate")));
                return criteriaBuilder.and(predicatesList.toArray(new Predicate[]{}));
            }
        };
    }
}
