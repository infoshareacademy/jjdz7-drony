package com.korpodrony.reports.services;

import com.korpodrony.reports.controllers.ReportRestService;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RestServices extends Application {

    private Set<Object> singletons = new HashSet<>();

    @Inject
    private ReportRestService reportRestService;

    public RestServices() {
        singletons.add(reportRestService);
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
