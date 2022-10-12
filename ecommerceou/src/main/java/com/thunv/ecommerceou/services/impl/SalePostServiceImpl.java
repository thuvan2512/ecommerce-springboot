package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.SalePostCreateDTO;
import com.thunv.ecommerceou.dto.SalePostUpdateDTO;
import com.thunv.ecommerceou.dto.SearchSalePostDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.SalePostRepository;
import com.thunv.ecommerceou.services.CategoryService;
import com.thunv.ecommerceou.services.SalePostService;
import com.thunv.ecommerceou.services.SellStatusService;
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
    private Utils utils;

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
    public Integer countSalePost() throws RuntimeException {
        try {
            return Math.toIntExact(this.salePostRepository.count());
        } catch (Exception ex) {
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Integer getTotalPageSalePost() throws RuntimeException {
        int size = Integer.parseInt(this.env.getProperty("post.paginate.size"));
        int count = this.countSalePost();
        int result;
        if (size > 0 && count > 0) {
            result = (int) Math.round((double) (((count * 1.0 / size * 1.0) + 0.499999)));
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
