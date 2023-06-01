package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.PromotionCode;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import com.thunv.ecommerceou.repositories.custom.PromotionCodeRepositoryCustom;
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
public class PromotionCodeRepositoryImpl implements PromotionCodeRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public List<PromotionCode> getListPromotionCodeByProgram(PromotionProgram promotionProgram) {
        Date current = new Date();
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PromotionCode> criteriaQuery = criteriaBuilder.createQuery(PromotionCode.class);
        Root rootPromotionCode = criteriaQuery.from(PromotionCode.class);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(rootPromotionCode.get("program").get("id"), promotionProgram.getId()),
                criteriaBuilder.equal(rootPromotionCode.get("isPublic"), 1),
                criteriaBuilder.equal(rootPromotionCode.get("state"), 1),
                criteriaBuilder.greaterThanOrEqualTo(rootPromotionCode.get("endUsableDate").as(Date.class), current)));
        criteriaQuery.orderBy(criteriaBuilder.desc(rootPromotionCode.get("totalCurrent").as(Integer.class)));
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<PromotionCode> getListPromotionCodeByProgramForManage(PromotionProgram promotionProgram) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PromotionCode> criteriaQuery = criteriaBuilder.createQuery(PromotionCode.class);
        Root rootPromotionCode = criteriaQuery.from(PromotionCode.class);
        criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(rootPromotionCode.get("program").get("id"), promotionProgram.getId()),
                criteriaBuilder.equal(rootPromotionCode.get("state"), 1)));
        criteriaQuery.orderBy(criteriaBuilder.desc(rootPromotionCode.get("createdDate").as(Date.class)));
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
