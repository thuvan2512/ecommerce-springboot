package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.SalePostCreateDTO;
import com.thunv.ecommerceou.dto.SearchSalePostDTO;
import com.thunv.ecommerceou.models.pojo.*;
import com.thunv.ecommerceou.repositories.CartItemRepository;
import com.thunv.ecommerceou.repositories.SalePostRepository;
import com.thunv.ecommerceou.specifications.SalePostSpecification;
import com.thunv.ecommerceou.services.*;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalePostServiceImpl implements SalePostService {
    @Autowired
    private SalePostRepository salePostRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SellStatusService sellStatusService;
    @Autowired
    private Environment env;
    @Autowired
    private CommentService commentService;
    @Autowired
    private Utils utils;
    @Autowired
    private FollowService followService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private SalePostSpecification salePostSpecification;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public SalePost getSalePostByID(int postID) throws RuntimeException {
        return this.salePostRepository.findById(postID).orElseThrow(
                () -> new RuntimeException("Can not find sale post with id = " + postID));
    }

    @Override
    public List<SalePost> getAllSalePost() throws RuntimeException {
        try {
            return this.salePostRepository.findAll();
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<SalePost> searchSalePost(SearchSalePostDTO searchSalePostDTO) throws RuntimeException {
        try {
            return this.salePostRepository.searchSalePost(searchSalePostDTO);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object> getSuggestForSearchProducts(String keyword) throws RuntimeException{
        try {
            return this.salePostRepository.getSuggestForSearchProducts(keyword);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<SalePost> getListSalePostLikeByUser(User user) throws RuntimeException {
        try {
            return this.salePostRepository.getListSalePostLikeByUser(user);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<SalePost> getListSalePostUnpublished(Agency agency) throws RuntimeException {
        try {
            return this.salePostRepository.getListSalePostUnpublished(agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<SalePost> getListSalePostPublished(Agency agency) throws RuntimeException {
        try {
            return this.salePostRepository.getListSalePostPublished(agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<SalePost> getAllSalePostByAgency(Agency agency) {
        try {
            return this.salePostRepository.findByAgency(agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }


    @Override
    public SalePost publishSalePost(SalePost salePost) throws RuntimeException {
        try {
            salePost.setIsActive(1);
            return this.salePostRepository.save(salePost);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public SalePost unPublishSalePost(SalePost salePost) throws RuntimeException {
        try {
            salePost.setIsActive(0);
            List<ItemPost> itemPostList = this.itemService.getListItemBySalePost(salePost);
            for (ItemPost itemPost: itemPostList){
                List<CartItem> cartItemList = this.cartItemRepository.findByItemPost(itemPost);
                for (CartItem cartItem: cartItemList){
                    this.cartItemRepository.deleteById(cartItem.getId());
                }
            }
            return this.salePostRepository.save(salePost);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public SalePost createSalePost(SalePostCreateDTO salePostCreateDTO) throws RuntimeException {
        try {
            SalePost salePost = new SalePost();
            salePostCreateDTO.loadSalePostFromDTO(salePost);
            salePost.setCreatedDate(new Date());
            salePost.setIsActive(0);
            salePost.setCategory(this.categoryService.getCategoryByID(salePostCreateDTO.getCategoryID()));
            salePost.setSellStatus(this.sellStatusService.getSellStatusByID(salePostCreateDTO.getSellStatusID()));
            salePost.setAgency(salePostCreateDTO.getAgency());
            List<FollowAgency> followAgencyList = this.followService.getListFollowByAgency(salePostCreateDTO.getAgency());
            this.notifyService.pushListFollowNotifyForUser(followAgencyList, salePost.getTitle());
            return this.salePostRepository.save(salePost);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public SalePost updateSalePost(SalePost salePost) throws RuntimeException {
        try {
            return this.salePostRepository.save(salePost);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteSalePost(int postID) throws RuntimeException {
        try {
            if (this.salePostRepository.existsById(postID) == false) {
                throw new RuntimeException("Sale post does not exist");
            }
            this.salePostRepository.deleteById(postID);
            return true;
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getTopSeller(int top) throws RuntimeException {
        try {
            return this.salePostRepository.getTopSeller(top);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getTopSellerByAgency(int top, Agency agency) throws RuntimeException{
        try {
            return this.salePostRepository.getTopSellerByAgency(top, agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getStatsInfoOfCommentByPost(SalePost salePost) throws RuntimeException{
        try {
            return this.utils.customListStatsComment(this.salePostRepository.getStatsInfoOfCommentByPost(salePost));
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Integer countSalePost() throws RuntimeException {
        try {
            return Math.toIntExact(this.salePostRepository.count(this.salePostSpecification.salePostValidToPublish()));
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public double getAverageStarRateOfSalePost(int postID) throws RuntimeException{
        try {
            SalePost salePost = this.getSalePostByID(postID);
            List<CommentPost> commentPostList = this.commentService.getListCommentByPost(salePost);
            int countRate = 0;
            int countStar = 0;
            for (CommentPost cp: commentPostList) {
                if (cp.getStarRate() != null) {
                    countStar += cp.getStarRate();
                    countRate++;
                }
            }
            if (countStar == 0) {
                return 0;
            }
            return (countStar * 1.0 )/ countRate;
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Integer getTotalPageSalePost(Integer total) throws RuntimeException {
        int size = Integer.parseInt(this.env.getProperty("post.paginate.size"));
        int result;
        if (size > 0 && total > 0) {
            result = (int) Math.round((double) (((total * 1.0 / size * 1.0) + 0.499999)));
        } else {
            result = 1;
        }
        return result;
    }

    @Override
    public List<Object[]> getStatsSalePostByCategory(Agency agency) throws RuntimeException {
        try {
            return this.salePostRepository.getStatsSalePostByCategoryByAgency(agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getStatsSalePostByCategory() throws RuntimeException {
        try {
            return this.salePostRepository.getStatsSalePostByCategory();
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getStatsRevenueMonthByYear(Agency agency, int year) throws RuntimeException {
        try {
            return this.utils.customListStatsMonth(this.salePostRepository.getStatsRevenueMonthByYear(agency, year));
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getStatsRevenueQuarterByYear(Agency agency, int year) throws RuntimeException {
        try {
            return this.utils.customListStatsQuarter(this.salePostRepository.getStatsRevenueQuarterByYear(agency, year));
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getStatsRevenueYear(Agency agency) throws RuntimeException {
        try {
            return this.salePostRepository.getStatsRevenueYear(agency);
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
