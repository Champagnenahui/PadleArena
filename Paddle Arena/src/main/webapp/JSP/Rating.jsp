<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.bean.Userbean" %>
<%@ page import="de.hwg_lu.bwi520.bean.Ratingbean" %>
<%@ page import="de.hwg_lu.bwi520.modell.Rating" %>
<%@ page import="java.util.List" %>
<%
    Userbean userbean = (Userbean) session.getAttribute("userbean");
    boolean loggedIn = (userbean != null && userbean.isLoggedIn());
    Ratingbean ratingbean = (Ratingbean) request.getAttribute("ratingbean");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paddle Arena - Bewertungen</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .navbar { background-color: #2c3e50; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin: 0 15px; }
        .navbar .brand { font-size: 22px; font-weight: bold; color: #e74c3c; }
        .container { max-width: 900px; margin: 30px auto; padding: 20px; }
        .card { background: white; padding: 25px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .card h2 { color: #2c3e50; margin-top: 0; }
        .avg-rating { font-size: 24px; color: #e67e22; font-weight: bold; text-align: center; margin: 10px 0; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .form-group textarea { height: 80px; resize: vertical; }
        .btn { padding: 10px 25px; border: none; border-radius: 4px; color: white; cursor: pointer; text-decoration: none; font-size: 14px; }
        .btn-success { background-color: #27ae60; }
        .btn-danger { background-color: #e74c3c; }
        .btn:hover { opacity: 0.9; }
        .rating-item { border-bottom: 1px solid #eee; padding: 15px 0; }
        .rating-item:last-child { border-bottom: none; }
        .rating-stars { color: #f39c12; font-size: 18px; }
        .rating-user { font-weight: bold; color: #2c3e50; }
        .rating-comment { color: #555; margin-top: 5px; }
        .error { color: #e74c3c; margin-bottom: 15px; padding: 10px; background: #ffeaea; border-radius: 4px; }
    </style>
</head>
<body>
    <div class="navbar">
        <span class="brand">Paddle Arena</span>
        <div>
            <a href="JSP/Startseite.jsp">Startseite</a>
            <% if (loggedIn) { %>
                <a href="BookingServlet">Buchungen</a>
            <% } %>
            <a href="RatingServlet">Bewertungen</a>
        </div>
        <div>
            <% if (loggedIn) { %>
                <span style="color:white">Hallo, <%= userbean.getUser().getUsername() %></span> |
                <a href="LoginServlet?action=logout" style="color:white">Abmelden</a>
            <% } else { %>
                <a href="LoginServlet" style="color:white">Anmelden</a>
            <% } %>
        </div>
    </div>

    <div class="container">
        <% if (loggedIn) { %>
        <div class="card">
            <h2>Neue Bewertung abgeben</h2>
            <% if (error != null) { %>
                <div class="error"><%= error %></div>
            <% } %>
            <form action="RatingServlet" method="post">
                <div class="form-group">
                    <label for="rating">Bewertung (1-5 Sterne)</label>
                    <select id="rating" name="rating" required>
                        <option value="">-- Bewertung waehlen --</option>
                        <option value="5">5 Sterne</option>
                        <option value="4">4 Sterne</option>
                        <option value="3">3 Sterne</option>
                        <option value="2">2 Sterne</option>
                        <option value="1">1 Stern</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="comment">Kommentar</label>
                    <textarea id="comment" name="comment" placeholder="Dein Kommentar..."></textarea>
                </div>
                <button type="submit" class="btn btn-success">Bewertung abgeben</button>
            </form>
        </div>
        <% } %>

        <div class="card">
            <h2>Alle Bewertungen</h2>
            <% if (ratingbean != null && ratingbean.getAverageRating() > 0) { %>
                <div class="avg-rating">
                    Durchschnitt: <%= String.format("%.1f", ratingbean.getAverageRating()) %> / 5.0
                </div>
            <% } %>

            <% if (ratingbean != null && !ratingbean.getRatings().isEmpty()) {
                for (Rating r : ratingbean.getRatings()) { %>
                    <div class="rating-item">
                        <span class="rating-user"><%= r.getUser().getUsername() %></span>
                        <span class="rating-stars">
                            <% for (int i = 0; i < r.getRating(); i++) { %>&#9733;<% } %>
                            <% for (int i = r.getRating(); i < 5; i++) { %>&#9734;<% } %>
                        </span>
                        <div class="rating-comment"><%= r.getComment() != null ? r.getComment() : "" %></div>
                        <% if (loggedIn && r.getUser().getUsername().equals(userbean.getUser().getUsername())) { %>
                            <a href="RatingServlet?action=delete&ratingId=<%= r.getRatingId() %>" class="btn btn-danger" style="margin-top:5px; display:inline-block; font-size:12px; padding:5px 10px;">Loeschen</a>
                        <% } %>
                    </div>
            <%  }
            } else { %>
                <p>Noch keine Bewertungen vorhanden.</p>
            <% } %>
        </div>
    </div>
</body>
</html>
