package com.thunv.ecommerceou.repositories.custom;

import java.util.List;

public interface RenewalOrderRepositoryCustom {
    List<Object[]> getStatsRevenueMonthByYear(int year);
    List<Object[]> getStatsRevenueQuarterByYear(int year);
    List<Object[]> getStatsRevenueYear();
}
