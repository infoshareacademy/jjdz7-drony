package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.UserDTO;
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

    public int createUser(String name, String surname, String email) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        entityManager.persist(user);
        logger.info("created user: " + user + " from name: " + name + ", surname: " + surname + ", email: " + email);
        return user.getId();
    }

    public boolean deleteUser(int userID) {
        if (hasUser(userID)) {
            entityManager.createQuery("DELETE FROM User u WHERE u.id=:id")
                    .setParameter("id", userID)
                    .executeUpdate();
            entityManager.flush();
            entityManager.clear();
            logger.info("User with id: " + userID + "has been removed");
            return true;
        } else {
            logger.info("User with id: " + userID + "doesn't exist");
            return false;
        }
    }

    public UserDTO getUserDTO(int userID) {
        try {
            logger.debug("Getting userDTO for id: " + userID);
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname, u.email) FROM User u WHERE " +
                                    "u.id=:id"
                            , UserDTO.class)
                    .setParameter("id", userID)
                    .getSingleResult();
        } catch (NoResultException e) {
            logger.info("Doesn't have user with id: " + userID);
            return null;
        }
    }

    public boolean editUser(int userID, String name, String surname) {
        if (hasUser(userID)) {
            UserEntity userEntity = getUserEntity(userID);
            logger.debug("User before changes: " + userEntity);
            logger.debug("Values of fields which will be changed: " + "name: " + name + ", surname" + surname);
            userEntity.setName(name);
            userEntity.setSurname(surname);
            entityManager.merge(userEntity);
            logger.debug("User after changes: " + userEntity);
            return true;
        }
        logger.debug("No user with id: " + userID);
        return false;
    }

    public List<UserDTO> getAllUsers() {
        logger.debug("Getting list of UserDTOs");
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname, u.email) FROM User u"
                        , UserDTO.class)
                .getResultList();
    }

    @Override
    public boolean hasUser(int userID) {
        try {
            entityManager
                    .createQuery("SELECT u.id FROM User u where u.id=:id")
                    .setParameter("id", userID)
                    .getSingleResult();
            logger.debug("Has user with id: " + userID);
            return true;
        } catch (NoResultException e) {
            logger.info("Doesn't have user with id: " + userID);
            return false;
        }
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

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private UserEntity getUserEntity(int userID) {
        logger.debug("Getting userEntity for id: " + userID);
        return entityManager.find(UserEntity.class, userID);
    }
}
