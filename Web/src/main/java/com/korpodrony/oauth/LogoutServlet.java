package com.korpodrony.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(LogoutServlet.class.getName());

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    resp.setCharacterEncoding("UTF-8");

    HttpSession session = req.getSession();
    String userEmail = (String) session.getAttribute("email");
    logger.info("User " + userEmail + " log out successfully");
    session.invalidate();
    try {
      resp.sendRedirect("/home");
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }
}