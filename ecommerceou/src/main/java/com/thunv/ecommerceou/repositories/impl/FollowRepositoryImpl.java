package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.custom.FollowRepositoryCustom;
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
public class FollowRepositoryImpl implements FollowRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public Boolean checkExistFollow(User user, Agency agency) {
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<FollowAgency> query = builder.createQuery(FollowAgency.class);
            Root root = query.from(FollowAgency.class);
            query.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = builder.equal(root.get("author").get("id"), user.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(root.get("agency").get("id"), agency.getId());
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            Query q = session.createQuery(query);
            List<FollowAgency> list = q.getResultList();
            System.out.println(list.size());
            if (list.size() == 0){
                throw new RuntimeException();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public FollowAgency getFollowExist(User user, Agency agency) {
        FollowAgency followAgency;
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<FollowAgency> query = builder.createQuery(FollowAgency.class);
            Root root = query.from(FollowAgency.class);
            query.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = builder.equal(root.get("author").get("id"), user.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(root.get("agency").get("id"), agency.getId());
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            Query q = session.createQuery(query);
            followAgency = (FollowAgency) q.getSingleResult();
        } catch (Exception e) {
           followAgency = null;
            e.printStackTrace();
        }
        return followAgency;
    }

    @Override
    public int countFollowByPost(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root root = query.from(FollowAgency.class);
        query.select(builder.count(root.get("agency")).as(Integer.class));
        query.where(builder.equal(root.get("agency").get("id"), agency.getId()));
        Query q = session.createQuery(query);
        return (int) q.getSingleResult();
    }
}
