<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.touroperator.model.Hotel" %>
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
        <p class="subtitle">Remplissez le formulaire pour créer une nouvelle réservation</p>
        
        <form action="${pageContext.request.contextPath}/reservation/save" method="post">
            
            <div class="form-group">
                <label for="nom">Nom *</label>
                <input type="text" id="nom" name="nom" required 
                       placeholder="Ex: Rakoto">
            </div>
            
            <div class="form-group">
                <label for="prenom">Prénom *</label>
                <input type="text" id="prenom" name="prenom" required 
                       placeholder="Ex: Tiana">
            </div>
            
            <div class="form-group">
                <label for="nbPassager">Nombre de Passagers *</label>
                <input type="number" id="nbPassager" name="nbPassager" 
                       min="1" max="20" required 
                       placeholder="Ex: 4">
            </div>
            
            <div class="form-group">
                <label for="dateHeureArrivee">Date et Heure d'Arrivée *</label>
                <input type="datetime-local" id="dateHeureArrivee" name="dateHeureArrivee" required>
            </div>
            
            <div class="form-group">
                <label for="idHotel">Hôtel de Destination *</label>
                <select id="idHotel" name="idHotel" required>
                    <option value="">-- Sélectionnez un hôtel --</option>
                    <%
                        List<Hotel> hotels = (List<Hotel>) request.getAttribute("hotels");
                        if (hotels != null) {
                            for (Hotel hotel : hotels) {
                                // Exclure l'aéroport (id=1)
                                if (hotel.getIdHotel() != 1) {
                    %>
                                    <option value="<%= hotel.getIdHotel() %>">
                                        <%= hotel.getNom() %>
                                    </option>
                    <%
                                }
                            }
                        }
                    %>
                </select>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">
                    Enregistrer la Réservation
                </button>
                <a href="${pageContext.request.contextPath}/reservation/liste" class="btn btn-info">
                    Annuler
                </a>
            </div>
            
        </form>
        
        <div class="text-center" style="margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/reservation/liste" class="btn-back">
                <- Retour à la liste
            </a>
        </div>
    </div>
</body>
</html>
