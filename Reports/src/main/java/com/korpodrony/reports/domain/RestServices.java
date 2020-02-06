package com.korpodrony.reports.domain;

import com.korpodrony.reports.controllers.ReportControllerImpl;
import com.korpodrony.reports.exceptions.AllExceptionHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/rest")
public class RestServices extends Application {

    private Set<Object> singletons = new HashSet<>();

//    @Inject
//    ReportControllerImpl reportController;

    public RestServices() {

        singletons.add(new ReportControllerImpl());
        singletons.add(AllExceptionHandler.class);
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    @Override
    public Map<String, Object> getProperties() {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("jersey.config.server.provider.packages",
                "com.korpodrony.reports.domain");
        return properties;
    }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        classes.add(ObjectMapperContextResolver.class);
        return classes;
    }
}
