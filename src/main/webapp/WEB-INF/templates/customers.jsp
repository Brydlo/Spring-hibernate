<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<%-- W tym pliku używam tagów c:url do generowania adresów, które są poprawne niezależnie od miejsca zdeployowania aplikacji.
Obojętnie, czy aplikacja działa bezpośrednio pod adresem localhost:8080/ , czy w jakimś podkatalogu (ma niepusty "context-path"),
 - adresy są odpowiednio poprawiane.
 Jest to jednak nieco pracochłonne w zapisie, dlatego jest to tylko w tym jednym pliku.
 --%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista klientów</title>
    <c:url var="style_url" value="/styl.css"/>
    <link rel="stylesheet" type="text/css" href="${style_url}"/>
</head>
<body>
<h1>Lista klientów</h1>

<table class="data-table">
    <tr><th>Email</th><th>Nazwa</th><th>Telefon</th><th>Adres</th><th>Miasto</th><th/></tr>
    <c:forEach var="customer" items="${customers}">
        <c:url var="adr" value="/customers/${customer.customerEmail}"/>
        <tr>
            <td><a href="${adr}">${customer.customerEmail}</a></td>
            <td>${customer.customerName}</td>
            <td>${customer.phoneNumber}</td>
            <td>${customer.address}</td>
            <td>${customer.postalCode} ${customer.city}</td>

            <td>
                <c:url var="adres_do_edycji" value="/customers/${customer.customerEmail}/edit"/>
                <div class="action"><a href="${adres_do_edycji}">Edytuj</a></div>
            </td>
        </tr>
    </c:forEach>
</table>

<c:url var="adres_new" value="/customers/new"/>
<div class="action"><a href="${adres_new}">Dodaj nowego klienta</a></div>
</body>
</html>


