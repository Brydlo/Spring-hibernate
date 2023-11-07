<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Klient ${customer.customerName}</title>
    <c:url var="style_url" value="/styl.css"/>
    <link rel="stylesheet" type="text/css" href="${style_url}"/>
</head>
<body>
<h1>Klient ${customer.customerName}</h1>

<table class="data-table">
    <tr><th>Email:</th><td>${customer.customerEmail}</td></tr>
    <tr><th>Nazwa:</th><td>${customer.customerName}</td></tr>
    <tr><th>Numer tel:</th><td>${customer.phoneNumber}</td></tr>
    <tr><th>Adres:</th><td>${customer.address}</td></tr>
    <tr><th>Miasto:</th><td>${customer.postalCode} ${customer.city}</td></tr>
</table>

<c:url var="adres_do_edycji" value="/customers/${customer.customerEmail}/edit"/>
<div class="action"><a href="${adres_edycji}">Edytuj</a></div>

</body>
</html>


