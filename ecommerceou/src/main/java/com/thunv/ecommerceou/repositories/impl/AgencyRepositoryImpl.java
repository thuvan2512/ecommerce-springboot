package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.dto.SearchAgencyDTO;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.custom.AgencyRepositoryCustom;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class AgencyRepositoryImpl implements AgencyRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Autowired
    private Environment env;
    @Override
    public List<Agency> searchAgency(SearchAgencyDTO searchAgencyDTO) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Agency> criteriaQuery = criteriaBuilder.createQuery(Agency.class);
        Root root = criteriaQuery.from(Agency.class);
        criteriaQuery.select(root);
        String kw = searchAgencyDTO.getKw();
        List<Predicate> predicateList = new ArrayList<>();
        if (kw != null && !kw.isEmpty()) {
            Predicate p = criteriaBuilder.like(root.get("name").as(String.class),
                    String.format("%%%s%%", kw));
            predicateList.add(p);
        }
        Predicate p2 = criteriaBuilder.equal(root.get("isActive").as(Integer.class),1);
        predicateList.add(p2);
        criteriaQuery.where(predicateList.toArray(new Predicate[]{}));
        Query query = session.createQuery(criteriaQuery);
        int page = searchAgencyDTO.getPage();
        if (page > 0){
            int size = Integer.parseInt(env.getProperty("page.size").toString());
            int start = (page - 1) * size;
            query.setFirstResult(start);
            query.setMaxResults(size);
        }
        return query.getResultList();
    }

    @Override
    public List<Agency> getListAgencyFollowByUser(User user) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Agency> query = builder.createQuery(Agency.class);
        Root rootAgency = query.from(Agency.class);
        Root rootFollow = query.from(FollowAgency.class);
        query.select(rootAgency);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootAgency.get("id"), rootFollow.get("agency").get("id"));
        predicates.add(p1);
        Predicate p2 = builder.equal(rootFollow.get("author").get("id"), user.getId());
        predicates.add(p2);
        Predicate p3 = builder.equal(rootFollow.get("state"), 1);
        predicates.add(p3);
        query.where(predicates.toArray(new Predicate[]{}));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getTopAgency(int top) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root root = criteriaQuery.from(Agency.class);
        Root rootPost = criteriaQuery.from(SalePost.class);
        Root rootLike = criteriaQuery.from(LikePost.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("isCensored").as(Integer.class), 1),
                criteriaBuilder.equal(root.get("isActive").as(Integer.class), 1),
                criteriaBuilder.equal(rootLike.get("state").as(Integer.class), 1),
                criteriaBuilder.equal(rootPost.get("agency").get("id"), root.get("id")),
                criteriaBuilder.equal(rootPost.get("id"), rootLike.get("salePost")));
        criteriaQuery.multiselect(root, criteriaBuilder.count(rootLike.get("id")).as(Integer.class));
        criteriaQuery.groupBy(root.get("id"));
        Query query = session.createQuery(criteriaQuery);
        query.setMaxResults(top);
        return query.getResultList();
    }
}
