<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.Movements" %>

<html>
<head>
    <title>Depense</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h2>Ajouter une ligne de Depense</h2>
    <form action="movements" method="post">
        <label for="label">Label :</label>
        <input type="text" id="label" name="label" required />

        <label for="amount">Amount :</label>
        <input type="number" name="amount" step="0.5" min="1" required />

        <input type="submit" value="Valider" />
    </form>
</body>
</html>