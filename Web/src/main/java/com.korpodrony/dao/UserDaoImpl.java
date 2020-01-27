package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.AuthUserDTO;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.PermissionLevel;
import com.korpodrony.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserDaoImpl implements UserRepositoryDaoInterface {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    Logger logger = LoggerFactory.getLogger("com.korpodrony.dao");

    public int createUser(String name, String surname, String email, PermissionLevel permissionLevel) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPermissionLevel(permissionLevel);
        entityManager.persist(user);
        logger.info("created user: " + user + " from name: " + name + ", surname: " + surname + ", email: " + email);
        return user.getId();
    }

    @Override
    public AuthUserDTO getAuthUserDTO(String email) {
        try {
            logger.debug("Getting userDTO for email: " + email);
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.AuthUserDTO(u.id, u.permissionLevel) FROM User u WHERE " +
                                    "u.email=:email"
                            , AuthUserDTO.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.info("Doesn't have user with email: " + email);
            return null;
        }
    }

    public List<UserDTO> getAllUsers() {
        logger.debug("Getting list of UserDTOs");
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname, u.email) FROM User u " +
                                "WHERE u.permissionLevel=:level"
                        , UserDTO.class)
                .setParameter("level", PermissionLevel.USER)
                .getResultList();
    }

    @Override
    public List<UserDTO> getUserDTObyName(String name) {
        try {
            logger.debug("Getting userDTOS by name: " + name);
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname, u.email) FROM User u " +
                                    "WHERE " +
                                    "lower(u.name) LIKE :name Or lower(u.surname) LIKE :name"
                            , UserDTO.class)
                    .setParameter("name", "%" + name + "%")
                    .getResultList();
        } catch (NoResultException e) {
            logger.info("No users which name contains: " + name);
            return new ArrayList<>();
        }
    }

    @Override
    public List<UserDTO> getUserDTObyName(String name, String surname) {
        try {
            logger.debug("Getting userDTOS by name: " + name + "and surname: " + surname);

            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname, u.email) FROM User u " +
                                    "WHERE " +
                                    "lower(u.name) LIKE :name AND lower(u.surname) LIKE :surname"
                            , UserDTO.class)
                    .setParameter("name", "%" + name + "%")
                    .setParameter("surname", "%" + surname + "%")
                    .getResultList();
        } catch (NoResultException e) {
            logger.info("No users which name contains: " + name + "and surname contains: " + surname);
            return new ArrayList<>();
        }
    }

    @Override
    public void updateUserPermissionLevel(int userId, PermissionLevel permissionLevel) {
        UserEntity userEntity = getUserEntity(userId);
        userEntity.setPermissionLevel(permissionLevel);
        entityManager.merge(userEntity);
    }

    @Override
    public int getUserIdByEmail(String email) {
        try {
            logger.debug("Getting user id for email: " + email);
            return (int) entityManager
                    .createQuery("SELECT u.id FROM User u where u.email=:email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.info("Doesn't have user with email: " + email);
            return 0;
        }
    }

    @Override
    public List<UserEntity> getAllUsersEntities() {
        return entityManager
                .createQuery("SELECT u FROM User u", UserEntity.class)
                .getResultList();
    }

    private UserEntity getUserEntity(int userId) {
        return entityManager.find(UserEntity.class, userId);
    }
}