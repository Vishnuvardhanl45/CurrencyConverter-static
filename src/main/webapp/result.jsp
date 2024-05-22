<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Conversion Result</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .container {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Conversion Result</h1>
        <p>Amount: ${amount}</p>
        <p>Source Currency: ${sourceCurrency}</p>
        <p>Source Currency Symbol: ${sourceCurrencySymbol}</p>
        <p>Target Currency: ${targetCurrency}</p>
        <p>Target Currency Symbol: ${targetCurrencySymbol}</p>
        <p>Converted Amount: ${convertedAmount}</p>
    </div>
</body>
</html>
