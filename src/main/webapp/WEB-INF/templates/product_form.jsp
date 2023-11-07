<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edycja danych produktu</title>
    <link rel="stylesheet" type="text/css" href="/styl.css">
</head>
<body>
<h1>Edycja produktu</h1>
<form id="product-form" method="post">
    <table class="form">
        <tr>
            <td><label for="productId">Numer:</label></td>
            <td><input id="productId" name="productId" placeholder="brak" type="number"
                       readonly="readonly" value="${product.productId}" /></td>
        </tr>
        <tr>
            <td><label for="productName">Nazwa towaru:</label></td>
            <td><input id="productName" name="productName" placeholder="nazwa..."
                       type="text" value="${product.productName}" /></td>
        </tr>
        <tr>
            <td><label for="price">Cena:</label></td>
            <td><input id="price" name="price" placeholder="12.90"
                       title="tu wpisz cenę" type="number" step="0.01"
                       value="${product.price}" /></td>
        </tr>
        <tr>
            <td><label for="vat">Stawka VAT:</label></td>
            <td><input id="vat" name="vat" placeholder="0.23" title="tu wpisz vat"
                       type="number" step="0.01" value="${product.vat}" /></td>
        </tr>
        <tr>
            <td><label for="description">Opis:</label></td>
            <td><textarea id="description" name="description" rows="10" cols="120">${product.description}</textarea></td>
        </tr>
        <tr>
            <td><button>Zapisz</button></td>
        </tr>
    </table>
</form>
<div class="action">
    <a href="/products">powrót do listy produktów</a>
</div>
<div class="action">
    <a href="/">powrót do spisu treści</a>
</div>
</body>
</html>

