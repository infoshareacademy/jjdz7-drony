package com.korpodrony.servlet;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.services.AcitvitiesWebService;
import com.korpodrony.validation.NumberValidator;
import com.korpodrony.validation.StringValidator;
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

@WebServlet(urlPatterns = {"/activity", "/activity-unassign", "/activity-assign"})
public class ActivityServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    AcitvitiesWebService acitvitiesWebService;

    @Inject
    NumberValidator numberValidator;

    @Inject
    StringValidator stringValidator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        if (req.getServletPath().equals("/activity")) {
            String requestedId = req.getParameter("id");
            if (!numberValidator.validateInteger(requestedId)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int id = Integer.parseInt(requestedId);
            if (!acitvitiesWebService.hasActivity(id)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Map<String, Object> model = new HashMap<>();
            model.put("activity", acitvitiesWebService.getActivity(id));
            model.put("users", acitvitiesWebService.getAssignedUsers(id));
            model.put("availableUsers", acitvitiesWebService.getAvaiableUsers(id));
            Template template = templateProvider.getTemplate(getServletContext(), "activity.ftlh");
            try {
                template.process(model, writer);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String url = req.getServletPath();
        switch (url) {
            case "/activity-assign": {
                if (assignUserToActivity(req.getParameterMap())) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;
            }
            case "/activity-unassign": {
                if (unassignUserToActivity(req.getParameterMap())) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;
            }
            case "/activity":{
                if(editActivity(req.getParameterMap())){
                    resp.setStatus(HttpServletResponse.SC_OK);
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;
            }
            default: {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/activity")) {
            String requestedId = req.getParameter("id");
            if (!numberValidator.validateInteger(requestedId)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            int id = Integer.parseInt(requestedId);
            if (!acitvitiesWebService.hasActivity(id)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            if (acitvitiesWebService.deleteActivity(id)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean assignUserToActivity(Map<String, String[]> parameterMap) {
        if (getCountParametersArgs(parameterMap) == 2
                && validateAssignParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            int userId = Integer.parseInt(parameterMap.get("userid")[0]);
            return acitvitiesWebService.assignUserToActivity(userId, activityId);
        } else {
            return false;
        }
    }

    private long getCountParametersArgs(Map<String, String[]> parameterMap) {
        return parameterMap.values().stream().map(x -> x.length).filter(x -> x == 1).count();
    }

    private boolean validateAssignParameters(Map<String, String[]> parameterMap) {
        String activityId = parameterMap.get("id")[0];
        String activityUserId = parameterMap.get("userid")[0];
        return numberValidator.validateInteger(activityId) && numberValidator.validateInteger(activityUserId);
    }

    private boolean unassignUserToActivity(Map<String, String[]> parameterMap) {
        if (getCountParametersArgs(parameterMap) == 2
                && validateAssignParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            int userId = Integer.parseInt(parameterMap.get("userid")[0]);
            return acitvitiesWebService.unassignUserFromActivity(userId, activityId);
        } else {
            return false;
        }
    }

    private boolean editActivity(Map<String, String[]> parameterMap) {
        if (getCountParametersArgs(parameterMap) == 5
                && validateEditParameters(parameterMap)) {
            int activityId = Integer.parseInt(parameterMap.get("id")[0]);
            String name = parameterMap.get("name")[0];
            short maxUsers = Short.parseShort(parameterMap.get("maxusers")[0]);
            byte duration = Byte.parseByte(parameterMap.get("duration")[0]);
            int activityType = Integer.parseInt(parameterMap.get("activitytype")[0]);
            return acitvitiesWebService.editActivity(activityId, name,maxUsers, duration, activityType);
        } else {
            return false;
        }
    }

    private boolean validateEditParameters(Map<String, String[]> parameterMap) {
        String activityId = parameterMap.get("id")[0];
        String name = parameterMap.get("name")[0];
        String maxUsers = parameterMap.get("maxusers")[0];
        String duration = parameterMap.get("duration")[0];
        String activityType = parameterMap.get("activitytype")[0];
        return numberValidator.validateInteger(activityId)
                && stringValidator.validate(name)
                && numberValidator.validateShort(maxUsers)
                && numberValidator.validateByte(duration)
                && numberValidator.validateAcitvityTypeInteger(activityType);
    }
}
