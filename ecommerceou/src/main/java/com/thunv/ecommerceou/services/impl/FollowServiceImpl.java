package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.FollowRepository;
import com.thunv.ecommerceou.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Override
    public FollowAgency createFollow(User user, Agency agency) throws RuntimeException{
        try {
            if(this.followRepository.checkExistFollow(user, agency)) {
               FollowAgency lp = this.followRepository.getFollowExist(user, agency);
                if (lp.getState() == 1) {
                    lp.setState(0);
                } else {
                    lp.setState(1);
                }
                return this.followRepository.save(lp);
            } else {
                FollowAgency fl = new FollowAgency();
                fl.setAgency(agency);
                fl.setAuthor(user);
                fl.setState(1);
                return this.followRepository.save(fl);
            }
        } catch (Exception e) {
            throw  new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean getFollowStateAgencyByUser(User user, Agency agency) throws RuntimeException{
        if (this.followRepository.checkExistFollow(user,agency)){
            FollowAgency fl = this.followRepository.getFollowExist(user,agency);
            if (fl.getState() == 1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int countFollowByAgency(Agency agency) throws RuntimeException{
        try {
            return this.followRepository.countFollowByPost(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
