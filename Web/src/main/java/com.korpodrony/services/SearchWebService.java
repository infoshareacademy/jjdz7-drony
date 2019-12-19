package com.korpodrony.services;

import com.korpodrony.comparators.ActivityIDComparator;
import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.daoInterfaces.ActivityRepositoryDaoInterface;
import com.korpodrony.daoInterfaces.PlanRepositoryDaoInterface;
import com.korpodrony.daoInterfaces.UserRepositoryDaoInterface;
import com.korpodrony.dto.UserDTO;
import com.korpodrony.model.User;
import com.korpodrony.utils.JSONWriter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequestScoped
public class SearchWebService {

    @EJB
    PlanRepositoryDaoInterface planRepositoryDao;

    @EJB
    UserRepositoryDaoInterface userRepositoryDao;

    @EJB
    ActivityRepositoryDaoInterface activityRepositoryDao;

    public String getUsersByName(String name) {
        String[] credentials = name.split(" ");
        List<UserDTO> userDTObyName;
        if (credentials.length == 1) {
            userDTObyName = userRepositoryDao.getUserDTObyName(credentials[0]);
        } else {
            userDTObyName = userRepositoryDao.getUserDTObyName(credentials[0], credentials[1]);
        }
        return JSONWriter.generateJsonString(
                userDTObyName
        );
    }

    public String getActivitiesByName(String name) {
        return JSONWriter.generateJsonString(activityRepositoryDao.getAllSimplifiedActivates(name)
        );
    }

    public String getPlansByName(String name) {
        return JSONWriter.generateJsonString(
                planRepositoryDao.getAllSimplifiedPlansDTO(name)
        );
    }
}