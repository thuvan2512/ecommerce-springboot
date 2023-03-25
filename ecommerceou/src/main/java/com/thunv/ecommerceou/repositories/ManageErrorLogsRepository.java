package com.thunv.ecommerceou.repositories;

import com.thunv.ecommerceou.models.pojo.ManageErrorLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManageErrorLogsRepository extends JpaRepository<ManageErrorLog, Integer> {
    List<ManageErrorLog> findByTypeLog(String typeLog);
}
