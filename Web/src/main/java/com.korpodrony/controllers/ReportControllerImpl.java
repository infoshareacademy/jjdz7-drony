package com.korpodrony.controllers;

import com.korpodrony.reports.client.ReportsConstants;
import com.korpodrony.reports.dto.ReportsStatisticDTO;
import com.korpodrony.reports.interfaces.ReportsStatisticsInterface;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ReportsConstants.API_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReportControllerImpl {

    @Inject
    ReportsStatisticsInterface reportsStatistics;

    @POST
    @Path(ReportsConstants.CREATE_ENTRY_ENDPOINT)
    public Response createEntry(@Valid ReportsStatisticDTO reportsStatisticDTO) {

        int reportsStatisticsEntryId = reportsStatistics.createReportsStatisticsEntry(reportsStatisticDTO);

        return Response.ok().build();
    }

    @GET
    @Path(ReportsConstants.GET_ALL_STATISTICS_ENDPOINT)
    public Response getAllStatistics() {

        return Response.ok(reportsStatistics.getAllReportsStatistics())
                .build();

    }

}