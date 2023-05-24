package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.custom.LikePostRepositoryCustom;
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
public class LikePostRepositoryImpl implements LikePostRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public Boolean checkExistLikePost(User user, SalePost salePost) {
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<LikePost> query = builder.createQuery(LikePost.class);
            Root root = query.from(LikePost.class);
            query.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = builder.equal(root.get("author").get("id"), user.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(root.get("salePost").get("id"), salePost.getId());
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            Query q = session.createQuery(query);
            List<LikePost> list = q.getResultList();
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
    public LikePost getLikePostExist(User user, SalePost salePost) {
        LikePost likePost;
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<LikePost> query = builder.createQuery(LikePost.class);
            Root root = query.from(LikePost.class);
            query.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = builder.equal(root.get("author").get("id"), user.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(root.get("salePost").get("id"), salePost.getId());
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            Query q = session.createQuery(query);
            likePost = (LikePost) q.getSingleResult();
        } catch (Exception e) {
            likePost = null;
            e.printStackTrace();
        }
        return likePost;
    }

    @Override
    public int countLikeByPost(SalePost salePost) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root root = query.from(LikePost.class);
        query.select(builder.count(root.get("salePost")).as(Integer.class));
        query.where(builder.equal(root.get("salePost").get("id"), salePost.getId()));
        Query q = session.createQuery(query);
        return (int) q.getSingleResult();
    }

    @Override
    public int countLikeByAgency(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root root = query.from(LikePost.class);
        query.select(builder.count(root.get("salePost")).as(Integer.class));
        query.where(builder.equal(root.get("salePost").get("agency").get("id"), agency.getId()));
        Query q = session.createQuery(query);
        return (int) q.getSingleResult();
    }
}
