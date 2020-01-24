package com.korpodrony.servlet.admin;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.services.UsersWebService;
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

@WebServlet("admin/admin-panel")
public class AdminPanelServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    UsersWebService usersWebService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Template template = templateProvider.getTemplate(getServletContext(), templateProvider.ADMIN_PANEL);
        try {
            template.process(null, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}