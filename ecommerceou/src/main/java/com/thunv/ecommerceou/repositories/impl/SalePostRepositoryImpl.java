package com.thunv.ecommerceou.repositories.impl;

import com.thunv.ecommerceou.dto.SearchSalePostDTO;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.custom.SalePostRepositoryCustom;
import com.thunv.ecommerceou.services.SalePostService;
import com.thunv.ecommerceou.utils.Utils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class SalePostRepositoryImpl implements SalePostRepositoryCustom {
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    @Autowired
    private Utils utils;
    @Autowired
    private Environment env;
    @Autowired
    private SalePostService salePostService;
    @Override
    public List<SalePost> searchSalePost(SearchSalePostDTO searchSalePostDTO) throws RuntimeException{
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<SalePost> criteriaQuery = criteriaBuilder.createQuery(SalePost.class);
        Root root = criteriaQuery.from(SalePost.class);
//        Root rootAgenct = criteriaQuery.from(Agency.class);
        criteriaQuery.select(root);
        List<Predicate> predicates1 = new ArrayList<>();
        List<Predicate> predicatesKeyword = new ArrayList<>();
        String kw = searchSalePostDTO.getKw();
        if (kw != null && !kw.isEmpty()) {
            List<Object> listKeyResults = this.salePostService.getSuggestForSearchProducts(kw);
            System.out.println(listKeyResults.size());
            for (Object key: listKeyResults){
                predicatesKeyword.add(criteriaBuilder.like(root.get("title").as(String.class), String.format("%%%s%%", key.toString())));
            }
            Predicate pcKeyword = criteriaBuilder.or(predicatesKeyword.toArray(new Predicate[]{}));
            predicates1.add(pcKeyword);
        }
        Double fp = searchSalePostDTO.getFromPrice();
        if (fp != null) {
            Predicate p2 = criteriaBuilder.greaterThanOrEqualTo(root.get("finalPrice").as(Double.class), fp);
            predicates1.add(p2);
        }

        Double tp = searchSalePostDTO.getToPrice();
        if (tp != null) {
            Predicate p3 = criteriaBuilder.lessThanOrEqualTo(root.get("finalPrice").as(Double.class), tp);
            predicates1.add(p3);
        }
        String fd = searchSalePostDTO.getFromDate();
        if (fd != null && !fd.isEmpty()) {
            Predicate p4;
            try {
                p4 = criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(Date.class), this.utils.getSimpleDateFormat().parse(fd));
                predicates1.add(p4);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        String td = searchSalePostDTO.getToDate();
        if (td != null && !td.isEmpty()) {
            Predicate p5;
            try {
                p5 = criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(Date.class), this.utils.getSimpleDateFormat().parse(td));
                predicates1.add(p5);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        predicates1.add(criteriaBuilder.equal(root.get("isActive").as(Integer.class), 1));
        predicates1.add(criteriaBuilder.equal(root.get("agency").get("isActive").as(Integer.class), 1));
        Integer category = searchSalePostDTO.getCategoryID();
        if (category != null) {
            Predicate p6 = criteriaBuilder.equal(root.get("category").get("id").as(Integer.class), category);
            predicates1.add(p6);
        }
        String nameOfAgency = searchSalePostDTO.getNameOfAgency();
        if (nameOfAgency != null && !nameOfAgency.isEmpty()) {
            Predicate p7 = criteriaBuilder.like(root.get("agency").get("name").as(String.class), String.format("%%%s%%", nameOfAgency));
            predicates1.add(p7);
        }
//        List<Predicate> mainPre = new ArrayList<>();
//        Predicate p1 = criteriaBuilder.and(predicates1.toArray(new Predicate[]{}));
//        mainPre.add(p1);
//        Predicate p2 = criteriaBuilder.and(predicates1.toArray(new Predicate[]{}));
//        mainPre.add(p2);
//        Predicate finalPre = criteriaBuilder.or(mainPre.toArray(new Predicate[]{}));
        criteriaQuery.where(predicates1.toArray(new Predicate[]{}));
        criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
        if (kw != null){
            criteriaQuery.orderBy(criteriaBuilder.desc( criteriaBuilder.locate(root.get("title"),kw.toString())));
        }
        Query query = session.createQuery(criteriaQuery);
        int page = searchSalePostDTO.getPage();
        if (page > 0){
            int size = Integer.parseInt(env.getProperty("post.paginate.size"));
            int start = (page - 1) * size;
            query.setFirstResult(start);
            query.setMaxResults(size);
        }
        return query.getResultList();
    }

    @Override
    public List<Object[]> getTopSeller(int top) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootItem = query.from(ItemPost.class);
        Root rootOrder = query.from(OrderDetail.class);
        query.where(builder.equal(rootItem.get("id"), rootOrder.get("itemPost").get("id")));
        query.multiselect(rootItem.get("id"), rootItem.get("salePost").as(SalePost.class), rootItem.get("name"),
                rootItem.get("unitPrice"), builder.sum(rootOrder.get("quantity")), rootItem.get("description"),rootItem.get("avatar"));
        query.groupBy(rootItem.get("id"));
        query.orderBy(builder.desc(builder.sum(rootOrder.get("quantity"))));
        Query q = session.createQuery(query);
        q.setMaxResults(top);
        return q.getResultList();
    }


    @Override
    public List<SalePost> getListSalePostLikeByUser(User user) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<SalePost> query = builder.createQuery(SalePost.class);
        Root rootSalePost = query.from(SalePost.class);
        Root rootLike = query.from(LikePost.class);
        query.select(rootSalePost);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootSalePost.get("id"), rootLike.get("salePost").get("id"));
        predicates.add(p1);
        Predicate p2 = builder.equal(rootLike.get("author").get("id"), user.getId());
        predicates.add(p2);
        Predicate p3 = builder.equal(rootLike.get("state"), 1);
        predicates.add(p3);
        query.where(predicates.toArray(new Predicate[]{}));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<SalePost> getListSalePostUnpublished(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootPost = query.from(SalePost.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p3 = builder.equal(rootPost.get("agency").get("id"),agency.getId());
        predicates.add(p3);
        Predicate p1 = builder.equal(rootPost.get("isActive").as(Integer.class), 0);
        predicates.add(p1);
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(rootPost);
        query.orderBy(builder.desc(rootPost.get("createdDate")));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<SalePost> getListSalePostPublished(Agency agency ){
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootPost = query.from(SalePost.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p3 = builder.equal(rootPost.get("agency").get("id"),agency.getId());
        predicates.add(p3);
        Predicate p1 = builder.equal(rootPost.get("isActive").as(Integer.class), 1);
        predicates.add(p1);
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(rootPost);
        query.orderBy(builder.desc(rootPost.get("createdDate")));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsSalePostByCategoryByAgency(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootCate = query.from(Category.class);
        Root rootPost = query.from(SalePost.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootPost.get("agency").get("id"), agency.getId());
        predicates.add(p1);
        Predicate p2 = builder.equal(rootPost.get("category").get("id"), rootCate.get("id"));
        predicates.add(p2);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(rootCate.get("name"), builder.count(rootPost.get("id")));
        query.groupBy(rootCate.get("name"));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsSalePostByCategory() {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootCate = query.from(Category.class);
        Root rootPost = query.from(SalePost.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootPost.get("isActive").as(Integer.class), 1);
        predicates.add(p1);
        Predicate p2 = builder.equal(rootPost.get("category").get("id"), rootCate.get("id"));
        predicates.add(p2);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(rootCate.get("name"), builder.count(rootPost.get("id")));
        query.groupBy(rootCate.get("name"));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsRevenueMonthByYear(Agency agency, int year) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootOrderDetail = query.from(OrderDetail.class);
        Root rootOrder = query.from(Orders.class);
        Root rootOrderAgency = query.from(OrderAgency.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootOrderAgency.get("agency").get("id"), agency.getId());
        predicates.add(p1);
        Predicate p2 = builder.equal(rootOrder.get("id"), rootOrderAgency.get("orders").get("id"));
        predicates.add(p2);
        Predicate p3 = builder.equal(rootOrderDetail.get("orderAgency").get("id"), rootOrderAgency.get("id"));
        predicates.add(p3);
        Predicate p5 = builder.equal(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")), year);
        predicates.add(p5);
        Predicate p6 = builder.notEqual(rootOrderAgency.get("orderState").get("id"), 6);
        predicates.add(p6);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(builder.function("MONTH", Integer.class, rootOrder.get("createdDate")), builder.sum(builder.prod(rootOrderDetail.get("itemPost").get("unitPrice"), rootOrderDetail.get("quantity"))));
        query.groupBy(builder.function("MONTH", Integer.class, rootOrder.get("createdDate")));
        query.orderBy(builder.asc(builder.function("MONTH", Integer.class, rootOrder.get("createdDate"))));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsRevenueQuarterByYear(Agency agency,int year) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootOrderDetail = query.from(OrderDetail.class);
        Root rootOrder = query.from(Orders.class);
        Root rootOrderAgency = query.from(OrderAgency.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootOrderAgency.get("agency").get("id"), agency.getId());
        predicates.add(p1);
        Predicate p2 = builder.equal(rootOrder.get("id"), rootOrderAgency.get("orders").get("id"));
        predicates.add(p2);
        Predicate p3 = builder.equal(rootOrderDetail.get("orderAgency").get("id"), rootOrderAgency.get("id"));
        predicates.add(p3);
        Predicate p5 = builder.equal(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")), year);
        predicates.add(p5);
        Predicate p6 = builder.notEqual(rootOrderAgency.get("orderState").get("id"), 6);
        predicates.add(p6);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(builder.function("QUARTER", Integer.class, rootOrder.get("createdDate")), builder.sum(builder.prod(rootOrderDetail.get("itemPost").get("unitPrice"), rootOrderDetail.get("quantity"))));
        query.groupBy(builder.function("QUARTER", Integer.class, rootOrder.get("createdDate")));
        query.orderBy(builder.asc(builder.function("QUARTER", Integer.class, rootOrder.get("createdDate"))));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public List<Object[]> getStatsRevenueYear(Agency agency) {
        Session session = this.sessionFactoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root rootOrderDetail = query.from(OrderDetail.class);
        Root rootOrder = query.from(Orders.class);
        Root rootOrderAgency = query.from(OrderAgency.class);
        List<Predicate> predicates = new ArrayList<>();
        Predicate p1 = builder.equal(rootOrderAgency.get("agency").get("id"), agency.getId());
        predicates.add(p1);
        Predicate p2 = builder.equal(rootOrder.get("id"), rootOrderAgency.get("orders").get("id"));
        predicates.add(p2);
        Predicate p3 = builder.equal(rootOrderDetail.get("orderAgency").get("id"), rootOrderAgency.get("id"));
        predicates.add(p3);
        Predicate p4 = builder.notEqual(rootOrderAgency.get("orderState").get("id"), 6);
        predicates.add(p4);
        query.where(predicates.toArray(new Predicate[]{}));
        query.multiselect(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")), builder.sum(builder.prod(rootOrderDetail.get("itemPost").get("unitPrice"), rootOrderDetail.get("quantity"))));
        query.groupBy(builder.function("YEAR", Integer.class, rootOrder.get("createdDate")));
        query.orderBy(builder.asc(builder.function("YEAR", Integer.class, rootOrder.get("createdDate"))));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

}
