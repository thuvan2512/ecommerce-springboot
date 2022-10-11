package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.dto.AgencyRegisterDTO;
import com.thunv.ecommerceou.dto.SearchAgencyDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.User;

import java.util.List;

public interface AgencyService {
    Agency getAgencyByID(int agencyID);
    List<Agency> getAllAgency();
    List<Agency> searchAgency(SearchAgencyDTO searchAgencyDTO);
    List<Agency> getListAgencyFollowByUser(User user);
    Integer countAgency();
    Integer getTotalPageAgency();
    Agency createAgency(AgencyRegisterDTO agencyRegisterDTO);
    Agency updateAgency(Agency agency);
    Agency banAgency(int agencyID);
    Agency unBanAgency(int agencyID);
    boolean deleteAgency(int agencyID);
}
