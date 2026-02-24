<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Padel Colosseum - Anmelden</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Login.css" />
</head>
<body>
    <div class="login-box">
        <h2>Anmelden</h2>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <input type="hidden" name="action" value="login">
            <div class="form-group">
                <label for="username">Benutzername</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Passwort</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit" class="btn btn-primary">Anmelden</button>
        </form>
        <div class="link">
            <p>Noch kein Konto? <a href="JSP/Register.jsp">Registrieren</a></p>
            <p><a href="Padle_Arena/JSP/Startseite.jsp">Zurueck zur Startseite</a></p>
        </div>
    </div>
</body>
</html>