package com.korpodrony.servlet;

import com.korpodrony.freemarker.TemplateProvider;
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

@WebServlet("/activities")
public class ActivitiesServlet extends HttpServlet {

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
        String typeNumber = req.getParameter("type");
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
            model.put("activities", activitiesWebService.getAllActivities());
            model.put("type", 4);
            return model;
        }
        switch (Integer.parseInt(typeNumber)) {
            case 1: {
                putToModelActivitiesWithTypeNumber(model, 1);
                break;
            }
            case 2: {
                putToModelActivitiesWithTypeNumber(model, 2);
                break;
            }
            case 3: {
                putToModelActivitiesWithTypeNumber(model, 3);
                break;
            }
            default: {
                putToModelActivitiesWithTypeNumber(model, 4);
            }
        }
        return model;
    }

    private void putToModelActivitiesWithTypeNumber(Map<String, Object> model, int typeNumber) {
        if (typeNumber == 4) {
            model.put("activities", activitiesWebService.
                    getAllActivities()
            );
        } else {
            model.put("activities", activitiesWebService.
                    getAllActivities(x -> x.getActivitiesType().getNumber() == typeNumber)
            );
        }
        model.put("type", typeNumber);
    }
}