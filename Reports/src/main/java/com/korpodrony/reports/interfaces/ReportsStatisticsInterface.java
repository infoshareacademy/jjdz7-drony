package com.korpodrony.reports.interfaces;

import com.korpodrony.reports.dto.ReportsStatisticDTO;
import com.korpodrony.reports.entity.ReportsStatisticsEntity;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ReportsStatisticsInterface {

    int createReportsStatisticsEntry(ReportsStatisticDTO reportsStatisticDTO);

    List<ReportsStatisticsEntity> getAllReportsStatistics();

}
