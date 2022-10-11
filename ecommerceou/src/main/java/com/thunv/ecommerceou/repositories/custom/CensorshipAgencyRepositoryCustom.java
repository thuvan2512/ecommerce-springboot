package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.models.pojo.CensorshipAgency;

import java.util.List;

public interface CensorshipAgencyRepositoryCustom {
    List<CensorshipAgency> getListUncensored();
}
