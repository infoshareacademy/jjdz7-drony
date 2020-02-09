package com.korpodrony.servlet.admin;

import com.korpodrony.freemarker.TemplateProvider;
import com.korpodrony.reports.entity.ReportsStatisticsEntity;
import com.korpodrony.rest.ReportsStatisticsRestConsumerInterface;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("admin/admin-panel")
public class AdminPanelServlet extends HttpServlet {

    @Inject
    TemplateProvider templateProvider;

    @Inject
    ReportsStatisticsRestConsumerInterface reportsStatisticsRestConsumerInterface;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        Map<String, Object> model = new HashMap<>();
        LocalDate today = LocalDate.now();
        LocalDate monthBefore = today.minusMonths(1);
        List<ReportsStatisticsEntity> allReportsStatistics = reportsStatisticsRestConsumerInterface.getAllReportsStatistics();
        model.put("to", today.getDayOfMonth() + "/" + today.getMonthValue() + "/" + today.getYear());
        model.put("from", monthBefore.getDayOfMonth() + "/" + monthBefore.getMonthValue() + "/" + monthBefore.getYear());
        model.put("reports", allReportsStatistics);
        Template template = templateProvider.getTemplate(getServletContext(), templateProvider.ADMIN_PANEL);
        try {
            template.process(model, writer);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}