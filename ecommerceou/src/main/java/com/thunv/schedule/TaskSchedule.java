package com.thunv.schedule;

import com.thunv.ecommerceou.services.RenewalService;
import com.thunv.ecommerceou.services.impl.RenewalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskSchedule {
    @Autowired
    private RenewalService renewalService;
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedule.class);

    public void scanAndBanExpiredAgent(){
        try {
            logger.info("executing scanAndBanExpiredAgent...");
            this.renewalService.scanAgencyRenewalInfo();
        }catch (Exception ex){
            ex.printStackTrace();
            logger.error(ex.getMessage());
        }
    }

}
