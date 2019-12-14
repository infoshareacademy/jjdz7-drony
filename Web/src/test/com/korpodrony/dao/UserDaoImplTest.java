package com.korpodrony.dao;

import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoImplTest {
    private EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("test");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private EntityTransaction transaction = entityManager.getTransaction();
    private UserDaoImpl testObj = new UserDaoImpl();

    @BeforeEach
    void clearDB() {
        entityManager = entityManagerFactory.createEntityManager();
        testObj.setEntityManager(entityManager);
        transaction = entityManager.getTransaction();
    }

    @Test
    void createUser() {
        // given
        String name = "Jan";
        String surname = "Maria";
        UserEntity expectedObj = new UserEntity();
        expectedObj.setName(name);
        expectedObj.setSurname(surname);

        // when
        transaction.begin();
        testObj.createUser(name, surname);
        transaction.commit();

        // then
        assertThat(entityManager.find(UserEntity.class, 1)).isEqualTo(expectedObj);
    }

    @Test
    void getUserTest() {
        // given
        User expectedUser = new User("Marwin", "Darwin");
        transaction.begin();
        UserEntity user = new UserEntity();
        user.setName(expectedUser.getName());
        user.setSurname(expectedUser.getSurname());
        entityManager.persist(user);
        transaction.commit();

        // when
        User result = testObj.getUser(1);

        // then
        assertThat(result).isEqualTo(expectedUser);
    }

    @Test
    void getUserTestNonExistingCase() {
        // given
        User expectedUser = null;

        // when
        User result = testObj.getUser(1);

        // then
        assertThat(result).isNull();
    }

    @Test
    void deleteUserTest() {
        // given
        transaction.begin();
        UserEntity user = new UserEntity();
        user.setName("Marwin");
        user.setSurname("B");
        entityManager.persist(user);
        transaction.commit();

        // when
        transaction.begin();
        boolean result = testObj.deleteUser(1);
        transaction.commit();

        // then
        assertThat(result).isTrue();
        assertThat(entityManager.find(UserEntity.class, 1)).isNull();
    }

    @Test
    void notDeleteUserTest() {
        // given

        // when
        transaction.begin();
        boolean result = testObj.deleteUser(1);
        transaction.commit();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void editUserTest() {
        // given;
        transaction.begin();
        UserEntity user = new UserEntity();
        user.setName("Marwin");
        user.setSurname("B");
        entityManager.persist(user);
        transaction.commit();
        String expectedSurame = "C";

        // when
        transaction.begin();
        boolean result = testObj.editUser(1, "Marwin", "C");
        transaction.commit();

        // then
        assertThat(result).isTrue();
        assertThat(assertThat(entityManager.find(UserEntity.class, 1).getSurname())
                .isEqualTo(expectedSurame));

    }

    @Test
    void getAllUsers() {
        // given
        UserEntity user1 = new UserEntity("adam", "madam");
        UserEntity user2 = new UserEntity("ada", "madam");
        UserEntity user3 = new UserEntity("ad", "madam");
        transaction.begin();
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.persist(user3);
        transaction.commit();
        List<User> expectedList = new ArrayList<>();
        expectedList.add(user1.getUserFromEntity());
        expectedList.add(user2.getUserFromEntity());
        expectedList.add(user3.getUserFromEntity());

        // when
        List<User> result = testObj.getAllUsers();

        // then
        assertThat(result).isEqualTo(expectedList);
    }


    @Test
    void getAllUsersReturnNull() {
        // given
        List<User> expectedUser = new ArrayList<>();

        // when
        List<User> result = testObj.getAllUsers();

        // then
        assertThat(result).isEqualTo(expectedUser);
    }

    @Test
    void notDeleteUser() {
        // given
        int userId = 1;

        // when
        transaction.begin();
        boolean result = testObj.deleteUser(userId);
        transaction.commit();

        // then
        assertThat(result).isFalse();
        assertThat(entityManager.find(UserEntity.class, userId)).isNull();
    }

    @Test
    void notEditUser() {
        // given
        int id = 1;
        String name = "adam";
        String surname = "badum";

        // when
        transaction.begin();
        boolean result = testObj.editUser(id, name, surname);
        transaction.commit();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void NotHasUserTest() {
        // given
        int id = 1;

        // when
        boolean result = testObj.hasUserWithThisID(id);

        // then
        assertThat(result).isFalse();
    }
}