package com.korpodrony.services;

import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.AuthUserDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.PermissionLevel;
import com.korpodrony.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersWebServiceTest {

    @Mock
    UserRepositoryDaoInterface userRepositoryDao;

    @InjectMocks
    private UsersWebService testObj;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetAllUsers() {
        //given
        String name = "name";
        String surname = "surname";
        String email = "email";
        when(userRepositoryDao.getAllUsers())
                .thenReturn(Arrays.asList(new UserDTO(0, name, surname, email)));

        //when
        List<UserDTO> result = testObj.getAllUsers();

        //then
        assertThat(result).isEqualTo(Arrays.asList(new UserDTO(0, name, surname, email)));
    }

    @Test
    void shouldGetAllUserEntities() {
        //given
        int user_id = 1;
        String name = "name";
        String surname = "surname";
        PermissionLevel admin = PermissionLevel.ADMIN;
        UserEntity expectedUser = new UserEntity(name, surname, admin);
        expectedUser.setId(user_id);
        when(userRepositoryDao.getAllUsersEntities())
                .thenReturn(Arrays.asList(expectedUser));

        //when
        List<UserEntity> result = testObj.getAllUserEntities();

        //then
        assertThat(result).isEqualTo(Arrays.asList(expectedUser));
    }

    @Test
    void shouldFindUserIdByEmail() {
        //given
        String email = "email";
        int expectedId = 1;
        when(userRepositoryDao.getUserIdByEmail(anyString())).thenReturn(expectedId);

        //when
        int result = testObj.findUserIdByEmail(email);

        //then
        assertThat(result).isEqualTo(expectedId);
    }

    @Test
    void shouldNotFindUserIdByEmail() {
        //given
        String email = "email";
        int expectedId = 0;
        when(userRepositoryDao.getUserIdByEmail(anyString())).thenReturn(expectedId);

        //when
        int result = testObj.findUserIdByEmail(email);

        //then
        assertThat(result).isEqualTo(expectedId);
    }

    @Test
    void shouldCreateUser() {
        //given
        int expectedId = 1;
        String name = "name";
        String surname = "surname";
        PermissionLevel admin = PermissionLevel.ADMIN;
        when(userRepositoryDao.createUser(any())).thenReturn(expectedId);

        //when
        int result = testObj.createUser(new UserEntity(name, surname, admin));

        //then
        assertThat(result).isEqualTo(expectedId);
    }

    @Test
    void shouldUpdatePermissionLevel() {
        //given
        int userId = 0;
        String name = "name";
        String surname = "surname";
        PermissionLevel permissionLevel = PermissionLevel.USER;
        PermissionLevel expectedPermissionLevel = PermissionLevel.ADMIN;
        UserEntity userEntity = new UserEntity(name, surname, permissionLevel);
        when(userRepositoryDao.getUserEntity(anyInt())).thenReturn(userEntity);

        //when
        testObj.updatePermissionLevel(userId, expectedPermissionLevel);

        //then
        assertThat(userEntity.getPermissionLevel()).isEqualTo(expectedPermissionLevel);
    }

    @Test
    void shouldThrowExceptionWhenUpdatePermissionLevel() {
        //given
        int userId = 0;
        String name = "name";
        String surname = "surname";
        PermissionLevel permissionLevel = PermissionLevel.USER;
        UserEntity userEntity = new UserEntity(name, surname, permissionLevel);
        when(userRepositoryDao.getUserEntity(anyInt())).thenReturn(userEntity);

        //when
        //then
        assertThatThrownBy(() -> testObj.updatePermissionLevel(userId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("PermissionLevel cannot be null");
    }

    @Test
    void shouldFindAuthUserDTOByEmail() {
        //given
        int id = 0;
        PermissionLevel admin = PermissionLevel.ADMIN;
        AuthUserDTO expectedAuthUserDTO = new AuthUserDTO(id, admin);
        String email = "email";
        when(userRepositoryDao.getAuthUserDTO(anyString())).thenReturn(expectedAuthUserDTO);

        //when
        AuthUserDTO result = testObj.findAuthUserDTOByEmail(email);

        //then
        assertEquals(expectedAuthUserDTO, result);
    }

    @Test
    void shouldThrowExceptionWhenFindAuthUserDTOByEmail() {
        //given
        int id = 1;
        PermissionLevel admin = PermissionLevel.ADMIN;
        AuthUserDTO expectedAuthUserDTO = new AuthUserDTO(id, admin);
        when(userRepositoryDao.getAuthUserDTO(anyString())).thenReturn(expectedAuthUserDTO);

        //when
        //then
        assertThatThrownBy(() -> testObj.findUserIdByEmail(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email cannot be null");
    }
}