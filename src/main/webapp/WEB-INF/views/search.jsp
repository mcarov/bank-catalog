<%@ page import="ru.cbrf.domain.Bank" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <%@include file="bootstrap-css.jsp"%>

    <title>Search</title>
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/page/1">Справочник БИК</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/banks/0/edit">Добавить банк</a>
                    </li>
                </ul>
                <form role="search" action="<%=request.getContextPath()%>/search">
                    <div class="input-group">
                        <input type="text" name="q" class="form-control" id="searchField" placeholder="БИК, регион, тип">
                        <div class="input-group-append">
                            <button class="btn btn-outline-primary" id="searchButton">Поиск</button>
                        </div>
                    </div>
                </form>
            </div>
        </nav>
        <br>
        <% List<Bank> banks = (List<Bank>)request.getAttribute("banks"); %>
        <% String query = (String)request.getAttribute("query"); %>
        <h4 class="text-center">Найдено <%=banks.size()%> записей по запросу "<%=query%>"</h4>
        <br>
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col"></th>
                <th scope="col">наименование участника расчетов</th>
                <th scope="col">БИК</th>
            </tr>
            </thead>
            <tbody>
            <% for(int i = 0; i < banks.size(); i++) { %>
            <tr>
                <th scope="row"><%=i+1%></th>
                <td><a href="<%=request.getContextPath()%>/banks/<%=banks.get(i).getNewNum()%>"><%=banks.get(i).getNameP()%></a></td>
                <td><%=banks.get(i).getNewNum()%></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>