package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.ReportsStatisticsDaoInterface;
import com.korpodrony.dto.ReportsStatisticDTO;
import com.korpodrony.entity.Action;
import com.korpodrony.entity.ReportsStatisticsEntity;
import com.korpodrony.entity.View;
import com.korpodrony.services.CurrentUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Stateless
public class ReportsStatisticsDaoImpl implements ReportsStatisticsDaoInterface {

    private Logger logger = LoggerFactory.getLogger("com.korpodrony.dao");

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    @Inject
    CurrentUserService currentUserService;

    @Override
    public int createReportsStatisticsEntry(View view, Action action) {

        String email = currentUserService.getEmail();
        if (email == null) {
            logger.error("User not logged. Statistics not added.");
            return 0;
        }

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.systemDefault());

        ReportsStatisticsEntity reportsStatistics = new ReportsStatisticsEntity();
        reportsStatistics.setEmail(email);
        reportsStatistics.setView(view);
        reportsStatistics.setAction(action);
        reportsStatistics.setTimeOfAction(currentTime);
        entityManager.persist(reportsStatistics);
        logger.info("created reports statistics: " + toString());
        return reportsStatistics.getId();
    }

    @Override
    public List<ReportsStatisticDTO> getAllReportsStatistics() {
        logger.debug("Getting list of ReportsStatisticDTO");
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.ReportsStatisticDTO(r.id, r.email, r.view, r.action, r.timeOfAction) FROM ReportsStatistics r", ReportsStatisticDTO.class)
                .getResultList();
    }
}
