<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Impressum - Padel Colosseum</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Startseite.css" />
    <style>
        .impressum-box {
            background: white;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            max-width: 500px;
            margin: 60px auto;
            text-align: center;
        }
        .impressum-box h2 {
            color: #2c3e50;
            margin-bottom: 25px;
        }
        .impressum-box p {
            color: #7f8c8d;
            font-size: 18px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <span class="brand">Padel Colosseum</span>
        <div>
            <a href="/Padle_Arena/JSP/Startseite.jsp">Startseite</a>
            <a href="${pageContext.request.contextPath}/RatingServlet">Bewertungen</a>
            <a href="${pageContext.request.contextPath}/BookingServlet">Buchungen</a>
            <a href="${pageContext.request.contextPath}/JSP/Impressum.jsp">Impressum</a>
        </div>
    </div>
    <div class="impressum-box">
        <h2>Impressum</h2>
        <p>Adresse: Ernst-Boehe-Straße, Ludwigshafen</p>
        <p>Padel Colosseum 24/7 GmbH</p>
    </div>
</body>
</html>