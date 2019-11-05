package com.korpodrony.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import java.io.IOException;

@RequestScoped
public class TemplateProvider {

    public static final String ACTIVITY_PATH = "activity.ftlh";
    public static final String ADD_ACTIVITY_PATH = "activity-add.ftlh";
    public static final String INDEX_PATH = "index.ftlh";
    public static final String USERS_PATH = "users.ftlh";
    private final String TEMPLATE_DIRECTORY_PATH = "WEB-INF/fm-templates";
    private Configuration configuration;

    @Inject
    private ConfigProvider configProvider;

    public Template getTemplate(ServletContext servletContext, String templateName) throws IOException {
        configuration = configProvider.getConfiguration();
        configuration.setServletContextForTemplateLoading(servletContext, TEMPLATE_DIRECTORY_PATH);
        return configuration.getTemplate(templateName);
    }
}
