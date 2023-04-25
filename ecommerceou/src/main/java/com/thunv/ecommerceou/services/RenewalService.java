package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.RenewalManage;
import com.thunv.ecommerceou.models.pojo.RenewalPackage;

import java.util.List;

public interface RenewalService {
    boolean checkRenewalManageExist(int renewalManageID);
    RenewalManage getRenewalManageByID(int renewalManageID);
    RenewalManage getRenewalManageByAgencyID(int agencyID);
    RenewalPackage getRenewalPackageByID(int renewalPackageID);
    List<RenewalPackage> getListRenewalPackage();
    void initialTrialForNewAgency(int agencyID);

    void  createOrderRenewal(int agencyID, int packageID);

    void scanAgencyRenewalInfo();

    List<Object[]> getStatsRevenueMonthByYear(int year);
    List<Object[]> getStatsRevenueQuarterByYear(int year);
    List<Object[]> getStatsRevenueYear();
}
