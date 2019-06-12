<%@ page import="java.util.Optional" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.cbrf.domain.*" %>
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
    <%@include file="bootstrap-scripts.jsp"%>

    <title>Edit</title>
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/page/1">Справочник БИК</a>
                    </li>
                    <li class="nav-item active">
                        <a class="nav-link">Добавить банк</a>
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
        <% Bank bank = new Bank();
            if(request.getAttribute("bank") != null) {
                bank = (Bank)request.getAttribute("bank");
            }
            List<Pzn> pznList = (List<Pzn>)request.getAttribute("pznList");
            List<Uer> uerList = (List<Uer>)request.getAttribute("uerList");
            List<Rgn> rgnList = (List<Rgn>)request.getAttribute("rgnList");
            List<Tnp> tnpList = (List<Tnp>)request.getAttribute("tnpList");
        %>
        <form action="<%=request.getContextPath()%>/banks/<%=bank.getNewNum()%>/save" method="post">
            <div class="row">
                <div class="col-6">
                    <div class="form-group">
                        <% Optional<String> real = Optional.ofNullable(bank.getReal()); %>
                        <label for="real">Код контроля допустимости проведения расчетных операций</label>
                        <input id="real" name="real" class="form-control" type="text" value="<%=real.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% Optional<String> pzn = Optional.ofNullable(bank.getPzn()); %>
                        <label for="pzn">Код типа участника расчетов</label>
                        <select name="pzn" id="pzn" class="custom-select">
                            <% for(Pzn item: pznList) { %>
                            <% if(StringUtils.equalsIgnoreCase(pzn.orElse(""), item.getPzn())) {%>
                                <option title="<%=item.getPzn()%>" selected><%=item.getName()%></option>
                            <% } else { %>
                                <option title="<%=item.getPzn()%>"><%=item.getName()%></option>
                            <% } %>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <% Optional<String> uer = Optional.ofNullable(bank.getUer()); %>
                        <label for="uer">Код пользователя системы электронных расчетов</label>
                        <select name="uer" id="uer" class="custom-select">
                            <% for(Uer item: uerList) { %>
                            <% if(StringUtils.equalsIgnoreCase(uer.orElse(""), item.getUer())) {%>
                            <option title="<%=item.getUer()%>" selected><%=item.getName()%></option>
                            <% } else { %>
                            <option title="<%=item.getUer()%>"><%=item.getName()%></option>
                            <% } %>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <% Optional<String> rgn = Optional.ofNullable(bank.getRgn()); %>
                        <label for="rgn">Код территории Российской Федерации</label>
                        <select name="rgn" id="rgn" class="custom-select">
                            <% for(Rgn item: rgnList) { %>
                            <% if(StringUtils.equalsIgnoreCase(rgn.orElse(""), item.getRgn())) {%>
                            <option title="<%=item.getRgn()%>" selected><%=item.getName()%></option>
                            <% } else { %>
                            <option title="<%=item.getRgn()%>"><%=item.getName()%></option>
                            <% } %>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <% Optional<String> ind = Optional.ofNullable(bank.getInd()); %>
                        <label for="ind">Индекс</label>
                        <input id="ind" name="ind" class="form-control" type="text" value="<%=ind.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% Optional<String> tnp = Optional.ofNullable(bank.getTnp()); %>
                        <label for="tnp">Код типа населенного пункта</label>
                        <select name="tnp" id="tnp" class="custom-select">
                            <option>НЕ ОПРЕДЕЛЕН</option>
                            <% for(Tnp item: tnpList) { %>
                            <% if(StringUtils.equalsIgnoreCase(tnp.orElse(""), item.getTnp())) {%>
                            <option title="<%=item.getTnp()%>" selected><%=item.getFullName()%></option>
                            <% } else { %>
                            <option title="<%=item.getTnp()%>"><%=item.getFullName()%></option>
                            <% } %>
                            <% } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <% Optional<String> nnp = Optional.ofNullable(bank.getNnp()); %>
                        <label for="nnp">Населенный пункт</label>
                        <input id="nnp" name="nnp" class="form-control" type="text" value="<%=nnp.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% Optional<String> adr = Optional.ofNullable(bank.getAdr()); %>
                        <label for="adr">Адрес</label>
                        <input id="adr" name="adr" class="form-control" type="text" value="<%=adr.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% Optional<String> rkc = Optional.ofNullable(bank.getRkc()); %>
                        <label for="rkc">БИК РКЦ (ГРКЦ)</label>
                        <input id="rkc" name="rkc" class="form-control" type="text" value="<%=rkc.orElse("")%>">
                    </div>
                </div>
                <div class="col-6">
                    <div class="form-group">
                        <% Optional<String> nameP = Optional.ofNullable(bank.getNameP()); %>
                        <label for="nameP">Наименование участника расчетов</label>
                        <input id="nameP" name="nameP" class="form-control" type="text" value="<%=nameP.orElse("")%>" required>
                    </div>
                    <div class="form-group">
                        <% Optional<String> newNum = Optional.ofNullable(bank.getNewNum()); %>
                        <label for="newNum">Банковский идентификационный код (БИК)</label>
                        <% if(StringUtils.isEmpty(newNum.orElse(""))) { %>
                            <input id="newNum" name="newNum" class="form-control" type="text" value="<%=newNum.orElse("")%>" required>
                        <% } else { %>
                            <input id="newNum" name="newNum" class="form-control" type="text" value="<%=newNum.orElse("")%>" required>
                        <% } %>
                    </div>
                    <div class="form-group">
                        <% Optional<String> phoneNum = Optional.ofNullable(bank.getPhoneNum()); %>
                        <label for="phoneNum">Телефон</label>
                        <input id="phoneNum" name="phoneNum" class="form-control" type="text" value="<%=phoneNum.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% Optional<String> regNum = Optional.ofNullable(bank.getRegNum()); %>
                        <label for="regNum">Регистрационный номер</label>
                        <input id="regNum" name="regNum" class="form-control" type="text" value="<%=regNum.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% Optional<String> okpo = Optional.ofNullable(bank.getOkpo()); %>
                        <label for="okpo">Код ОКПО</label>
                        <input id="okpo" name="okpo" class="form-control" type="text" value="<%=okpo.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% String changingDate = "";
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            if(bank.getChangingDate() != null) {
                                changingDate = dateFormat.format(bank.getChangingDate());
                            }
                        %>
                        <label for="changingDate">Дата последнего изменения записи</label>
                        <input id="changingDate" name="changingDate" class="form-control" type="text" value="<%=changingDate%>" required>
                    </div>
                    <div class="form-group">
                        <% Optional<String> ksnp = Optional.ofNullable(bank.getKsnp()); %>
                        <label for="ksnp">Номер счета</label>
                        <input id="ksnp" name="ksnp" class="form-control" type="text" value="<%=ksnp.orElse("")%>">
                    </div>
                    <div class="form-group">
                        <% String inclusionDate = "";
                            if(bank.getInclusionDate() != null) {
                                inclusionDate = dateFormat.format(bank.getInclusionDate());
                            }
                        %>
                        <label for="inclusionDate">Дата включения информации об участнике расчетов в ЭБД</label>
                        <input id="inclusionDate" name="inclusionDate" class="form-control" type="text" value="<%=inclusionDate%>" required>
                    </div>
                    <div class="form-group">
                        <% String controlDate = "";
                            if(bank.getControlDate() != null) {
                                controlDate = dateFormat.format(bank.getControlDate());
                            }
                        %>
                        <label for="controlDate">Дата контроля</label>
                        <input id="controlDate" name="controlDate" class="form-control" type="text" value="<%=controlDate%>">
                    </div>
                </div>
            </div>
            <br>
            <div class="row justify-content-center">
                <button class="btn btn-outline-primary" type="submit">Сохранить</button>
            </div>
        </form>
    </div>
</body>
</html>
