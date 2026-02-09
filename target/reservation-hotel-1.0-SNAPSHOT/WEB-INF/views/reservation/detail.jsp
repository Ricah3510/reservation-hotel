<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.touroperator.model.Reservation" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${titre}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reservation-style.css">
</head>
<body>
    <div class="container">
        <h1>${titre}</h1>
        
        <%
            Reservation reservation = (Reservation) request.getAttribute("reservation");
            if (reservation != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        %>
        
        <div class="detail-card">
            <div class="detail-row">
                <div class="detail-label">ID Réservation</div>
                <div class="detail-value"><%= reservation.getIdReservation() %></div>
            </div>
            
            <div class="detail-row">
                <div class="detail-label">Nom du Client</div>
                <div class="detail-value">
                    <strong><%= reservation.getNom() %> <%= reservation.getPrenom() %></strong>
                </div>
            </div>
            
            <div class="detail-row">
                <div class="detail-label"> Nombre de Passagers</div>
                <div class="detail-value"><%= reservation.getNbPassager() %></div>
            </div>
            
            <div class="detail-row">
                <div class="detail-label">Date et Heure d'Arrivée</div>
                <div class="detail-value">
                    <%= reservation.getDateHeureArrivee().format(formatter) %>
                </div>
            </div>
            
            <div class="detail-row">
                <div class="detail-label">Hôtel de Destination</div>
                <div class="detail-value"><%= reservation.getHotel().getNom() %></div>
            </div>
            
            <div class="detail-row">
                <div class="detail-label">Statut d'Assignation</div>
                <div class="detail-value">
                    <% if (reservation.getAssigner()) { %>
                        <span class="badge badge-assigned">✓ Assignée à un véhicule</span>
                    <% } else { %>
                        <span class="badge badge-pending">En attente d'assignation</span>
                    <% } %>
                </div>
            </div>
        </div>
        
        <% } else { %>
            <div class="empty-state">
                <div class="icon"></div>
                <h2>Réservation introuvable</h2>
                <p>Cette réservation n'existe pas ou a été supprimée.</p>
            </div>
        <% } %>
        
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/reservation/liste" class="btn-back">
                ← Retour à la liste
            </a>
        </div>
    </div>
</body>
</html>
