package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.OrderAgency;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.custom.OrdersAgencyRepositoryCustom;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class OrdersAgencyRepositoryImpl implements OrdersAgencyRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public List<OrderAgency> getListOrderAgencyByUser(User user) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<OrderAgency> criteriaQuery = criteriaBuilder.createQuery(OrderAgency.class);
        Root root = criteriaQuery.from(OrderAgency.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get("orders").get("author").get("id"),user.getId()));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("orders").get("createdDate")));
        criteriaQuery.select(root);
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
