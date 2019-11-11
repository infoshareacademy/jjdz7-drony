package com.korpodrony.services;

import com.korpodrony.comparators.PlanIDComparator;
import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.model.Plan;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class PlansWebService {

    @EJB
    OrganizationRepositoryDao organizationRepositoryDao;

    public List<Plan> getAllPlans() {
        return organizationRepositoryDao.getAllPlans()
                .stream()
                .sorted((x, y) -> new PlanIDComparator()
                        .compare(x, y))
                .collect(Collectors.toList());
    }
}
