package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.dto.AgencyRegisterDTO;
import com.thunv.ecommerceou.dto.SearchAgencyDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;
import java.util.Map;

public interface AgencyService {
    Agency getAgencyByID(int agencyID);
    Agency getAgencyByUserID(int userID);
    List<Agency> getAllAgency();
    List<Object[]> getTopAgency(int top);
    List<Agency> searchAgency(SearchAgencyDTO searchAgencyDTO);
    List<Agency> getListAgencyFollowByUser(User user);
    Integer countAgency();
    Integer getTotalPageAgency(Integer total);
    Agency createAgency(AgencyRegisterDTO agencyRegisterDTO);
    Agency updateAgency(Agency agency);
    Agency banAgency(int agencyID, boolean isAdmin);
    Agency unBanAgency(int agencyID);
    boolean deleteAgency(int agencyID);
    Map<Object, Object> getGeneralStatsForAdminView();
}
