package com.korpodrony.reports.controllers;

import com.korpodrony.reports.client.ReportsConstants;
import com.korpodrony.reports.dto.ReportsStatisticDTO;
import com.korpodrony.reports.interfaces.ReportsStatisticsInterface;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ReportsConstants.API_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class ReportRestServiceImpl implements ReportRestService {

    @Inject
    ReportsStatisticsInterface reportsStatistics;

    @Override
    @POST
    @Path(ReportsConstants.CREATE_ENTRY_ENDPOINT)
    public Response createEntry(@Valid ReportsStatisticDTO reportsStatisticDTO) {

        return Response
                .status(Response.Status.CREATED)
                .entity(reportsStatistics.createReportsStatisticsEntry(reportsStatisticDTO))
                .build();
    }

    @Override
    @GET
    @Path(ReportsConstants.GET_ALL_STATISTICS_ENDPOINT)
    public Response getAllStatistics() {

        return Response.ok(reportsStatistics.getAllReportsStatistics())
                .build();

    }

}