package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CommentPost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.repositories.custom.CommentRepositoryCustom;
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
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public List<CommentPost> getListCommentByPost(SalePost salePost) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<CommentPost> query = builder.createQuery(CommentPost.class);
        Root root = query.from(CommentPost.class);
        query.select(root);
        query.where(builder.equal(root.get("salePost").get("id"), salePost.getId()));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public int countCommentByPost(SalePost salePost) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root root = query.from(CommentPost.class);
        query.select(builder.count(root.get("salePost")).as(Integer.class));
        query.where(builder.equal(root.get("salePost").get("id"), salePost.getId()));
        Query q = session.createQuery(query);
        return (int) q.getSingleResult();
    }
    @Override
    public int countCommentByAgency(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
        Root root = query.from(CommentPost.class);
        query.select(builder.count(root.get("salePost")).as(Integer.class));
        query.where(builder.equal(root.get("salePost").get("agency").get("id"), agency.getId()));
        Query q = session.createQuery(query);
        return (int) q.getSingleResult();
    }

    @Override
    public double getAverageStarByAgency(Agency agency) {
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Integer> query = builder.createQuery(Integer.class);
            Root rootComment = query.from(CommentPost.class);
            Root rootSalePost = query.from(SalePost.class);
            List<Predicate> predicates = new ArrayList<>();

            Predicate p1 = builder.equal(rootSalePost.get("agency").get("id"), agency.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(rootComment.get("salePost").get("id"), rootSalePost.get("id"));
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            query.select(builder.avg(rootComment.get("starRate").as(Integer.class)));
            Query q = session.createQuery(query);
            double result = (double) q.getSingleResult();
            return result;
        } catch (Exception e) {
            return 0;
        }
    }
}
