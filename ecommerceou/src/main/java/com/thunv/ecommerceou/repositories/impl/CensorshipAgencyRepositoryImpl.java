package com.thunv.ecommerceou.repositories.impl;


import com.thunv.ecommerceou.models.pojo.CensorshipAgency;
import com.thunv.ecommerceou.repositories.custom.CensorshipAgencyRepositoryCustom;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CensorshipAgencyRepositoryImpl implements CensorshipAgencyRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public List<CensorshipAgency> getListUncensored() {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CensorshipAgency> criteriaQuery = criteriaBuilder.createQuery(CensorshipAgency.class);
        Root root = criteriaQuery.from(CensorshipAgency.class);
        criteriaQuery.where(criteriaBuilder.isNull(root.get("censoredDate").as(Date.class)));
        Query query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
