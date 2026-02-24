<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Padel Colosseum - Registrieren</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Register.css" />
</head>
<body>
    <div class="login-box">
        <h2>Registrieren</h2>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <input type="hidden" name="action" value="register">
            <div class="form-group">
                <label for="username">Benutzername</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Passwort</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="passwordConfirm">Passwort bestätigen</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" required>
            </div>
            <button type="submit" class="btn btn-success">Registrieren</button>
        </form>
        <div class="link">
            <p>Bereits ein Konto? <a href="Login.jsp">Anmelden</a></p>
            <p><a href="Startseite.jsp">Zurueck zur Startseite</a></p>
        </div>
    </div>
</body>
</html>