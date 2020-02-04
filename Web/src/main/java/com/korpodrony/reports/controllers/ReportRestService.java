package com.korpodrony.reports.controllers;

import com.korpodrony.reports.dto.ReportsStatisticDTO;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.ws.rs.core.Response;

@Local
public interface ReportRestService {

    Response createEntry(@Valid ReportsStatisticDTO reportsStatisticDTO);

    Response getAllStatistics();
}
