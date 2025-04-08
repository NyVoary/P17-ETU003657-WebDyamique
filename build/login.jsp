<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.User" %>

<html>
<head>
    <title>Connexion</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h2>Connexion</h2>
    <form action="action" method="post">
        <input type="hidden" name="action" value="LoginServlet" />

        <label for="username">Username :</label>
        <input type="text" id="username" name="username" value="<%= request.getAttribute("defaultUsername") %>" required />

        <label for="password">Password :</label>
        <input type="password" id="password" name="password" value="<%= request.getAttribute("defaultPassword") %>" required />

        <%
        // Si un message d'erreur est passé via la requête
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
        %>
            <div class="error"><%= errorMessage %></div>
        <% } %>

        <input type="submit" value="Se connecter" />
    </form>
</body>
</html>