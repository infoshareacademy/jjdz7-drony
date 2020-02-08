package com.korpodrony.services;

import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.dto.PlanDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.SimplifiedPlanDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.PlanEntity;
import com.korpodrony.model.ActivitiesType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PlansWebServiceTest {
    @Mock
    PlanRepositoryDaoInterface planRepositoryDao;

    @InjectMocks
    PlansWebService plansWebService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllPlans() {
        //given
        SimplifiedPlanDTO simplifiedPlanDTO = prepareSimplifiedPlanDTO();
        List<SimplifiedPlanDTO> expected = Arrays.asList(simplifiedPlanDTO);
        when(planRepositoryDao.getAllSimplifiedPlansDTO()).thenReturn(expected);

        //when
        List<SimplifiedPlanDTO> result = plansWebService.getAllPlans();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testHasPlan() {
        //given
        int planId = 1;
        when(planRepositoryDao.hasPlanWithThisID(anyInt())).thenReturn(true);

        //when
        boolean result = plansWebService.hasPlan(planId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void testGetPlanDTO() {
        //given
        int id = 1;
        SimplifiedActivityDTO simplifiedActivityDTO = prepareSimplifiedActivityDTO();
        PlanDTO expected = preparePlanDTO(simplifiedActivityDTO);
        when(planRepositoryDao.getPlanDTO(anyInt())).thenReturn(expected);

        //when
        PlanDTO result = plansWebService.getPlanDTO(id);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testDeletePlan() {
        //given
        int planId = 1;
        when(planRepositoryDao.deletePlan(anyInt())).thenReturn(true);

        //when
        boolean result = plansWebService.deletePlan(planId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void testGetAvailableActivities() {
        //given
        SimplifiedActivityDTO simplifiedActivityDTO = prepareSimplifiedActivityDTO();
        List<SimplifiedActivityDTO> expected = Arrays.asList(simplifiedActivityDTO);
        when(planRepositoryDao.getAvailableSimplifiedActivitiesDTO(anyInt())).thenReturn(expected);
        int planId = 1;

        //when
        List<SimplifiedActivityDTO> result = plansWebService.getAvailableActivities(planId);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testAssignActivitiesToPlan() {
        //given
        PlanEntity planEntity = new PlanEntity();
        planEntity.setAssignedActivities(new HashSet<>());
        ActivityEntity activityEntity = new ActivityEntity();
        List<ActivityEntity> expected = Arrays.asList(activityEntity);
        when(planRepositoryDao.getPlanEntityWithRelations(anyInt())).thenReturn(planEntity);
        when(planRepositoryDao.getActivitiesEntitiesList(any())).thenReturn(expected);
        List<Integer> activitiesIds = Arrays.asList(Integer.valueOf(activityEntity.getId()));

        //when
        boolean result = plansWebService.assignActivitiesToPlan(activitiesIds, planEntity.getId());

        //then
        assertThat(result).isTrue();
        assertThat(planEntity.getAssignedActivities()).contains(activityEntity);
    }

    @Test
    void testNotAssignActivitiesToPlan() {
        //given
        PlanEntity planEntity = new PlanEntity();
        int id = 1;
        when(planRepositoryDao.getPlanEntity(anyInt())).thenReturn(null);
        when(planRepositoryDao.getActivitiesEntitiesList(any())).thenReturn(null);
        List<Integer> activitiesIds = Arrays.asList(id);

        //when
        boolean result = plansWebService.assignActivitiesToPlan(activitiesIds, planEntity.getId());

        //then
        assertThat(result).isFalse();
    }

    @Test
    void testUnassignActivitiesFromPlan() {
        //given
        PlanEntity planEntity = new PlanEntity();
        ActivityEntity activityEntity = new ActivityEntity();
        planEntity.setAssignedActivities(Set.of(activityEntity));
        List<Integer> activitiesIds = Arrays.asList(activityEntity.getId());
        when(planRepositoryDao.getPlanEntityWithRelations(anyInt())).thenReturn(planEntity);

        //when
        boolean result = plansWebService.unassignActivitiesFromPlan(activitiesIds, planEntity.getId());

        //then
        assertThat(result).isTrue();
        assertThat(planEntity.getAssignedActivities()).isEmpty();
    }

    @Test
    void testNotUnassignActivitiesFromPlan() {
        //given
        ActivityEntity activityEntity = new ActivityEntity();
        List<Integer> activitiesIds = Arrays.asList(activityEntity.getId());
        when(planRepositoryDao.getPlanEntity(anyInt())).thenReturn(null);
        int planId = 1;

        //when
        boolean result = plansWebService.unassignActivitiesFromPlan(activitiesIds, planId);

        //then
        assertThat(result).isFalse();
    }


    @Test
    void testEditPlan() {
        //given
        PlanEntity planEntity = new PlanEntity();
        when(planRepositoryDao.getPlanEntity(anyInt())).thenReturn(planEntity);
        String name = "name";

        //when
        boolean result = plansWebService.editPlan(planEntity.getId(), name);

        //then
        assertThat(result).isTrue();
        assertThat(planEntity.getName()).isEqualTo(name);
    }

    @Test
    void testNotEditPlan() {
        //given
        int planId = 1;
        when(planRepositoryDao.getPlanEntity(anyInt())).thenReturn(null);
        String name = "name";

        //when
        boolean result = plansWebService.editPlan(planId, name);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void testCreatePlan() {
        //given
        int expectedId = 1;
        when(planRepositoryDao.createPlan(any())).thenReturn(expectedId);
        String name = "name";

        //when
        int result = plansWebService.createPlan(name);

        //then
        assertThat(result).isEqualTo(expectedId);
    }

    private SimplifiedPlanDTO prepareSimplifiedPlanDTO() {
        int id = 0;
        String name = "name";
        return new SimplifiedPlanDTO(id, name);
    }

    private SimplifiedActivityDTO prepareSimplifiedActivityDTO() {
        return new SimplifiedActivityDTO(0, "name", ActivitiesType.LECTURE);
    }

    private PlanDTO preparePlanDTO(SimplifiedActivityDTO simplifiedActivityDTO) {
        return new PlanDTO(0, "name", Arrays.asList(simplifiedActivityDTO));
    }
}