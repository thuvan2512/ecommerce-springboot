package com.thunv.ecommerceou.services;


import com.thunv.ecommerceou.models.pojo.AgentField;

import java.util.List;

public interface AgencyFieldService {
    AgentField getAgentFieldByID(int fieldID);
    List<AgentField> getAllAgencyField();
    AgentField createAgentField(AgentField agentField);
    AgentField updateAgentField(AgentField agentField);
    boolean deleteAgentField(int fieldID);
}
