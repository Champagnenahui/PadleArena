<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.bean.Userbean" %>
<%@ page import="de.hwg_lu.bwi520.bean.Ratingbean" %>
<%
    Userbean userbean = (Userbean) session.getAttribute("userbean");
    boolean loggedIn = (userbean != null && userbean.isLoggedIn());
    String username = loggedIn ? userbean.getUser().getUsername() : "";
    
    // Load average rating
    Ratingbean ratingbean = new Ratingbean();
    ratingbean.loadAllRatings();
    double averageRating = ratingbean.getAverageRating();
    int ratingCount = ratingbean.getRatings().size();
%>
<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Padel Colosseum - Startseite</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/Startseite.css" />
    <style>
        body { margin: 0; padding: 0; }
        .hero-section {
            min-height: 60vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            background: linear-gradient(rgba(30,30,30,0.7),rgba(30,30,30,0.7)), url('${pageContext.request.contextPath}/img/view.png') center/cover no-repeat;
            color: #fff;
            text-align: center;
        }
        .hero-section h1 { font-size: 3rem; font-weight: bold; }
        .hero-section p { font-size: 1.5rem; margin-bottom: 2rem; }
        .hero-section .btn { margin: 0 10px; }
        .about-section {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            padding: 60px 0;
            background: #f8f9fa;
        }
        .about-section .about-text {
            flex: 1 1 300px;
            padding: 30px;
        }
        .about-section .about-img {
            flex: 1 1 300px;
            display: flex;
            justify-content: center;
        }
        .about-section img {
            max-width: 350px;
            border-radius: 12px;
            box-shadow: 0 2px 12px rgba(0,0,0,0.15);
        }
        .courts-section {
            padding: 60px 0;
            background: #fff;
        }
        .courts-section h2 { text-align: center; margin-bottom: 40px; }
        .courts-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 30px;
            max-width: 1100px;
            margin: 0 auto;
        }
        .court-card {
            background: #f4f4f4;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.07);
            padding: 30px 20px;
            text-align: center;
        }
        .court-card h3 { margin-bottom: 10px; }
        .court-card p { color: #555; }
       
  
        html { scroll-behavior: smooth; }
        footer a:hover {
            color: #ffc107 !important;
            transition: color 0.3s ease;
        }
        @media (max-width: 900px) {
            .about-section { flex-direction: column; }
            .about-section .about-img { margin-top: 30px; }
        }
    </style>
</head>
<body>
    <header>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/JSP/Startseite.jsp">
                    <img src="${pageContext.request.contextPath}/img/logo.png" alt="Padel Colosseum Logo" style="height:100px; vertical-align:middle;">
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link" href="#hero">Startseite</a></li>
                        <li class="nav-item"><a class="nav-link" href="#about">Über uns</a></li>
                        <li class="nav-item"><a class="nav-link" href="#ratings">Bewertungen</a></li>
                        <li class="nav-item"><a class="nav-link" href="#courts">Courts</a></li>
                        <li class="nav-item"><a class="nav-link" href="#pricing">Preise</a></li>
                        <% if (loggedIn) { %>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/BookingServlet">Buchungen</a></li>
                        <% } %>
                        
                        <% if (loggedIn) { %>
                        <li class="nav-item"><span class="nav-link">Hallo, <%= username %></span></li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/LoginServlet?action=logout">Abmelden</a></li>
                        <% } else { %>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/LoginServlet">Anmelden</a></li>
                        <% } %>
                    </ul>
                </div>
            </div>
        </nav>
    </header>

    <section class="hero-section" id="hero">
        <h1>Willkommen beim Padel Colosseum</h1>
        <p>Padel Courts jetzt anmelden und buchen.</p>
        <div>
            <% if (loggedIn) { %>
                <h2 style="color: #fff; margin-bottom: 30px;">Hallo <%= username %>!</h2>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/LoginServlet" class="btn btn-primary btn-lg">Login</a>
                
            <% } %>
        </div>
    </section>

    <section class="about-section" id="about">
        <div class="about-text">
            <h2>24/7 geöffnet – Dein Spiel. Deine Zeit.</h2>
            <p>Im Padel Colosseum bestimmst du, wann gespielt wird. Wir sind 24 Stunden am Tag, 7 Tage die Woche für dich geöffnet – ohne Kompromisse. Frühtraining vor der Arbeit, Night Sessions mit deinem Team oder spontane Matches am Wochenende? Jederzeit möglich.
				Unsere modernen Courts, das einfache Online-Buchungssystem und der flexible Zugang geben dir volle Freiheit. Keine festen Zeiten. Keine Ausreden. Nur Padel.
				Das Spiel schläft nicht – und wir auch nicht.</p>
            <p>High-End-Courts, smarter 24/7-Zugang und pure Arena-Atmosphäre. Bei uns zählt nur eins: spielen, kämpfen, gewinnen – jederzeit.</p>
        </div>
        <div class="about-img">
            <img src="${pageContext.request.contextPath}/img/outdoor.png" alt="Padel Colosseum" />
        </div>
    </section>

    

    <section class="courts-section" id="courts">
        <h2>Unsere Courts</h2>
        <div class="courts-grid">
            <div class="court-card">
                <h3>Outdoor Court</h3>
                <p>Genießen Sie das Spiel unter freiem Himmel auf unserem modernen Outdoor-Court.</p>
            </div>
            <div class="court-card">
                <h3>Indoor Court</h3>
                <p>Spielen Sie unabhängig vom Wetter auf unserem professionellen Indoor-Court.</p>
            </div>

            <div class="court-card">
                <h3>Kids Court</h3>
                <p>Speziell für Kinder und Familien – sicher, bunt und spaßig!</p>
            </div>
        </div>
    </section>

    <section class="pricing-section" id="pricing" style="padding: 60px 0; background: #f8f9fa;">
        <div class="container">
            <h2 style="text-align: center; margin-bottom: 40px;">Preise</h2>
            <div style="max-width: 600px; margin: 0 auto; background: #fff; border-radius: 12px; box-shadow: 0 2px 12px rgba(0,0,0,0.15); padding: 40px; text-align: center;">
                <div style="font-size: 3rem; font-weight: bold; color: #0d6efd; margin-bottom: 20px;">30€ <span style="font-size: 1.5rem; color: #555;">/ Stunde</span></div>
                <p style="font-size: 1.1rem; color: #333; margin-bottom: 15px;">Professionelle Courts zu fairen Preisen</p>
                <p style="font-size: 0.95rem; color: #666; margin-bottom: 30px;">Maximale Buchungszeit: 2 Stunden</p>
                <% if (loggedIn) { %>
                    <a href="${pageContext.request.contextPath}/BookingServlet" class="btn btn-primary btn-lg">Jetzt buchen</a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/LoginServlet" class="btn btn-primary btn-lg">Login zum Buchen</a>
                <% } %>
            </div>
        </div>
    </section>

<section class="rating-section" id="ratings" style="padding: 40px 0; background: #fff; text-align: center;">
        <div class="container">
            <h2 style="margin-bottom: 20px;">Kundenbewertungen</h2>
            <div style="max-width: 400px; margin: 0 auto; background: #f8f9fa; border-radius: 10px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 25px;">
                <div style="font-size: 2.5rem; font-weight: bold; color: #ffc107; margin-bottom: 8px;">
                    <%= String.format("%.1f", averageRating) %> / 5.0
                </div>
                <div style="font-size: 1.5rem; margin-bottom: 10px; color: #ffc107;">
                    <% 
                    int fullStars = (int) averageRating;
                    boolean hasHalfStar = (averageRating - fullStars) >= 0.5;
                    for (int i = 0; i < fullStars; i++) { %>★<% }
                    if (hasHalfStar) { %>☆<% }
                    for (int i = fullStars + (hasHalfStar ? 1 : 0); i < 5; i++) { %>☆<% }
                    %>
                </div>
                <p style="font-size: 0.95rem; color: #666; margin-bottom: 15px;">
                    <%= ratingCount %> <%= ratingCount == 1 ? "Bewertung" : "Bewertungen" %>
                </p>
                <a href="${pageContext.request.contextPath}/RatingServlet" class="btn btn-primary">Alle Bewertungen ansehen</a>
            </div>
        </div>
    </section>
    <footer class="bg-dark text-light py-5 mt-5">
        <div class="container">
            <div class="row">
               
                <div class="col-md-4 mb-3">
                    <h5 style="color: #ffc107; margin-bottom: 15px;">Kontakt</h5>
                    <p style="font-size: 0.9rem; line-height: 1.8; margin-bottom: 0;">
                        <strong>Padel Colosseum 24/7 GmbH</strong><br>
                        Ernst-Boehe-Straße<br>
                        Ludwigshafen<br>
                        Telefon: 0621 123456<br>
                    </p>
                </div>
                <div class="col-md-4 mb-3">
                    
                    <ul style="list-style: none; padding: 0; font-size: 0.9rem; line-height: 2;">
                        <li><a href="#hero" style="color: #adb5bd; text-decoration: none;">Zurück zu Startseite</a></li>
                    </ul>
                </div>
                </div>
                    </div>
     
    </footer>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>