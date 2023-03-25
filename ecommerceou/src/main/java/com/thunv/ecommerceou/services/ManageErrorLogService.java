package com.thunv.ecommerceou.services;

import com.thunv.ecommerceou.models.pojo.ManageErrorLog;
import io.swagger.models.auth.In;

import java.util.List;

public interface ManageErrorLogService {
    ManageErrorLog getLogByID(Integer logID);
    ManageErrorLog saveThirdPartyErrorLog(ManageErrorLog manageErrorLog);
    List<ManageErrorLog> getAllManageErrorLogs(String logType);
}
