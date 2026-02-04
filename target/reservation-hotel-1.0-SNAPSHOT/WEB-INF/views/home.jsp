<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/home-style.css">
    <title>${titre}</title>

</head>
<body>
    <div class="container">
        <h1> ${titre}</h1>
        <h2>Ricah 3510</h2>
        <div class="message">
            <strong>${message}</strong>
        </div>
        
        <div class="info">
            <p><strong> Spring MVC est opérationnel !</strong></p>
            <p>Technologies utilisées :</p>
            <div>
                <span class="badge">Spring MVC</span>
                <span class="badge">Spring Framework</span>
                <span class="badge">JPA / Hibernate</span>
                <span class="badge">PostgreSQL</span>
                <span class="badge">JSP / JSTL</span>
            </div>
        </div>
        
        <a href="<c:url value='/vehicule/liste'/>" class="btn">Liste Vehicule</a>
    </div>
</body>
</html>
