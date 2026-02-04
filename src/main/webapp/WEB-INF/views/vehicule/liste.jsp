<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.touroperator.model.Vehicule" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${titre}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/vehicule-style.css">
</head>
<body>
    <div class="container">
        <h1>${titre}</h1>
        <p class="subtitle">Gestion de la flotte de véhicules</p>
        
        <%
            List<Vehicule> vehicules = (List<Vehicule>) request.getAttribute("vehicules");
            int nbVehicules = (vehicules != null) ? vehicules.size() : 0;
        %>
        
        <div class="stats">
            <div class="stat-card">
                <div class="stat-number"><%= nbVehicules %></div>
                <div class="stat-label">Véhicules Total</div>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/vehicule/ajout" class="btn-info">
            + Ajouter un véhicule
        </a>

        <% if (vehicules != null && !vehicules.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Numéro</th>
                        <th>Nombre de Places</th>
                        <th>Carburant</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Vehicule vehicule : vehicules) { %>
                    <tr>
                        <td><%= vehicule.getIdVehicule() %></td>
                        <td>
                            <span class="vehicule-numero"><%= vehicule.getNumero() %></span>
                        </td>
                        <td class="places">
                            <%= vehicule.getNbPlace() %> places
                        </td>
                        <td>
                            <% if (vehicule.getCarburant() == 1) { %>
                                <span class="badge badge-essence">Essence</span>
                            <% } else { %>
                                <span class="badge badge-gasoil">Gasoil</span>
                            <% } %>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/vehicule/details/<%= vehicule.getIdVehicule() %>">
                                Voir détails
                            </a>
                        </td>
                    </tr>
                <% } %>
                </tbody>

            </table>
        <% } else { %>
            <div class="empty-state">
                <div class="icon"></div>
                <h2>Aucun véhicule trouvé</h2>
                <p>La flotte est vide pour le moment.</p>
            </div>
        <% } %>
        
        <div class="text-center">
            <a href="${pageContext.request.contextPath}/" class="btn-back">← Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>
