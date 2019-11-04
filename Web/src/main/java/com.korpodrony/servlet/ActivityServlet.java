package com.korpodrony.servlet;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.service.RepositoryService;
import com.korpodrony.services.AcitvitiesWebService;
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

@WebServlet(urlPatterns = {"/activity", "/activity-unassign", "/activity-assign", "/activity-add"})
public class ActivityServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    AcitvitiesWebService acitvitiesWebService;

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
            case "/activity": {
                String requestedId = req.getParameter("id");
                if (!validator.validateInteger(requestedId)) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
                int id = Integer.parseInt(requestedId);
                if (!acitvitiesWebService.hasActivity(id)) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
                Map<String, Object> model = getActivityModel(id);
                proccesTemplate(writer, model, templateProvider.ACTIVITY);
                break;
            }
            case "/activity-add": {
                proccesTemplate(writer, null, templateProvider.ADD_ACTIVITY);
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
            case "/activity-assign": {
                if (!assignUserToActivity(req.getParameterMap())) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
                repositoryService.writeRepositoryToFile();
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            }
            case "/activity-unassign": {
                if (!unassignUserToActivity(req.getParameterMap())) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
                repositoryService.writeRepositoryToFile();
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            }
            case "/activity": {
                if (!editActivity(req.getParameterMap())) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    break;
                }
                repositoryService.writeRepositoryToFile();
                resp.setStatus(HttpServletResponse.SC_OK);
                break;
            }
            default: {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getServletPath().equals("/activity")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String requestedId = req.getParameter("id");
        if (!validator.validateInteger(requestedId)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int id = Integer.parseInt(requestedId);
        if (!acitvitiesWebService.hasActivity(id)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (acitvitiesWebService.deleteActivity(id)) {
            repositoryService.writeRepositoryToFile();
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/activity")) {
            if (createActivity(req.getParameterMap())) {
                repositoryService.writeRepositoryToFile();
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private Map<String, Object> getActivityModel(int id) {
        Map<String, Object> model = new HashMap<>();
        model.put("activity", acitvitiesWebService.getActivity(id));
        model.put("users", acitvitiesWebService.getAssignedUsers(id));
        model.put("availableUsers", acitvitiesWebService.getAvaiableUsers(id));
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

    private boolean assignUserToActivity(Map<String, String[]> parameterMap) {
        if (checkAssignParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            int userId = Integer.parseInt(parameterMap.get("userid")[0]);
            return acitvitiesWebService.assignUserToActivity(userId, activityId);
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
        return parameterMap.values().stream().map(x -> x.length).filter(x -> checkNumberOfParameters(1, x)).count();
    }

    private boolean validateAssignParameters(Map<String, String[]> parameterMap) {
        String activityId = parameterMap.get("id")[0];
        String activityUserId = parameterMap.get("userid")[0];
        return validator.validateInteger(activityId) && validator.validateInteger(activityUserId);
    }

    private boolean unassignUserToActivity(Map<String, String[]> parameterMap) {
        if (checkUnassignParmeters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            int userId = Integer.parseInt(parameterMap.get("userid")[0]);
            return acitvitiesWebService.unassignUserFromActivity(userId, activityId);
        } else {
            return false;
        }
    }

    private boolean checkUnassignParmeters(Map<String, String[]> parameterMap) {
        return checkParameters(parameterMap, 2, validateAssignParameters(parameterMap));
    }

    private boolean editActivity(Map<String, String[]> parameterMap) {
        if (checkEditParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            String name = parameterMap.get("name")[0].trim();
            short maxUsers = Short.parseShort(parameterMap.get("maxusers")[0]);
            byte duration = Byte.parseByte(parameterMap.get("duration")[0]);
            int activityType = Integer.parseInt(parameterMap.get("activitytype")[0]);
            return acitvitiesWebService.editActivity(activityId, name, maxUsers, duration, activityType);
        } else {
            return false;
        }
    }

    private boolean checkEditParameters(Map<String, String[]> parameterMap) {
        return checkParameters(parameterMap, 5, validateEditParameters(parameterMap));
    }

    private boolean validateEditParameters(Map<String, String[]> parameterMap) {
        String activityId = parameterMap.get("id")[0];
        String name = parameterMap.get("name")[0];
        String maxUsers = parameterMap.get("maxusers")[0];
        String duration = parameterMap.get("duration")[0];
        String activityType = parameterMap.get("activitytype")[0];
        return validator.validateInteger(activityId)
                && validator.validateString(name)
                && validator.validateShort(maxUsers)
                && validator.validateByte(duration)
                && validator.validateAcitvityTypeInteger(activityType);
    }

    private boolean createActivity(Map<String, String[]> parameterMap) {
        if (checkParameters(parameterMap, 4, validateCreateParameters(parameterMap))) {
            String name = parameterMap.get("name")[0].trim();
            short maxUsers = Short.parseShort(parameterMap.get("maxusers")[0]);
            byte duration = Byte.parseByte(parameterMap.get("duration")[0]);
            int activityType = Integer.parseInt(parameterMap.get("activitytype")[0]);
            return acitvitiesWebService.createActivity(name, maxUsers, duration, activityType);
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
                && validator.validateAcitvityTypeInteger(activityType);
    }
}