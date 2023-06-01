package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.PromotionProgram;
import com.thunv.ecommerceou.repositories.custom.PromotionProgramRepositoryCustom;
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

@Transactional
@Repository
public class PromotionProgramRepositoryImpl implements PromotionProgramRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public List<PromotionProgram> getListProgramByListAgencyID(List<Integer> listAgencyID, Integer top) {
        Date current = new Date();
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PromotionProgram> criteriaQuery = criteriaBuilder.createQuery(PromotionProgram.class);
        Root rootPromotionProgram = criteriaQuery.from(PromotionProgram.class);
        criteriaQuery.where(criteriaBuilder.and(rootPromotionProgram.get("agency").get("id").in(listAgencyID),
                criteriaBuilder.equal(rootPromotionProgram.get("state"), 1),
                criteriaBuilder.lessThanOrEqualTo(rootPromotionProgram.get("beginUsable").as(Date.class), current),
                criteriaBuilder.greaterThanOrEqualTo(rootPromotionProgram.get("endUsable").as(Date.class), current)));
        criteriaQuery.orderBy(criteriaBuilder.desc(rootPromotionProgram.get("createdDate").as(Date.class)));
        Query query = session.createQuery(criteriaQuery);
        if (top > 0){
            query.setMaxResults(top);
        }
        return query.getResultList();
    }

    @Override
    public List<PromotionProgram> getAllProgramByListAgency(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PromotionProgram> criteriaQuery = criteriaBuilder.createQuery(PromotionProgram.class);
        Root rootPromotionProgram = criteriaQuery.from(PromotionProgram.class);
        criteriaQuery.where(criteriaBuilder.equal(rootPromotionProgram.get("agency").get("id"), agency.getId()));
        criteriaQuery.orderBy(criteriaBuilder.desc(rootPromotionProgram.get("createdDate").as(Date.class)));
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<PromotionProgram> getAllPublishProgram() {
        Date current = new Date();
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<PromotionProgram> criteriaQuery = criteriaBuilder.createQuery(PromotionProgram.class);
        Root rootPromotionProgram = criteriaQuery.from(PromotionProgram.class);
        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(rootPromotionProgram.get("state"), 1),
                criteriaBuilder.lessThanOrEqualTo(rootPromotionProgram.get("beginUsable").as(Date.class), current),
                criteriaBuilder.greaterThanOrEqualTo(rootPromotionProgram.get("endUsable").as(Date.class), current)));
        criteriaQuery.orderBy(criteriaBuilder.desc(rootPromotionProgram.get("createdDate").as(Date.class)));
        Query query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
