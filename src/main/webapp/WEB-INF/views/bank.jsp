<%@ page import="ru.cbrf.domain.Bank" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <%@include file="bootstrap-css.jsp"%>

    <title>Bank</title>
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
        <% Bank bank = (Bank)request.getAttribute("bank");
            String pzn = (String)request.getAttribute("pzn");
            String uer = (String)request.getAttribute("uer");
            String rgn = (String)request.getAttribute("rgn");
            String tnp = (String)request.getAttribute("tnp");
        %>
        <div class="d-flex flex-row justify-content-between">
            <div class="d-flex flex-column">
                <a href="<%=request.getContextPath()%>/banks/<%=bank.getNewNum()%>/edit" class="btn btn-outline-primary align-self-stretch my-2">Редактировать</a>
                <a href="<%=request.getContextPath()%>/banks/<%=bank.getNewNum()%>/remove" class="btn btn-outline-danger">Удалить</a>
            </div>
            <div class="d-flex flex-column align-items-center">
                <h2 class="text-center"><%=bank.getNameP()%></h2>
                <h5 class="text-center">БИК: <%=bank.getNewNum()%></h5>
            </div>
            <div class="d-flex flex-column"></div>
        </div>
        <br>
        <table class="table">
            <tbody>
                <tr>
                    <td>Код контроля допустимости проведения расчетных операций</td>
                    <td><%=bank.getReal()%></td>
                </tr>
                <tr>
                    <td>Тип участника расчетов</td>
                    <td><%=pzn%></td>
                </tr>
                <tr>
                    <td>Пользоаватель системы электронных расчетов</td>
                    <td><%=uer%></td>
                </tr>
                <tr>
                    <td>Территория Российской Федерации</td>
                    <td><%=rgn%></td>
                </tr>
                <tr>
                    <td>Индекс</td>
                    <td><%=bank.getInd()%></td>
                </tr>
                <tr>
                    <td>Тип населенного пункта</td>
                    <td><%=tnp%></td>
                </tr>
                <tr>
                    <td>Населенный пункт</td>
                    <td><%=bank.getNnp()%></td>
                </tr>
                <tr>
                    <td>Адрес</td>
                    <td><%=bank.getAdr()%></td>
                </tr>
                <tr>
                    <td>БИК РКЦ (ГРКЦ)</td>
                    <td><%=bank.getRkc()%></td>
                </tr>
                <tr>
                    <td>Телефон</td>
                    <td><%=bank.getPhoneNum()%></td>
                </tr>
                <tr>
                    <td>Регистрационный номер</td>
                    <td><%=bank.getRegNum()%></td>
                </tr>
                <tr>
                    <td>Код ОКПО</td>
                    <td><%=bank.getOkpo()%></td>
                </tr>
                <tr>
                    <td>Дата последнего изменения записи</td>
                    <% Date date = bank.getChangingDate();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    if(date != null) { %>
                        <td><%=dateFormat.format(date)%></td>
                    <% } else { %>
                        <td></td>
                    <% }%>
                </tr>
                <tr>
                    <td>Номер счета</td>
                    <td><%=bank.getKsnp()%></td>
                </tr>
                <tr>
                    <td>Дата включения информации об участнике расчетов в ЭБД</td>
                    <% date = bank.getInclusionDate();
                    if(date != null) { %>
                        <td><%=dateFormat.format(date)%></td>
                    <% } else { %>
                        <td></td>
                    <% }%>
                </tr>
                <tr>
                    <td>Дата контроля</td>
                    <% date = bank.getControlDate();
                    if(date != null) { %>
                        <td><%=dateFormat.format(date)%></td>
                    <% } else { %>
                    <td></td>
                    <% }%>
                </tr>
            </tbody>
        </table>
        <br>
    </div>
</body>
</html>
