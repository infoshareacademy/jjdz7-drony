package com.korpodrony.services;

import com.korpodrony.reports.client.ReportsConstants;
import com.korpodrony.reports.dto.ReportsStatisticDTO;
import com.korpodrony.reports.entity.Action;
import com.korpodrony.reports.entity.View;
import com.korpodrony.rest.ReportsStatisticsRestConsumerInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

@Stateless
public class ReportsStatisticsRestConsumerImpl implements ReportsStatisticsRestConsumerInterface {

    private Logger logger = LoggerFactory.getLogger(ReportsStatisticsRestConsumerImpl.class);

    @Inject
    CurrentUserService currentUserService;

    @Override
    public void createReportsStatisticsEntry(View view, Action action) {

        String email = currentUserService.getEmail();
        if (email == null) {
            logger.error("User not logged. Statistics not added.");
            return;
        }

        Client client = ClientBuilder.newClient();
        String restUrl = ReportsConstants.URL + ReportsConstants.CREATE_ENTRY_ENDPOINT;
        WebTarget target = client.target(restUrl);

        ReportsStatisticDTO reportStatisticsDto = createReportStatisticsDTO(view, action, email);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(reportStatisticsDto, MediaType.APPLICATION_JSON));

        int status = response.getStatus();

        logger.info("created reports statistics status: {} entry: {} ", status, reportStatisticsDto);
    }

    private ReportsStatisticDTO createReportStatisticsDTO(View view, Action action, String email) {

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());

        ReportsStatisticDTO reportsStatisticDTO = new ReportsStatisticDTO();
        reportsStatisticDTO.setAction(action);
        reportsStatisticDTO.setEmail(email);
        reportsStatisticDTO.setView(view);
        reportsStatisticDTO.setTimeOfAction(currentTime);
        return reportsStatisticDTO;

    }

    @Override
    public List<ReportsStatisticDTO> getAllReportsStatistics() {
        logger.debug("Getting list of all statistics");

        // TODO must be implemented

        return Collections.emptyList();
    }

}
