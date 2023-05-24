package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.enumerate.NotificationImages;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.LikePost;
import com.thunv.ecommerceou.models.pojo.SalePost;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.LikePostRepository;
import com.thunv.ecommerceou.services.LikePostService;
import com.thunv.ecommerceou.services.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikePostServiceImpl implements LikePostService {
    @Autowired
    private LikePostRepository likePostRepository;
    @Autowired
    private NotifyService notifyService;
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
                String recipient = String.format("agency-%s", salePost.getAgency().getId());
                String title = "Your sale post has new likes";
                String detail = String.format("User %s just liked your sale post '%s'.",
                        user.getUsername(), salePost.getTitle());
                String type = "Interactions";
                this.notifyService.pushNotify(recipient, user.getAvatar(), title, detail, type);
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

    @Override
    public int countLikeByAgency(Agency agency) {
        try {
            return this.likePostRepository.countLikeByAgency(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
