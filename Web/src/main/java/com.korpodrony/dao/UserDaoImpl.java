package com.korpodrony.dao;

import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.entity.UserEntity;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UserDaoImpl implements UserRepositoryDaoInterface {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    public boolean createUser(String name, String surname) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        entityManager.persist(user);
        return true;
    }


    public boolean deleteUser(int userID) {
        if (hasUserWithThisID(userID)) {
            entityManager.createQuery("DELETE FROM User u WHERE u.id=:id")
                    .setParameter("id", userID)
                    .executeUpdate();
            entityManager.flush();
            entityManager.clear();
            return true;
        }else {
            return false;
        }
    }

    public UserDTO getUserDTO(int userID) {
        try {
            return entityManager
                    .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname) FROM User u WHERE " +
                                    "u.id=:id"
                            , UserDTO.class)
                    .setParameter("id", userID)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean editUser(int userID, String name, String surname) {
        if (hasUserWithThisID(userID)) {
            UserEntity userEntity = getUserEntity(userID);
            userEntity.setName(name);
            userEntity.setSurname(surname);
            entityManager.merge(userEntity);
            return true;
        }
        return false;
    }

    public List<UserDTO> getAllUsers() {
        return entityManager
                .createQuery("SELECT new com.korpodrony.dto.UserDTO(u.id, u.name, u.surname) FROM User u"
                        , UserDTO.class)
                .getResultList();
    }

    @Override
    public boolean hasUserWithThisID(int userID) {
        try {
            Object id = entityManager
                    .createQuery("SELECT u.id FROM User u where u.id=:id")
                    .setParameter("id", userID)
                    .getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private UserEntity getUserEntity(int userID) {
        return entityManager.find(UserEntity.class, userID);
    }
}
