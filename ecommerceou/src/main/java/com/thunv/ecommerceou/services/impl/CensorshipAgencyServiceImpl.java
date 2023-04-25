package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.Agency;
import com.thunv.ecommerceou.models.pojo.CensorshipAgency;
import com.thunv.ecommerceou.models.pojo.Role;
import com.thunv.ecommerceou.models.pojo.User;
import com.thunv.ecommerceou.repositories.CensorshipAgencyRepository;
import com.thunv.ecommerceou.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CensorshipAgencyServiceImpl implements CensorshipAgencyService {
    @Autowired
    private CensorshipAgencyRepository censorshipAgencyRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private NotifyService notifyService;
    @Override
    public CensorshipAgency createCensorshipAgency(Agency agency)  throws RuntimeException{
        try {
            CensorshipAgency censorshipAgency = new CensorshipAgency();
            censorshipAgency.setAgency(agency);
            censorshipAgency.setManager(agency.getManager());
            censorshipAgency.setCreatedDate(new Date());
            String recipient = "admin";
            String title = "There is 01 new partner agency moderation request";
            String detail = String.format("User %s's %s agent just submitted a new agent moderation request.",
                    agency.getManager().getUsername(), agency.getName());
            String type = "Censorship Partner Agency";
            this.notifyService.pushNotify(recipient, agency.getAvatar(), title, detail, type);
            return this.censorshipAgencyRepository.save(censorshipAgency);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public CensorshipAgency censorAgency(User censor,CensorshipAgency censorshipAgency, boolean state)  throws RuntimeException{
        try {
            censorshipAgency.setCensor(censor);
            censorshipAgency.setCensoredDate(new Date());
            Agency agency = censorshipAgency.getAgency();
            if (state){
                User manager = censorshipAgency.getManager();
                if (manager.getRole().getId() == 3){
                    Role role = new Role();
                    role.setId(2);
                    manager.setRole(role);
                    this.userService.updateUser(manager);
                }
                agency.setIsActive(1);
                agency.setIsCensored(1);
                agency.setDeactivatedByAdmin(0);
                this.agencyService.updateAgency(agency);
                return this.censorshipAgencyRepository.save(censorshipAgency);
            }else {
                this.agencyService.deleteAgency(agency.getId());
                return null;
            }

        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteCensorshipAgency(int censorshipID)  throws RuntimeException{
        try {
            if (this.censorshipAgencyRepository.existsById(censorshipID) == false){
                throw new RuntimeException("Censorship agency does not exist");
            }
            this.censorshipAgencyRepository.deleteById(censorshipID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CensorshipAgency> getAllCensorshipAgency()  throws RuntimeException{
        try {
            return this.censorshipAgencyRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<CensorshipAgency> getUncensored() throws RuntimeException{
        try {
            return this.censorshipAgencyRepository.getListUncensored();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public CensorshipAgency getCensorshipByID(int censorshipID) throws RuntimeException{
        return this.censorshipAgencyRepository.findById(censorshipID).orElseThrow(() ->
                new RuntimeException("Can not find censorship agency with id = " + censorshipID));
    }
}
