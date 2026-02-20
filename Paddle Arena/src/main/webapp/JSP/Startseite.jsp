<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.bean.Userbean" %>
<%
    Userbean userbean = (Userbean) session.getAttribute("userbean");
    boolean loggedIn = (userbean != null && userbean.isLoggedIn());
    String username = loggedIn ? userbean.getUser().getUsername() : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paddle Arena - Startseite</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4; }
        .navbar { background-color: #2c3e50; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }
        .navbar a { color: white; text-decoration: none; margin: 0 15px; font-size: 16px; }
        .navbar a:hover { text-decoration: underline; }
        .navbar .brand { font-size: 22px; font-weight: bold; color: #e74c3c; }
        .navbar .user-info { color: #ecf0f1; }
        .container { max-width: 900px; margin: 40px auto; padding: 20px; }
        .welcome { background: white; padding: 40px; border-radius: 8px; text-align: center; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }
        .welcome h1 { color: #2c3e50; }
        .welcome p { color: #7f8c8d; font-size: 18px; }
        .btn { display: inline-block; padding: 12px 30px; margin: 10px; border-radius: 5px; text-decoration: none; color: white; font-size: 16px; }
        .btn-primary { background-color: #3498db; }
        .btn-success { background-color: #27ae60; }
        .btn-warning { background-color: #e67e22; }
        .btn-danger { background-color: #e74c3c; }
        .btn:hover { opacity: 0.9; }
    </style>
</head>
<body>
    <div class="navbar">
        <span class="brand">Paddle Arena</span>
        <div>
            <a href="JSP/Startseite.jsp">Startseite</a>
            <% if (loggedIn) { %>
                <a href="BookingServlet">Buchungen</a>
                <a href="RatingServlet">Bewertungen</a>
            <% } %>
        </div>
        <div class="user-info">
            <% if (loggedIn) { %>
                Hallo, <%= username %> |
                <a href="LoginServlet?action=logout">Abmelden</a>
            <% } else { %>
                <a href="LoginServlet">Anmelden</a>
            <% } %>
        </div>
    </div>

    <div class="container">
        <div class="welcome">
            <h1>Willkommen bei Paddle Arena!</h1>
            <p>Buche deinen Padel-Platz und bewerte dein Spielerlebnis.</p>

            <% if (loggedIn) { %>
                <a href="BookingServlet" class="btn btn-primary">Platz buchen</a>
                <a href="RatingServlet" class="btn btn-success">Bewertungen ansehen</a>
            <% } else { %>
                <a href="LoginServlet" class="btn btn-primary">Anmelden</a>
                <a href="LoginServlet?action=register" class="btn btn-success">Registrieren</a>
            <% } %>
        </div>
    </div>
</body>
</html>
