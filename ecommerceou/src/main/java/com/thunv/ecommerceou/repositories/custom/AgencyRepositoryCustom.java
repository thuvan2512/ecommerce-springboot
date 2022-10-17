package com.thunv.ecommerceou.repositories.custom;

import com.thunv.ecommerceou.dto.SearchAgencyDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface AgencyRepositoryCustom{
    List<Agency> searchAgency(SearchAgencyDTO searchAgencyDTO);
    List<Agency> getListAgencyFollowByUser(User user);
    List<Object[]> getTopAgency(int top);
}
