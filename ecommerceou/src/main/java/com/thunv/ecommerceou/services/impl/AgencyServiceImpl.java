package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.dto.AgencyRegisterDTO;
import com.thunv.ecommerceou.dto.SearchAgencyDTO;
import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.AgencyRepository;
import com.thunv.ecommerceou.services.AgencyFieldService;
import com.thunv.ecommerceou.services.AgencyService;
import com.thunv.ecommerceou.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private Environment env;
    @Autowired
    private UserService userService;
    @Autowired
    private AgencyFieldService agencyFieldService;
    @Override
    public Agency getAgencyByID(int agencyID) throws RuntimeException{
        return this.agencyRepository.findById(agencyID).orElseThrow(() -> new RuntimeException("Can not find agency with id = " + agencyID));
    }

    @Override
    public Agency getAgencyByUserID(int userID) throws RuntimeException{
        User user = this.userService.getUserByID(userID);
        List<Agency> agencyList = this.agencyRepository.findByManager(user);
        if(agencyList.size() > 0){
            return agencyList.get(0);
        }
        return null;
    }

    @Override
    public List<Agency> getAllAgency()  throws RuntimeException{
        try {
            return this.agencyRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Object[]> getTopAgency(int top) throws RuntimeException{
        try {
            return this.agencyRepository.getTopAgency(top);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Agency> searchAgency(SearchAgencyDTO searchAgencyDTO)  throws RuntimeException{
        try {
            return this.agencyRepository.searchAgency(searchAgencyDTO);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<Agency> getListAgencyFollowByUser(User user) throws RuntimeException {
        try {
            return this.agencyRepository.getListAgencyFollowByUser(user);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Integer countAgency() {
        return Math.toIntExact(this.agencyRepository.count());
    }

    @Override
    public Integer getTotalPageAgency() {
        int size = Integer.parseInt(this.env.getProperty("page.size"));
        int count = this.countAgency();
        int result;
        if (size > 0 && count > 0) {
            result = (int)Math.round((double)(((count*1.0 / size*1.0) + 0.499999)));
        }else{
            result = 1;
        }
        return result;
    }

    @Override
    public Agency createAgency(AgencyRegisterDTO agencyRegisterDTO)  throws RuntimeException{
        try {
            Agency agency = new Agency();
            agencyRegisterDTO.loadAgencyFromAgencyDTO(agency);
            agency.setManager(this.userService.getUserByID(agencyRegisterDTO.getManagerID()));
            agency.setField(this.agencyFieldService.getAgentFieldByID(agencyRegisterDTO.getFieldID()));
            agency.setIsActive(0);
            agency.setIsCensored(0);
            agency.setCreatedDate(new Date());
            return this.agencyRepository.save(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Agency updateAgency(Agency agency) throws RuntimeException {
        try {
            return this.agencyRepository.save(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Agency banAgency(int agencyID)  throws RuntimeException{
        try {
            Agency agency = this.getAgencyByID(agencyID);
            agency.setIsActive(0);
            return this.agencyRepository.save(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public Agency unBanAgency(int agencyID) throws RuntimeException{
        try {
            Agency agency = this.getAgencyByID(agencyID);
            agency.setIsActive(1);
            return this.agencyRepository.save(agency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteAgency(int agencyID)  throws RuntimeException{
        try {
            if (this.agencyRepository.existsById(agencyID) == false){
                throw new RuntimeException("Agency does not exist");
            }
            this.agencyRepository.deleteById(agencyID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
