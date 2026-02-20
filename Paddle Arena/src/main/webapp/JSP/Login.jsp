<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Paddle Arena - Anmelden</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }
        .login-box { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); width: 350px; }
        .login-box h2 { text-align: center; color: #2c3e50; margin-bottom: 25px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; color: #555; font-weight: bold; }
        .form-group input { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; font-size: 14px; }
        .btn { width: 100%; padding: 12px; border: none; border-radius: 4px; color: white; font-size: 16px; cursor: pointer; margin-top: 10px; }
        .btn-primary { background-color: #3498db; }
        .btn:hover { opacity: 0.9; }
        .error { color: #e74c3c; text-align: center; margin-bottom: 15px; font-size: 14px; }
        .link { text-align: center; margin-top: 15px; }
        .link a { color: #3498db; text-decoration: none; }
    </style>
</head>
<body>
    <div class="login-box">
        <h2>Anmelden</h2>
        <% if (error != null) { %>
            <div class="error"><%= error %></div>
        <% } %>
        <form action="LoginServlet" method="post">
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
            <p><a href="JSP/Startseite.jsp">Zurueck zur Startseite</a></p>
        </div>
    </div>
</body>
</html>
