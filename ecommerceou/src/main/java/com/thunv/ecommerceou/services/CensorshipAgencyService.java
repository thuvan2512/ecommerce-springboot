package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CensorshipAgency;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface CensorshipAgencyService {
    CensorshipAgency createCensorshipAgency(Agency agency);
    CensorshipAgency censorAgency(User censor,CensorshipAgency censorshipAgency, boolean state);
    boolean deleteCensorshipAgency(int censorshipID);
    List<CensorshipAgency> getAllCensorshipAgency();
    List<CensorshipAgency> getUncensored();
    CensorshipAgency getCensorshipByID(int censorshipID);
}
