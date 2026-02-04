<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.touroperator.model.Vehicule" %>
<%@ page import="com.touroperator.model.Carburant" %>

<%
    Vehicule vehicule = (Vehicule) request.getAttribute("vehicule");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${titre}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/vehicule-details.css">
</head>

<body>
<div class="container">

    <h1>Détails du véhicule</h1>

    <% if (vehicule != null) { %>

        <div class="detail-row">
            <span class="label">ID :</span>
            <span><%= vehicule.getIdVehicule() %></span>
        </div>

        <div class="detail-row">
            <span class="label">Numéro :</span>
            <span><%= vehicule.getNumero() %></span>
        </div>

        <div class="detail-row">
            <span class="label">Nombre de places :</span>
            <span><%= vehicule.getNbPlace() %></span>
        </div>

        <div class="detail-row">
            <span class="label">Carburant :</span>
            <%= vehicule.getCarburant().getCarburant() %>
        </div>

    <% } else { %>
        <p>Véhicule introuvable.</p>
    <% } %>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/vehicule/liste" class="btn">
            Retour à la liste
        </a>
    </div>

</div>
</body>
</html>
