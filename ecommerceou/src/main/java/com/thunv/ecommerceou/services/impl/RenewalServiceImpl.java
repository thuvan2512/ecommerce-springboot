package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.RenewalManage;
import com.thunv.ecommerceou.models.pojo.RenewalOrder;
import com.thunv.ecommerceou.models.pojo.RenewalPackage;
import com.thunv.ecommerceou.repositories.RenewalManageRepository;
import com.thunv.ecommerceou.repositories.RenewalOrderRepository;
import com.thunv.ecommerceou.repositories.RenewalPackageRepository;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.NotifyService;
import com.thunv.ecommerceou.services.RenewalService;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RenewalServiceImpl implements RenewalService {
    @Autowired
    private RenewalManageRepository renewalManageRepository;
    @Autowired
    private RenewalOrderRepository renewalOrderRepository;
    @Autowired
    private RenewalPackageRepository renewalPackageRepository;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private Utils utils;

    @Override
    public boolean checkRenewalManageExist(int renewalManageID) throws RuntimeException{
        return this.renewalManageRepository.existsById(renewalManageID);
    }

    @Override
    public RenewalManage getRenewalManageByID(int renewalManageID) throws RuntimeException{
        return this.renewalManageRepository.findById(renewalManageID).orElseThrow(() -> new RuntimeException("Can not find renewal manage with id = " + renewalManageID));
    }

    @Override
    public RenewalManage getRenewalManageByAgencyID(int agencyID) throws RuntimeException{
        Agency agency = this.agencyService.getAgencyByID(agencyID);
        List<RenewalManage> renewalManageList = this.renewalManageRepository.findByAgency(agency);
        if (renewalManageList.size() > 0){
            return renewalManageList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public RenewalPackage getRenewalPackageByID(int renewalPackageID) throws RuntimeException{
        return this.renewalPackageRepository.findById(renewalPackageID).orElseThrow(() -> new RuntimeException("Can not find renewal package with id = " + renewalPackageID));
    }

    @Override
    public List<RenewalPackage> getListRenewalPackage() throws RuntimeException{
        return this.renewalPackageRepository.findAll();
    }

    @Override
    public void initialTrialForNewAgency(int agencyID) throws RuntimeException{
        Agency agency = this.agencyService.getAgencyByID(agencyID);
        if (this.renewalManageRepository.existsByAgency(agency) == false){
            RenewalPackage renewalPackageTrial = this.renewalPackageRepository.findById(1).orElseThrow(() ->  new RuntimeException("Can not find renewal package trial with id = " + 1));
            RenewalManage renewalManage = new RenewalManage();
            renewalManage.setAgency(agency);
            Date date = new Date();
            renewalManage.setUpdatedDate(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, renewalPackageTrial.getNumberOfDaysAvailable());
            renewalManage.setExpireDate(calendar.getTime());
            this.renewalManageRepository.save(renewalManage);
        }
    }

    @Override
    public void createOrderRenewal(int agencyID, int packageID) throws RuntimeException{
        Calendar calendar = Calendar.getInstance();

        RenewalPackage renewalPackage = getRenewalPackageByID(packageID);
        RenewalManage renewalManage = this.getRenewalManageByAgencyID(agencyID);
        if (renewalManage != null){
            RenewalOrder renewalOrder = new RenewalOrder();
            renewalOrder.setRenewalPackage(renewalPackage);
            renewalOrder.setRenewalManage(renewalManage);
            renewalOrder.setPrice(renewalPackage.getDiscountPrice());
            renewalOrder.setNumberOfDaysAvailable(renewalPackage.getNumberOfDaysAvailable());
            this.renewalOrderRepository.save(renewalOrder);
            renewalManage.setUpdatedDate(renewalOrder.getCreatedDate());
            renewalManage.setIsRemind(0);
            renewalManage.setIsDeactivate(0);
            if (renewalManage.getExpireDate().before(renewalOrder.getCreatedDate())){
                calendar.setTime(renewalOrder.getCreatedDate());
            }else {
                calendar.setTime(renewalManage.getExpireDate());
            }
            calendar.add(Calendar.DATE, renewalPackage.getNumberOfDaysAvailable());
            renewalManage.setExpireDate(calendar.getTime());
            Agency agency = renewalManage.getAgency();
            agency.setIsActive(1);
            this.agencyService.updateAgency(agency);
            this.renewalManageRepository.save(renewalManage);
        }
    }

    @Override
    @Async
    public void scanAgencyRenewalInfo() throws RuntimeException{
        Date currentDate = new Date();
        List<Agency> banAgency = new ArrayList<>();
        List<Agency> remindAgency = new ArrayList<>();
        List<Agency> agencyList = this.agencyService.getAllAgency();
        for (Agency agency: agencyList){
            RenewalManage renewalManage = this.getRenewalManageByAgencyID(agency.getId());
            if (renewalManage != null){
                if (currentDate.after(renewalManage.getExpireDate())){
                    this.agencyService.banAgency(agency.getId());
                    if (renewalManage.getIsDeactivate() == 0){
                        renewalManage.setIsDeactivate(1);
                        banAgency.add(agency);
                    }

                }else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(renewalManage.getExpireDate());
                    cal.add(Calendar.DATE, -3);
                    if (currentDate.after(cal.getTime())){
                        if (renewalManage.getIsRemind() == 0){
                            renewalManage.setIsRemind(1);
                            remindAgency.add(agency);
                        }
                    }
                }
                this.renewalManageRepository.save(renewalManage);
            }else {
                initialTrialForNewAgency(agency.getId());
            }
        }
        this.notifyService.pushListBanAgencyNotifyForManager(banAgency);
        this.notifyService.pushListNotifyRemindAgency(remindAgency);
    }

    @Override
    public List<Object[]> getStatsRevenueMonthByYear(int year) throws RuntimeException{
        return this.utils.customListStatsMonth(this.renewalOrderRepository.getStatsRevenueMonthByYear(year));
    }

    @Override
    public List<Object[]> getStatsRevenueQuarterByYear(int year) throws RuntimeException{
        return this.utils.customListStatsQuarter(this.renewalOrderRepository.getStatsRevenueQuarterByYear(year));
    }

    @Override
    public List<Object[]> getStatsRevenueYear() throws RuntimeException{
        return this.renewalOrderRepository.getStatsRevenueYear();
    }
}
