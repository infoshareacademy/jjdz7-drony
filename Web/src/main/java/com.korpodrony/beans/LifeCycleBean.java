package com.korpodrony.beans;

import com.korpodrony.service.RepositoryService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class LifeCycleBean {

    @Inject
    RepositoryService repositoryService;

    @PostConstruct
    public void init() {
        repositoryService.loadRepositoryFromFile();
    }

    @PreDestroy
    public void destroy() {
    }
}