package de.hwg_lu.bwi520.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.hwg_lu.bwi520.bean.Userbean;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect("JSP/Startseite.jsp");
        } else {
            request.getRequestDispatcher("JSP/Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Userbean userbean = new Userbean();

        if ("register".equals(action)) {
            if (userbean.register(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("userbean", userbean);
                response.sendRedirect("JSP/Startseite.jsp");
            } else {
                request.setAttribute("error", userbean.getErrorMessage());
                request.getRequestDispatcher("JSP/Register.jsp").forward(request, response);
            }
        } else {
            // Login
            if (userbean.login(username, password)) {
                HttpSession session = request.getSession();
                session.setAttribute("userbean", userbean);
                response.sendRedirect("JSP/Startseite.jsp");
            } else {
                request.setAttribute("error", userbean.getErrorMessage());
                request.getRequestDispatcher("JSP/Login.jsp").forward(request, response);
            }
        }
    }
}
