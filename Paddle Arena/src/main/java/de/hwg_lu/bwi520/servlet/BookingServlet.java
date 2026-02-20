package de.hwg_lu.bwi520.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de.hwg_lu.bwi520.bean.Bookingbean;
import de.hwg_lu.bwi520.bean.Userbean;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Userbean userbean = (session != null) ? (Userbean) session.getAttribute("userbean") : null;

        if (userbean == null || !userbean.isLoggedIn()) {
            response.sendRedirect("JSP/Login.jsp");
            return;
        }

        Bookingbean bookingbean = new Bookingbean();

        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int bookingId = Integer.parseInt(request.getParameter("bookingId"));
            bookingbean.deleteBooking(bookingId);
        }

        bookingbean.loadAllBookings();
        bookingbean.loadAllCourts();

        request.setAttribute("bookingbean", bookingbean);
        request.getRequestDispatcher("JSP/Booking.jsp").forward(request, response);
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

        Bookingbean bookingbean = new Bookingbean();

        try {
            int courtId = Integer.parseInt(request.getParameter("courtId"));
            LocalDateTime start = LocalDateTime.parse(request.getParameter("start"), FORMATTER);
            LocalDateTime ende = LocalDateTime.parse(request.getParameter("ende"), FORMATTER);

            if (ende.isBefore(start) || ende.isEqual(start)) {
                request.setAttribute("error", "Endzeit muss nach Startzeit liegen.");
            } else {
                boolean success = bookingbean.createBooking(userbean.getUser(), courtId, start, ende);
                if (!success) {
                    request.setAttribute("error", bookingbean.getErrorMessage());
                }
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            request.setAttribute("error", "Ungueltige Eingabe: " + e.getMessage());
        }

        bookingbean.loadAllBookings();
        bookingbean.loadAllCourts();

        request.setAttribute("bookingbean", bookingbean);
        request.getRequestDispatcher("JSP/Booking.jsp").forward(request, response);
    }
}
