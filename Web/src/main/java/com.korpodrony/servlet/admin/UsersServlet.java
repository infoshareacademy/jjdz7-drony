package com.korpodrony.servlet.admin;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.services.UsersWebService;
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

@WebServlet("admin/users")
public class UsersServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    UsersWebService usersWebService;

    @Inject
    Validator validator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("users", usersWebService.getAllUserEntities());
        Template template = templateProvider.getTemplate(getServletContext(), templateProvider.USERS_TEMPLATE);
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String sessionUserId = req.getSession().getAttribute("userId").toString();
        if (req.getParameter("id").equals(sessionUserId)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        if (validateParameters(req, resp)) {
            int id = Integer.parseInt(req.getParameter("id"));
            int value = Integer.parseInt(req.getParameter("select"));
            usersWebService.updatePermissionLevel(id, value);
            return;
        }
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    private boolean validateParameters(HttpServletRequest req, HttpServletResponse resp) {
        if (!validateNumber(req, "id") || !validateNumber(req, "select")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        int value = Integer.parseInt(req.getParameter("select"));
        if (value != 0 && value != 1) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return false;
        }
        return true;
    }

    private boolean validateNumber(HttpServletRequest req, String attribute) {
        return validator.validateInteger(req.getParameter(attribute));
    }
}