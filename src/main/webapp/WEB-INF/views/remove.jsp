<%@ page import="ru.cbrf.domain.Bank" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <%@include file="bootstrap-css.jsp"%>

    <title>Remove</title>
</head>
<body>
    <% Bank bank = (Bank)request.getAttribute("bank"); %>
    <div class="container">
        <br>
        <h4 class="text-center mt-100">Удалить запись "<%=bank.getNameP()%>" из базы данных?</h4>
        <div class="d-flex flex-row justify-content-center">
            <form action="<%=request.getContextPath()%>/banks/<%=bank.getNewNum()%>/remove" method="post">
                <button class="btn btn-outline-danger mr-2">Да</button>
            </form>
            <a href="<%=request.getContextPath()%>/banks/<%=bank.getNewNum()%>" class="btn btn-outline-secondary">Нет</a>
        </div>
    </div>
</body>
</html>
