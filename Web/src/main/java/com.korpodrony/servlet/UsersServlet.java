package com.korpodrony.servlet;

import com.korpodrony.dao.OrganizationRepositoryDao;
import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.services.UsersWebService;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.ejb.EJB;
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

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @EJB
    OrganizationRepositoryDao dao;

    @Inject
    UsersWebService usersWebService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("users", usersWebService.getAllUsers());
        Template template = templateProvider.getTemplate(getServletContext(), templateProvider.USERS_TEMPLATE);
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}