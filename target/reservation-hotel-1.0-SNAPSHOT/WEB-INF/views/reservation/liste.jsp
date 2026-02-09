<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
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
        <p class="subtitle">Gestion des réservations clients</p>
        
        <%
            List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
            int nbTotal = (reservations != null) ? reservations.size() : 0;
            int nbAssignees = 0;
            int nbEnAttente = 0;
            
            if (reservations != null) {
                for (Reservation r : reservations) {
                    if (r.getAssigner()) {
                        nbAssignees++;
                    } else {
                        nbEnAttente++;
                    }
                }
            }
        %>
        
        <div class="stats">
            <div class="stat-card">
                <div class="stat-number"><%= nbTotal %></div>
                <div class="stat-label">Total</div>
            </div>
            <div class="stat-card">
                <div class="stat-number"><%= nbAssignees %></div>
                <div class="stat-label">Assignées</div>
            </div>
            <div class="stat-card">
                <div class="stat-number"><%= nbEnAttente %></div>
                <div class="stat-label">En attente</div>
            </div>
        </div>
        
        <div class="actions-bar">
            <h2 style="color: #667eea; margin: 0;">Liste complète</h2>
            <a href="${pageContext.request.contextPath}/reservation/ajout" class="btn btn-primary">
                Nouvelle Réservation
            </a>
        </div>
        
        <% if (reservations != null && !reservations.isEmpty()) { 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Client</th>
                        <th>Passagers</th>
                        <th>Date Arrivée</th>
                        <th>Hôtel</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Reservation reservation : reservations) { %>
                        <tr>
                            <td><%= reservation.getIdReservation() %></td>
                            <td>
                                <strong><%= reservation.getNom() %> <%= reservation.getPrenom() %></strong>
                            </td>
                            <td style="text-align: center;">
                                <%= reservation.getNbPassager() %>
                            </td>
                            <td>
                                <%= reservation.getDateHeureArrivee().format(formatter) %>
                            </td>
                            <td>
                                <%= reservation.getHotel().getNom() %>
                            </td>
                            <td>
                                <% if (reservation.getAssigner()) { %>
                                    <span class="badge badge-assigned">Assignée</span>
                                <% } else { %>
                                    <span class="badge badge-pending">En attente</span>
                                <% } %>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/reservation/detail/<%= reservation.getIdReservation() %>" 
                                   class="btn btn-info">Détails</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <div class="empty-state">
                <div class="icon"</div>
                <h2>Aucune réservation trouvée</h2>
                <p>Commencez par ajouter une nouvelle réservation.</p>
            </div>
        <% } %>
        
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/" class="btn-back">← Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>
