package com.korpodrony.servlet.admin;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.model.ActivitiesType;
import com.korpodrony.services.ActivitiesWebService;
import com.korpodrony.validation.Validator;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("admin/activities")
public class ActivitiesServlet extends HttpServlet {

    private final int LECTURE_ACTIVITY_TYPE_NUMBER = 1;
    private final int EXERCISE_ACTIVITY_TYPE_NUMBER = 2;
    private final int WORKSHOP_ACTIVITY_TYPE_NUMBER = 3;
    private final int ALL_ACTIVITIES_TYPES_NUMBER = 4;
    private final String ACTIVITIES_FILED = "activities";
    private final String TYPE_FILED = "type";

    @Inject
    TemplateProvider templateProvider;

    @Inject
    Validator validator;

    @Inject
    ActivitiesWebService activitiesWebService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String typeNumber = req.getParameter(TYPE_FILED);
        Map<String, Object> model = getModel(typeNumber);
        Template template = templateProvider.getTemplate(getServletContext(), templateProvider.ACTIVITIES_TEMPLATE);
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> getModel(String typeNumber) {
        Map<String, Object> model = new HashMap<>();
        if (!validator.validateInteger(typeNumber)) {
            model.put(ACTIVITIES_FILED, activitiesWebService.getAllActivities());
            model.put(TYPE_FILED, ALL_ACTIVITIES_TYPES_NUMBER);
            return model;
        }
        switch (Integer.parseInt(typeNumber)) {
            case 1: {
                putToModelActivitiesWithTypeNumber(model, LECTURE_ACTIVITY_TYPE_NUMBER);
                break;
            }
            case 2: {
                putToModelActivitiesWithTypeNumber(model, EXERCISE_ACTIVITY_TYPE_NUMBER);
                break;
            }
            case 3: {
                putToModelActivitiesWithTypeNumber(model, WORKSHOP_ACTIVITY_TYPE_NUMBER);
                break;
            }
            default: {
                putToModelActivitiesWithTypeNumber(model, ALL_ACTIVITIES_TYPES_NUMBER);
            }
        }
        return model;
    }

    private void putToModelActivitiesWithTypeNumber(Map<String, Object> model, int typeNumber) {
        if (typeNumber == ALL_ACTIVITIES_TYPES_NUMBER) {
            model.put(ACTIVITIES_FILED, activitiesWebService.
                    getAllActivities()
            );
        } else {
            model.put(ACTIVITIES_FILED, activitiesWebService.
                    getAllActivitiesByActivityType(ActivitiesType.getActivity(typeNumber))
            );
        }
        model.put(TYPE_FILED, typeNumber);
    }
}