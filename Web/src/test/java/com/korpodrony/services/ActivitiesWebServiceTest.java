package com.korpodrony.services;

import com.korpodrony.daoInterfaces.ActivityRepositoryDaoInterface;
import com.korpodrony.dto.ActivityDTO;
import com.korpodrony.dto.SimplifiedActivityDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.ActivityEntity;
import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.rest.ReportsStatisticsRestConsumerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ActivitiesWebServiceTest {

    @Mock
    ActivityRepositoryDaoInterface activityRepositoryDao;

    @Mock
    ReportsStatisticsRestConsumerInterface reportsStatisticsRestConsumerInterface;

    @InjectMocks
    ActivitiesWebService testObj;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHasActivity() {
        //given
        int activityId = 1;
        when(activityRepositoryDao.hasActivityWithThisID(anyInt())).thenReturn(true);

        //when
        boolean result = testObj.hasActivity(activityId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void testHasNotActivity() {
        //given
        int activityId = 0;
        when(activityRepositoryDao.hasActivityWithThisID(anyInt())).thenReturn(false);

        //when
        boolean result = testObj.hasActivity(activityId);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void testGetAllActivities() {
        //given
        SimplifiedActivityDTO simplifiedActivityDTO = prepareSimplifiedActivityDTO();
        List<SimplifiedActivityDTO> expected = Arrays.asList(simplifiedActivityDTO);
        when(activityRepositoryDao.getAllSimplifiedActivates()).thenReturn(expected);

        //when
        List<SimplifiedActivityDTO> result = testObj.getAllActivities();

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testGetActivityDTO() {
        //given
        int id = 1;
        UserDTO userDTO = prepareUserDTO();
        ActivityDTO activityDTO = prepareActivityDTO(userDTO, id);
        when(activityRepositoryDao.getActivityDTO(anyInt())).thenReturn(activityDTO);

        //when
        ActivityDTO result = testObj.getActivityDTO(id);

        //then
        assertThat(result).isEqualTo(activityDTO);
    }

    @Test
    void testAssignUsersToActivity() {
        //given
        UserEntity userEntity = new UserEntity();
        ActivityEntity activityEntity = new ActivityEntity();
        when(activityRepositoryDao.getActivityEntityWithRelations(anyInt())).thenReturn(activityEntity);
        when(activityRepositoryDao.getUsersEntitiesList(any())).thenReturn(Arrays.asList(userEntity));

        //when
        boolean result = testObj.assignUsersToActivity(Arrays.asList(userEntity.getId()), activityEntity.getId());

        //then
        assertThat(result).isTrue();
        assertThat(activityEntity.getAssigned_users()).contains(userEntity);
    }

    @Test
    void testNotAssignUsersToActivity() {
        //given
        when(activityRepositoryDao.getActivityEntity(anyInt())).thenReturn(null);
        List<Integer> usersIds = Arrays.asList(1);
        int activityId = 2;

        //when
        boolean result = testObj.assignUsersToActivity(usersIds, activityId);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void testUnassignUsersFromActivity() {
        //given
        int activity_id = 1;
        ActivityEntity activityEntity = prepareActivityEntity(activity_id);
        int user_id = 1;
        UserEntity userEntity = prepareUserEntity(user_id);
        prepareAssignedUsers(activityEntity, userEntity);
        when(activityRepositoryDao.getActivityEntityWithRelations(anyInt())).thenReturn(activityEntity);

        //when
        boolean result = testObj.unassignUsersFromActivity(Arrays.asList(Integer.valueOf(user_id)), activity_id);

        //then
        assertThat(result).isTrue();
        assertThat(activityEntity.getAssigned_users()).doesNotContain(userEntity);
    }

    @Test
    void testNotUnassignUsersFromActivity() {
        //given
        int activity_id = 1;
        int user_id = 1;
        when(activityRepositoryDao.getActivityEntity(anyInt())).thenReturn(null);

        //when
        boolean result = testObj.unassignUsersFromActivity(Arrays.asList(user_id), activity_id);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void testDeleteActivity() {
        //given
        int activityId = 1;
        when(activityRepositoryDao.deleteActivity(anyInt())).thenReturn(true);

        //when
        boolean result = testObj.deleteActivity(activityId);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void testEditActivity() {
        //given
        ActivityEntity activityEntity = new ActivityEntity();
        int activityId = 0;
        String name = "name";
        short maxUsers = (short) 1;
        byte duration = (byte) 1;
        int activityTypeNumber = 1;
        when(activityRepositoryDao.getActivityEntity(anyInt())).thenReturn(activityEntity);

        //when
        boolean result = testObj.editActivity(activityId, name, maxUsers, duration, activityTypeNumber);

        //then
        assertThat(result).isTrue();
        assertThat(activityEntity.getName()).isEqualTo(name);
        assertThat(activityEntity.getMaxUsers()).isEqualTo(maxUsers);
        assertThat(activityEntity.getLengthInQuarters()).isEqualTo(duration);
        assertThat(activityEntity.getActivitiesType()).isEqualTo(ActivitiesType.getActivity(activityTypeNumber));
    }

    @Test
    void testCreateActivity() {
        //given
        int expectedId = 0;
        int activityType = 1;
        String name = "name";
        short maxUsers = (short) 0;
        byte duration = (byte) 0;
        when(activityRepositoryDao.createActivity(any())).thenReturn(expectedId);

        //when
        int result = testObj.createActivity(name, maxUsers, duration, activityType);

        //then
        assertThat(result).isEqualTo(expectedId);
    }

    @Test
    void testGetAvailableUserDTO() {
        //given
        UserDTO userDTO = prepareUserDTO();
        List<UserDTO> expected = Arrays.asList(userDTO);
        int activityId = 0;
        when(activityRepositoryDao.getAvailableUsersDTO(anyInt())).thenReturn(expected);

        //when
        List<UserDTO> result = testObj.getAvailableUserDTO(activityId);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void testGetAllActivitiesByActivityType() {
        //given
        ActivitiesType lecture = ActivitiesType.LECTURE;
        SimplifiedActivityDTO simplifiedActivityDTO = prepareSimplifiedActivityDTO(lecture);
        List<SimplifiedActivityDTO> expected = Arrays.asList(simplifiedActivityDTO);
        when(activityRepositoryDao.getAllSimplifiedActivates(lecture)).thenReturn(expected);

        //when
        List<SimplifiedActivityDTO> result = testObj.getAllActivitiesByActivityType(lecture);

        //then
        assertThat(result).isEqualTo(expected);
    }

    private SimplifiedActivityDTO prepareSimplifiedActivityDTO() {
        int id = 0;
        String name = "name";
        ActivitiesType activitiesType = ActivitiesType.LECTURE;
        return new SimplifiedActivityDTO(id, name, activitiesType);
    }

    private UserDTO prepareUserDTO() {
        int id = 1;
        String name = "name";
        String surname = "surname";
        String email = "email";
        return new UserDTO(id, name, surname, email);
    }

    private ActivityDTO prepareActivityDTO(UserDTO userDTO, int id) {
        String name = "name";
        byte duration = (byte) 3;
        short maxUsers = (short) 30;
        ActivitiesType activitiesType = ActivitiesType.LECTURE;
        return new ActivityDTO(id, name, maxUsers, Arrays.asList(userDTO), duration, activitiesType);
    }

    private ActivityEntity prepareActivityEntity(int activity_id) {
        ActivityEntity activityEntity = new ActivityEntity();
        activityEntity.setId(activity_id);
        return activityEntity;
    }

    private UserEntity prepareUserEntity(int userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        return userEntity;
    }

    private void prepareAssignedUsers(ActivityEntity activityEntity, UserEntity userEntity) {
        HashSet<UserEntity> users = new HashSet<>();
        users.add(userEntity);
        activityEntity.setAssigned_users(users);
    }

    private SimplifiedActivityDTO prepareSimplifiedActivityDTO(ActivitiesType lecture) {
        int id = 1;
        String name = "name";
        return new SimplifiedActivityDTO(id, name, lecture);
    }
}