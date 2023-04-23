package com.thunv.ecommerceou.repositories.specifications;

import com.thunv.ecommerceou.models.pojo.Agency;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Component
public class AgencySpecification {
    public Specification<Agency> agencyValidToPublic() {
        return new Specification<Agency>() {
            @Override
            public Predicate toPredicate(Root<Agency> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                predicatesList.add(criteriaBuilder.equal(root.get("isActive").as(Integer.class), 1));
                return criteriaBuilder.and(predicatesList.toArray(new Predicate[]{}));
            }
        };
    }
}
