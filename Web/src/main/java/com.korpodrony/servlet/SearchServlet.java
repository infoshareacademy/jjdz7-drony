package com.korpodrony.servlet;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.services.SearchWebService;
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

@WebServlet(urlPatterns = {"/search", "/search-users", "/search-activities", "/search-plans"})
public class SearchServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    Validator validator;

    @Inject
    SearchWebService searchWebService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        String url = req.getServletPath();
        switch (url) {
            case "/search": {
                Template template = templateProvider.getTemplate(getServletContext(), "search.ftlh");
                try {
                    template.process(null, writer);
                    break;
                } catch (TemplateException e) {
                }
            }
            case "/search-users": {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String name = req.getParameter("name").toLowerCase();
                writer.print(searchWebService.getUsersByName(name));
                writer.flush();
                break;
            }
            case "/search-activities": {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String name = req.getParameter("name").toLowerCase();
                String result = searchWebService.getActivitiesByName(name);
                writer.print(result);
                writer.flush();
                break;
            }
            case "/search-plans": {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                String name = req.getParameter("name").toLowerCase();
                writer.print(searchWebService.getPlansByName(name));
                writer.flush();
                break;
            }
            default: {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
