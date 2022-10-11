package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.SellStatus;

import java.util.List;

public interface SellStatusService {
    SellStatus getSellStatusByID(int statusID);
    List<SellStatus> getAllSellStatus();
    SellStatus createSellStatus(SellStatus sellStatus);
    SellStatus updateSellStatus(SellStatus sellStatus);
    boolean deleteSellStatus(int statusID);
}
