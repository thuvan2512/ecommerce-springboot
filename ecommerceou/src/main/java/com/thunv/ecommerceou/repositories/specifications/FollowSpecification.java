package com.thunv.ecommerceou.repositories.specifications;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
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
public class FollowSpecification {
    public Specification<FollowAgency> getFollowHasValidStateByUser(User user) {
        return new Specification<FollowAgency>() {
            @Override
            public Predicate toPredicate(Root<FollowAgency> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList.add(criteriaBuilder.equal(root.get("state").as(Integer.class), 1));
                predicatesList.add(criteriaBuilder.equal(root.get("author").get("id").as(Integer.class), user.getId()));
                return criteriaBuilder.and(predicatesList.toArray(new Predicate[]{}));
            }
        };
    }

    public Specification<FollowAgency> getFollowHasValidStateByAgency(Agency agency) {
        return new Specification<FollowAgency>() {
            @Override
            public Predicate toPredicate(Root<FollowAgency> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList.add(criteriaBuilder.equal(root.get("state").as(Integer.class), 1));
                predicatesList.add(criteriaBuilder.equal(root.get("agency").get("id").as(Integer.class), agency.getId()));
                return criteriaBuilder.and(predicatesList.toArray(new Predicate[]{}));
            }
        };
    }
}
