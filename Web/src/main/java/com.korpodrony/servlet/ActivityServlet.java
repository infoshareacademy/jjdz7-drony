package com.korpodrony.servlet;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.services.AcitvitiesWebService;
import com.korpodrony.validation.NumberValidator;
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

@WebServlet("/activity")
public class ActivityServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    AcitvitiesWebService acitvitiesWebService;

    @Inject
    NumberValidator numberValidator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String requestedId = req.getParameter("id");
        if (!numberValidator.validateInteger(requestedId)){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int id = Integer.parseInt(requestedId);
        if (!acitvitiesWebService.hasActivity(id)){
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
    }
}
