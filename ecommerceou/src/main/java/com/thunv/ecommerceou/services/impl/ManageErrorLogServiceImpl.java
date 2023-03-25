package com.thunv.ecommerceou.services.impl;

import com.thunv.ecommerceou.models.pojo.ManageErrorLog;
import com.thunv.ecommerceou.repositories.ManageErrorLogsRepository;
import com.thunv.ecommerceou.services.ManageErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageErrorLogServiceImpl implements ManageErrorLogService {
    @Autowired
    private ManageErrorLogsRepository manageErrorLogsRepository;

    @Override
    public ManageErrorLog getLogByID(Integer logID) throws RuntimeException{
        return this.manageErrorLogsRepository.findById(logID).orElseThrow(() ->
                new RuntimeException("Can not find log with id = " + logID));
    }

    @Override
    public ManageErrorLog saveThirdPartyErrorLog(ManageErrorLog manageErrorLog) throws RuntimeException{
        try {
            return this.manageErrorLogsRepository.save(manageErrorLog);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }

    @Override
    public List<ManageErrorLog> getAllManageErrorLogs(String logType) throws RuntimeException{
        try {
            return this.manageErrorLogsRepository.findByTypeLog(logType);
        }catch (Exception ex){
            String error_ms = ex.getMessage();
            throw new RuntimeException(error_ms);
        }
    }
}
