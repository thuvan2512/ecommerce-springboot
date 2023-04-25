package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.OrderAgency;
import com.thunv.ecommerceou.models.pojo.OrderDetail;
import com.thunv.ecommerceou.models.pojo.Orders;
import com.thunv.ecommerceou.models.pojo.RenewalOrder;
import com.thunv.ecommerceou.repositories.custom.RenewalOrderRepositoryCustom;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RenewalOrderRepositoryImpl implements RenewalOrderRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;

    @Override
    public List<Object[]> getStatsRevenueMonthByYear(int year) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootOrder = query.from(RenewalOrder.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p5 = builder.equal(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")), year);
        predicates.add(p5);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(builder.function("MONTH", Integer.class, rootOrder.get("createdDate")), builder.sum(rootOrder.get("price")));
        query.groupBy(builder.function("MONTH", Integer.class, rootOrder.get("createdDate")));
        query.orderBy(builder.asc(builder.function("MONTH", Integer.class, rootOrder.get("createdDate"))));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsRevenueQuarterByYear(int year) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootOrder = query.from(RenewalOrder.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p5 = builder.equal(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")), year);
        predicates.add(p5);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(builder.function("QUARTER", Integer.class, rootOrder.get("createdDate")), builder.sum(rootOrder.get("price")));
        query.groupBy(builder.function("QUARTER", Integer.class, rootOrder.get("createdDate")));
        query.orderBy(builder.asc(builder.function("QUARTER", Integer.class, rootOrder.get("createdDate"))));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsRevenueYear() {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootOrder = query.from(RenewalOrder.class);
        query.multiselect(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")), builder.sum(rootOrder.get("price")));
        query.groupBy(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")));
        query.orderBy(builder.asc(builder.function("YEAR", Integer.class, rootOrder.get("createdDate"))));
        Query q = session.createQuery(query);
        return q.getResultList();
    }
}
