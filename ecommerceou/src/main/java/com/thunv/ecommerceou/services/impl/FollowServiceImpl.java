package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.FollowAgency;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.FollowRepository;
import com.thunv.ecommerceou.specifications.FollowSpecification;
import com.thunv.ecommerceou.services.FollowService;
import com.thunv.ecommerceou.services.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private FollowSpecification followSpecification;
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
                String recipient = String.format("agency-%s", agency.getId());
                String title = "Your agency has new follower";
                String detail = String.format("User %s just followed your agency.",
                        user.getUsername());
                String type = "Follow";
                this.notifyService.pushNotify(recipient, user.getAvatar(), title, detail, type);

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
            return this.followRepository.countFollowByAgency(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<FollowAgency> getListFollowByUser(User user) throws RuntimeException{
        try {
            return this.followRepository.findAll(this.followSpecification.getFollowHasValidStateByUser(user));
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<FollowAgency> getListFollowByAgency(Agency agency) throws RuntimeException{
        try {
            return this.followRepository.findAll(this.followSpecification.getFollowHasValidStateByAgency(agency));
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
