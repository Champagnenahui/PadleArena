<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.bean.Userbean" %>
<%@ page import="de.hwg_lu.bwi520.bean.Bookingbean" %>
<%@ page import="de.hwg_lu.bwi520.modell.Booking" %>
<%@ page import="de.hwg_lu.bwi520.modell.Courts" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    Userbean userbean = (Userbean) session.getAttribute("userbean");
    if (userbean == null || !userbean.isLoggedIn()) {
        response.sendRedirect("Login.jsp");
        return;
    }
    Bookingbean bookingbean = (Bookingbean) request.getAttribute("bookingbean");
    String error = (String) request.getAttribute("error");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paddle Arena - Buchungen</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .navbar { background-color: #2c3e50; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin: 0 15px; }
        .navbar .brand { font-size: 22px; font-weight: bold; color: #e74c3c; }
        .container { max-width: 900px; margin: 30px auto; padding: 20px; }
        .card { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .card h2 { color: #2c3e50; margin-top: 0; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        .form-group input, .form-group select { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { padding: 10px 25px; border: none; border-radius: 4px; color: white; cursor: pointer; text-decoration: none; font-size: 14px; }
        .btn-primary { background-color: #3498db; }
        .btn-danger { background-color: #e74c3c; }
        .btn:hover { opacity: 0.9; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #2c3e50; color: white; }
        tr:hover { background-color: #f5f5f5; }
        .error { color: #e74c3c; margin-bottom: 15px; padding: 10px; background: #ffeaea; border-radius: 4px; }
    </style>
</head>
<body>
    <div class="navbar">
        <span class="brand">Paddle Arena</span>
        <div>
            <a href="JSP/Startseite.jsp">Startseite</a>
            <a href="BookingServlet">Buchungen</a>
            <a href="RatingServlet">Bewertungen</a>
        </div>
        <div>
            <span style="color:white">Hallo, <%= userbean.getUser().getUsername() %></span> |
            <a href="LoginServlet?action=logout" style="color:white">Abmelden</a>
        </div>
    </div>

    <div class="container">
        <div class="card">
            <h2>Neue Buchung</h2>
            <% if (error != null) { %>
                <div class="error"><%= error %></div>
            <% } %>
            <form action="BookingServlet" method="post">
                <div class="form-group">
                    <label for="courtId">Platz</label>
                    <select id="courtId" name="courtId" required>
                        <option value="">-- Platz waehlen --</option>
                        <% if (bookingbean != null) {
                            for (Courts court : bookingbean.getCourts()) { %>
                                <option value="<%= court.getCourtId() %>"><%= court.getName() %></option>
                        <%  }
                        } %>
                    </select>
                </div>
                <div class="form-group">
                    <label for="start">Startzeit</label>
                    <input type="datetime-local" id="start" name="start" required>
                </div>
                <div class="form-group">
                    <label for="ende">Endzeit</label>
                    <input type="datetime-local" id="ende" name="ende" required>
                </div>
                <button type="submit" class="btn btn-primary">Buchen</button>
            </form>
        </div>

        <div class="card">
            <h2>Alle Buchungen</h2>
            <% if (bookingbean != null && !bookingbean.getBookings().isEmpty()) { %>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Benutzer</th>
                        <th>Platz</th>
                        <th>Start</th>
                        <th>Ende</th>
                        <th>Aktion</th>
                    </tr>
                    <% for (Booking b : bookingbean.getBookings()) { %>
                        <tr>
                            <td><%= b.getBookingId() %></td>
                            <td><%= b.getUser().getUsername() %></td>
                            <td><%= b.getCourt().getName() %></td>
                            <td><%= b.getStart().format(fmt) %></td>
                            <td><%= b.getEnde().format(fmt) %></td>
                            <td>
                                <% if (b.getUser().getUsername().equals(userbean.getUser().getUsername())) { %>
                                    <a href="BookingServlet?action=delete&bookingId=<%= b.getBookingId() %>" class="btn btn-danger">Loeschen</a>
                                <% } %>
                            </td>
                        </tr>
                    <% } %>
                </table>
            <% } else { %>
                <p>Keine Buchungen vorhanden.</p>
            <% } %>
        </div>
    </div>
</body>
</html>
