package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.models.pojo.Cart;
import com.thunv.ecommerceou.models.pojo.CartItem;
import com.thunv.ecommerceou.models.pojo.ItemPost;
import com.thunv.ecommerceou.repositories.custom.CartItemRepositoryCustom;
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
public class CartItemRepositoryImpl implements CartItemRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Override
    public boolean existsByItemPost(Cart cart, ItemPost itemPost) {
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<CartItem> query = builder.createQuery(CartItem.class);
            Root root = query.from(CartItem.class);
            query.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = builder.equal(root.get("cart").get("id"), cart.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(root.get("itemPost").get("id"), itemPost.getId());
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            Query q = session.createQuery(query);
            List<CartItem> list = q.getResultList();
            System.out.println(list.size());
            if (list.size() == 0){
                throw new RuntimeException();
            }
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public CartItem getItemExistInCart(Cart cart, ItemPost itemPost) {
        CartItem cartItem;
        try {
            Session session = this.sessionFactoryBean.getObject().getCurrentSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<CartItem> query = builder.createQuery(CartItem.class);
            Root root = query.from(CartItem.class);
            query.select(root);
            List<Predicate> predicates = new ArrayList<>();
            Predicate p1 = builder.equal(root.get("cart").get("id"), cart.getId());
            predicates.add(p1);
            Predicate p2 = builder.equal(root.get("itemPost").get("id"), itemPost.getId());
            predicates.add(p2);
            query.where(predicates.toArray(new Predicate[]{}));
            Query q = session.createQuery(query);
            cartItem = (CartItem) q.getSingleResult();
        } catch (Exception e) {
            cartItem = null;
            e.printStackTrace();
        }
        return cartItem;
    }
}
