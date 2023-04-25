package com.thunv.schedule;

import com.thunv.ecommerceou.services.RenewalService;
import com.thunv.ecommerceou.services.impl.RenewalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskSchedule {
    @Autowired
    private RenewalService renewalService;


    public void scanAndBanExpiredAgent(){
        try {
            System.out.println("executing scanAndBanExpiredAgent...");
            this.renewalService.scanAgencyRenewalInfo();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

}
