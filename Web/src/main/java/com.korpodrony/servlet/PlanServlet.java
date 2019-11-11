package com.korpodrony.servlet;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.service.RepositoryService;
import com.korpodrony.services.PlansWebService;
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

@WebServlet(urlPatterns = {"/plan", "/plan-unassign", "/plan-assign", "/plan-add"})
public class PlanServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    PlansWebService plansWebService;

    @Inject
    RepositoryService repositoryService;

    @Inject
    Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String url = req.getServletPath();
        switch (url) {
            case "/plan": {
                String requestedId = req.getParameter("id");
                if (setRespStatusOnValidationFailure(resp, validator.validateInteger(requestedId))) {
                    break;
                }
                int id = Integer.parseInt(requestedId);
                if (setRespStatusOnValidationFailure(resp, plansWebService.hasPlan(id))) {
                    break;
                }
                Map<String, Object> model = getPlanModel(id);
                proccesTemplate(writer, model, templateProvider.PLAN_TEMPLATE);
                break;
            }
            case "/plan-add": {
                proccesTemplate(writer, null, templateProvider.ADD_PLAN_TEMPLATE);
                break;
            }
            default: {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String url = req.getServletPath();
        switch (url) {
            case "/plan-assign": {
                if (setRespStatusOnValidationFailure(resp, assignActivityToPlan(req.getParameterMap()))) {
                    break;
                }
                saveStatus(resp);
                break;
            }
            case "/plan-unassign": {
                if (setRespStatusOnValidationFailure(resp, unassignActivityFromPlan(req.getParameterMap()))) {
                    break;
                }
                saveStatus(resp);
                break;
            }
            case "/plan": {
                if (setRespStatusOnValidationFailure(resp, editPlan(req.getParameterMap()))) {
                    break;
                }
                saveStatus(resp);
                break;
            }
            default: {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getServletPath().equals("/plan")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String requestedId = req.getParameter("id");
        if (setRespStatusOnValidationFailure(resp, requestedId)) {
            return;
        }
        int id = Integer.parseInt(requestedId);
        if (setRespStatusOnValidationFailure(resp, plansWebService.hasPlan(id))) {
            return;
        }
        if (plansWebService.deleteActivity(id)) {
            saveStatus(resp);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/activity")) {
            if (setRespStatusOnValidationFailure(resp, createActivity(req.getParameterMap()))) {
                return;
            }
            saveStatus(resp);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private boolean setRespStatusOnValidationFailure(HttpServletResponse resp, boolean statement) {
        if (!statement) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }
        return false;
    }

    private boolean setRespStatusOnValidationFailure(HttpServletResponse resp, String requestedId) {
        if (!validator.validateInteger(requestedId)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return true;
        }
        return false;
    }

    private void saveStatus(HttpServletResponse resp) {
        repositoryService.writeRepositoryToFile();
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private Map<String, Object> getPlanModel(int id) {
        Map<String, Object> model = new HashMap<>();
        model.put("plan", plansWebService.getPlan(id));
        model.put("activities", plansWebService.getAssignedActivities(id));
        model.put("avaiableActivities", plansWebService.getAvaiableActivities(id));
        return model;
    }

    private void proccesTemplate(PrintWriter writer, Map<String, Object> model, String path) throws IOException {
        Template template = templateProvider.getTemplate(getServletContext(), path);
        try {
            template.process(model, writer);
            return;
        } catch (TemplateException e) {
            return;
        }
    }

    private boolean assignActivityToPlan(Map<String, String[]> parameterMap) {
        if (checkAssignParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            int userId = Integer.parseInt(parameterMap.get("activityid")[0]);
            return plansWebService.assignActivityToPlan(userId, activityId);
        } else {
            return false;
        }
    }

    private boolean checkAssignParameters(Map<String, String[]> parameterMap) {
        return checkParameters(parameterMap, 2, validateAssignParameters(parameterMap));
    }

    private boolean checkParameters(Map<String, String[]> parameterMap, int number, boolean statement) {
        return checkNumberOfParameters(number, countParameters(parameterMap)) && statement;
    }

    private boolean checkNumberOfParameters(int number, long l) {
        return l == number;
    }

    private long countParameters(Map<String, String[]> parameterMap) {
        return parameterMap.values()
                .stream()
                .map(x -> x.length)
                .filter(x -> checkNumberOfParameters(1, x))
                .count();
    }

    private boolean validateAssignParameters(Map<String, String[]> parameterMap) {
        String activityId = parameterMap.get("id")[0];
        String activityUserId = parameterMap.get("activityid")[0];
        return validator.validateInteger(activityId) && validator.validateInteger(activityUserId);
    }

    private boolean unassignActivityFromPlan(Map<String, String[]> parameterMap) {
        if (checkUnassignParmeters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            int userId = Integer.parseInt(parameterMap.get("activityid")[0]);
            return plansWebService.unassignActivityFromPlan(userId, activityId);
        } else {
            return false;
        }
    }

    private boolean checkUnassignParmeters(Map<String, String[]> parameterMap) {
        return checkParameters(parameterMap, 2, validateAssignParameters(parameterMap));
    }

    private boolean editPlan(Map<String, String[]> parameterMap) {
        if (checkEditParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            String name = parameterMap.get("name")[0].trim();
            return plansWebService.editPlan(activityId, name);
        } else {
            return false;
        }
    }

    private boolean checkEditParameters(Map<String, String[]> parameterMap) {
        return checkParameters(parameterMap, 2, validateEditParameters(parameterMap));
    }

    private boolean validateEditParameters(Map<String, String[]> parameterMap) {
        String activityId = parameterMap.get("id")[0];
        String name = parameterMap.get("name")[0];
        return validator.validateInteger(activityId)
                && validator.validateString(name);
    }

    private boolean createActivity(Map<String, String[]> parameterMap) {
        if (checkParameters(parameterMap, 4, validateCreateParameters(parameterMap))) {
            String name = parameterMap.get("name")[0].trim();
            return plansWebService.createPlan(name);
        }
        return false;
    }

    private boolean validateCreateParameters(Map<String, String[]> parameterMap) {
        String name = parameterMap.get("name")[0];
        String maxUsers = parameterMap.get("maxusers")[0];
        String duration = parameterMap.get("duration")[0];
        String activityType = parameterMap.get("activitytype")[0];
        return validator.validateString(name)
                && validator.validateShort(maxUsers)
                && validator.validateByte(duration)
                && validator.validateActivityTypeInteger(activityType);
    }
}