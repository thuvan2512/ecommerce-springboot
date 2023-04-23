package com.thunv.ecommerceou.repositories.specifications;

import com.thunv.ecommerceou.models.pojo.SalePost;
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
public class SalePostSpecification {
    public Specification<SalePost> salePostValidToPublish() {
        return new Specification<SalePost>() {
            @Override
            public Predicate toPredicate(Root<SalePost> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList.add(criteriaBuilder.equal(root.get("isActive").as(Integer.class), 1));
                predicatesList.add(criteriaBuilder.equal(root.get("agency").get("isActive").as(Integer.class), 1));
                return criteriaBuilder.and(predicatesList.toArray(new Predicate[]{}));
            }
        };
    }
}
