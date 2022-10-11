package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.LikePostRepository;
import com.thunv.ecommerceou.services.LikePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikePostServiceImpl implements LikePostService {
    @Autowired
    private LikePostRepository likePostRepository;
    @Override
    public LikePost createLikePost(User user, SalePost salePost) throws RuntimeException{
        try {
            if(this.likePostRepository.checkExistLikePost(user, salePost)) {
                LikePost lp = this.likePostRepository.getLikePostExist(user, salePost);
                if (lp.getState() == 1) {
                    lp.setState(0);
                } else {
                    lp.setState(1);
                }
                return this.likePostRepository.save(lp);
            } else {
                LikePost likePost = new LikePost();
                likePost.setSalePost(salePost);
                likePost.setAuthor(user);
                likePost.setState(1);
                return this.likePostRepository.save(likePost);
            }
        } catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean getLikeStatePostByUser(User user, SalePost salePost) {
        if (this.likePostRepository.checkExistLikePost(user,salePost)){
            LikePost likePost = this.likePostRepository.getLikePostExist(user,salePost);
            if (likePost.getState() == 1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int countLikeByPost(SalePost salePost) {
        try {
            return this.likePostRepository.countLikeByPost(salePost);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
