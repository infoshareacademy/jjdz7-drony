package com.korpodrony.daoInterfaces;

import com.korpodrony.dto.ReportsStatisticDTO;
import com.korpodrony.entity.Action;
import com.korpodrony.entity.View;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ReportsStatisticsDaoInterface {

    int createReportsStatisticsEntry(View view, Action action);

    List<ReportsStatisticDTO> getAllReportsStatistics();


}
