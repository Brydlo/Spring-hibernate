<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edycja danych klienta</title>
    <c:url var="style_url" value="/styl.css"/>
    <link rel="stylesheet" type="text/css" href="${style_url}"/>
</head>
<body>
<h1>Edycja danych klienta</h1>

<f:form id="product-form" method="post" modelAttribute="customer">
    <table class="form-tab">
        <tr>
            <td><f:label path="customerEmail">Email:</f:label></td>
            <td><f:input path="customerEmail" type="email"/>
                <f:errors path="customerEmail" cssClass="form-error" element="div"/>
            </td>
        </tr>
        <tr>
            <td><f:label path="customerName">Nazwa / imię i nazwisko:</f:label></td>
            <td><f:input path="customerName" placeholder="Jan Kowalski" type="text"/>
                <f:errors path="customerName" cssClass="form-error" element="div"/>
            </td>
        </tr>
        <tr>
            <td><f:label path="phoneNumber">Telefon:</f:label></td>
            <td><f:input path="phoneNumber" placeholder="123123123" type="text"/>
                <f:errors path="phoneNumber" cssClass="form-error" element="div"/>
            </td>
        </tr>
        <tr>
            <td><f:label path="address">Adres:</f:label></td>
            <td><f:textarea path="address" rows="2" cols="120"/></td>
        </tr>
        <tr>
            <td><f:label path="postalCode">Kod pocztowy:</f:label></td>
            <td><f:input path="postalCode" placeholder="12-345" type="text"/>
                <f:errors path="postalCode" cssClass="form-error" element="div"/>
            </td>
        </tr>
        <tr>
            <td><f:label path="city">Miasto:</f:label></td>
            <td><f:input path="city" placeholder="Kraków" type="text"/>
                <f:errors path="city" cssClass="form-error" element="div"/>
            </td>
        </tr>
        <tr>
            <td><f:button>Zapisz</f:button></td>
        </tr>
    </table>
</f:form>

<div><a href="${pageContext.request.contextPath}/customers">powrót do listy klientów</a></div>
</body>
</html>

