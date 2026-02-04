<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un véhicule</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/vehicule-form.css">
</head>
<body>

<div class="container">
    <h1>Ajouter un véhicule</h1>
    <p class="subtitle">Remplissez les informations du véhicule</p>

    <form action="${pageContext.request.contextPath}/vehicule/ajouter" method="post">

        <div class="form-group">
            <label>Numéro du véhicule</label>
            <input type="text" name="numero" required>
        </div>

        <div class="form-group">
            <label>Nombre de places</label>
            <input type="number" name="nbPlace" min="1" required>
        </div>

        <div class="form-group">
            <label>Carburant</label>
            <select name="carburant" required>
                <option value="">-- Choisir --</option>
                <option value="1">Essence</option>
                <option value="2">Gasoil</option>
            </select>
        </div>

        <div class="actions">
            <a href="${pageContext.request.contextPath}/vehicule/liste" class="btn-secondary">
                Annuler
            </a>
            <button type="submit" class="btn-primary">
                Enregistrer
            </button>
        </div>

    </form>
</div>

</body>
</html>
