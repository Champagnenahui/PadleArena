package de.hwg_lu.bwi520.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.hwg_lu.bwi520.bean.Ratingbean;
import de.hwg_lu.bwi520.bean.Userbean;

@WebServlet("/RatingServlet")
public class RatingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Ratingbean ratingbean = new Ratingbean();

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int ratingId = Integer.parseInt(request.getParameter("ratingId"));
            ratingbean.deleteRating(ratingId);
        }

        ratingbean.loadAllRatings();
        request.setAttribute("ratingbean", ratingbean);
        request.getRequestDispatcher("JSP/Rating.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        Userbean userbean = (session != null) ? (Userbean) session.getAttribute("userbean") : null;

        if (userbean == null || !userbean.isLoggedIn()) {
            response.sendRedirect("JSP/Login.jsp");
            return;
        }

        Ratingbean ratingbean = new Ratingbean();

        try {
            int ratingValue = Integer.parseInt(request.getParameter("rating"));
            String comment = request.getParameter("comment");

            boolean success = ratingbean.createRating(userbean.getUser(), ratingValue, comment);
            if (!success) {
                request.setAttribute("error", ratingbean.getErrorMessage());
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Ungueltige Bewertung.");
        }

        ratingbean.loadAllRatings();
        request.setAttribute("ratingbean", ratingbean);
        request.getRequestDispatcher("JSP/Rating.jsp").forward(request, response);
    }
}
