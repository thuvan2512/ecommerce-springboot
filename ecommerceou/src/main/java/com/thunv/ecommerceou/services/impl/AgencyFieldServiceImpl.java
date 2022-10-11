package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.AgentField;
import com.thunv.ecommerceou.repositories.AgentFieldRepository;
import com.thunv.ecommerceou.services.AgencyFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyFieldServiceImpl implements AgencyFieldService {
    @Autowired
    private AgentFieldRepository agentFieldRepository;
    @Override
    public AgentField getAgentFieldByID(int fieldID) throws RuntimeException {
        return this.agentFieldRepository.findById(fieldID).orElseThrow(() -> new RuntimeException("Can not find field with id = " + fieldID));
    }

    @Override
    public List<AgentField> getAllAgencyField() throws RuntimeException{
        try {
            return this.agentFieldRepository.findAll();
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public AgentField createAgentField(AgentField agentField) throws RuntimeException{
        try {
            return this.agentFieldRepository.save(agentField);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public AgentField updateAgentField(AgentField agentField) {
        try {
            return this.agentFieldRepository.save(agentField);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public boolean deleteAgentField(int fieldID) {
        try {
            if (this.agentFieldRepository.existsById(fieldID) == false){
                throw new RuntimeException("Field does not exist");
            }
            this.agentFieldRepository.deleteById(fieldID);
            return true;
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
