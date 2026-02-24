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
    <title>Padel Colosseum - Bewertungen</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Rating.css" />
</head>
<body>
    <div class="navbar">
        <span class="brand">Padel Colosseum</span>
        <div>
            <a href="/Padle_Arena/JSP/Startseite.jsp">Startseite</a>
            <% if (loggedIn) { %>
                <a href="${pageContext.request.contextPath}/BookingServlet">Buchungen</a>
            <% } %>
            <a href="${pageContext.request.contextPath}/RatingServlet">Bewertungen</a>
        </div>
        <div>
            <% if (loggedIn) { %>
                <span style="color:white">Hallo, <%= userbean.getUser().getUsername() %></span> |
                <a href="${pageContext.request.contextPath}/LoginServlet?action=logout" style="color:white">Abmelden</a>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/LoginServlet" style="color:white">Anmelden</a>
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
            <form action="${pageContext.request.contextPath}/RatingServlet" method="post">
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
                            <a href="${pageContext.request.contextPath}/RatingServlet?action=delete&ratingId=<%= r.getRatingId() %>" class="btn btn-danger" style="margin-top:5px; display:inline-block; font-size:12px; padding:5px 10px;">Loeschen</a>
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