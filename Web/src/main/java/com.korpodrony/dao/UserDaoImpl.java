package com.korpodrony.dao;

import com.korpodrony.entity.UserEntity;
import com.korpodrony.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class UserDaoImpl implements UserRepositoryDao {

    @PersistenceContext(unitName = "korpodrony-hibernate")
    private EntityManager entityManager;

    @Override
    public boolean createUser(String name, String surname) {
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setSurname(surname);
        entityManager.persist(user);
        return true;
    }


    @Override
    public boolean deleteUser(int userID) {
        if (getUser(userID) != null) {
            entityManager.createQuery("DELETE FROM User u WHERE u.user_id=:id")
                    .setParameter("id", userID)
                    .executeUpdate();
            entityManager.flush();
            entityManager.clear();
            return true;
        }
        return false;
    }

    @Override
    public User getUser(int userID) {
        UserEntity userEntity = getUserEntity(userID);
        return userEntity == null ? null : userEntity.getUserFromEntity();
    }

    @Override
    public boolean editUser(int userID, String name, String surname) {
        if (hasUserWithThisID(userID)) {
            UserEntity userEntity = entityManager.find(UserEntity.class, userID);
            userEntity.setName(name);
            userEntity.setSurname(surname);
            entityManager.merge(userEntity);
            return true;
        }
        return false;
    }

    @Override
    public List<Integer> getAllUsersIDs() {
        return entityManager
                .createQuery("SELECT u.user_id FROM User u")
                .getResultList();
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> resultList = entityManager
                .createQuery("SELECT u FROM User u")
                .getResultList();
        return resultList
                .stream()
                .map(UserEntity::getUserFromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasUser(User user) {
        UserEntity result = entityManager.createQuery("SELECT u FROM User u WHERE " +
                "u.user_id = :id AND u.name=:name AND u.surname=:surname", UserEntity.class)
                .setParameter("id", user.getId())
                .setParameter("name", user.getName())
                .setParameter("surname", user.getSurname())
                .getSingleResult();
        return result != null;
    }

    @Override
    public boolean hasUserWithThisID(int userID) {
        return getUser(userID) != null;
    }

    @Override
    public Set<User> getUsersSet() {
        return new HashSet<>(getAllUsers());
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private UserEntity getUserEntity(int userID) {
        return entityManager.find(UserEntity.class, userID);
    }
}
