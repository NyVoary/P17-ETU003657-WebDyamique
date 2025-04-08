<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.User" %>
<%@ page import="entities.Budget" %>

<html>
<head>
    <title>Credit</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h2>Ajouter une ligne de Credit</h2>
    <form action="budget" method="post">
        <label for="label">Label :</label>
        <input type="text" id="label" name="label" required />

        <label for="amount">Amount :</label>
        <input type="number" name="amount" step="0.5" min="1" required />

        <input type="submit" value="Valider" />
    </form>

    <a href="movements.jsp">Ajouter une dépense</a>
    <a href="dashboard.jsp">Dashboard</a>

    <% if (request.getAttribute("successMessage") != null) { %>
        <p style="color: green;"><%= request.getAttribute("successMessage") %></p>
    <% } %>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
    <% } %>
</body>
</html>