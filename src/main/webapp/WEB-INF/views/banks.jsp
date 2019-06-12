<%@ page import="ru.cbrf.domain.Bank" %>
<%@ page import="java.util.List" %>
<%@ page import="static ru.cbrf.Constants.LIST_SIZE" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <%@include file="bootstrap-css.jsp"%>

    <%! private int num = 0;
        private int lastNum = 0;
        private int size = 0;
    %>
    <% if(request.getAttribute("page") != null && request.getAttribute("last") != null) {
        num = (Integer)request.getAttribute("page");
        lastNum = (Integer)request.getAttribute("last");
        size = (Integer)request.getAttribute("size");
    } %>

    <title>Bank Catalog</title>
</head>
<body>
    <div class="container">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link">Справочник БИК</a>
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
        <% if(banks.size() > 0) { %>
            <% if(size > LIST_SIZE) { %>
                <nav>
                    <ul class="pagination justify-content-center">
                        <% if(num == 1) { %>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Begin">
                                <span aria-hidden="true">&laquo;&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item disabled"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/2">2</a></li>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/3">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num+1%>" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/<%=lastNum%>" aria-label="End">
                                <span aria-hidden="true">&raquo;&raquo;</span>
                            </a>
                        </li>
                        <% } else if(num == lastNum) { %>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/1" aria-label="Begin">
                                <span aria-hidden="true">&laquo;&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-2%>"><%=num-2%></a></li>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>"><%=num-1%></a></li>
                        <li class="page-item disabled"><a class="page-link" href="#"><%=num%></a></li>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="End">
                                <span aria-hidden="true">&raquo;&raquo;</span>
                            </a>
                        </li>
                        <% } else { %>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/1" aria-label="Begin">
                                <span aria-hidden="true">&laquo;&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>"><%=num-1%></a></li>
                        <li class="page-item disabled"><a class="page-link" href="#"><%=num%></a></li>
                        <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num+1%>"><%=num+1%></a></li>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num+1%>" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="<%=request.getContextPath()%>/page/<%=lastNum%>" aria-label="End">
                                <span aria-hidden="true">&raquo;&raquo;</span>
                            </a>
                        </li>
                        <% } %>
                    </ul>
                </nav>
            <% } %>

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
                            <th scope="row"><%=(num-1)*LIST_SIZE+(i+1)%></th>
                            <td><a href="<%=request.getContextPath()%>/banks/<%=banks.get(i).getNewNum()%>"><%=banks.get(i).getNameP()%></a></td>
                            <td><%=banks.get(i).getNewNum()%></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <% if(size > LIST_SIZE) { %>
            <nav>
                <ul class="pagination justify-content-center">
                    <% if(num == 1) { %>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="Begin">
                            <span aria-hidden="true">&laquo;&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item disabled"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/2">2</a></li>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/3">3</a></li>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num+1%>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/<%=lastNum%>" aria-label="End">
                            <span aria-hidden="true">&raquo;&raquo;</span>
                        </a>
                    </li>
                    <% } else if(num == lastNum) { %>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/1" aria-label="Begin">
                            <span aria-hidden="true">&laquo;&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-2%>"><%=num-2%></a></li>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>"><%=num-1%></a></li>
                    <li class="page-item disabled"><a class="page-link" href="#"><%=num%></a></li>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <li class="page-item disabled">
                        <a class="page-link" href="#" aria-label="End">
                            <span aria-hidden="true">&raquo;&raquo;</span>
                        </a>
                    </li>
                    <% } else { %>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/1" aria-label="Begin">
                            <span aria-hidden="true">&laquo;&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num-1%>"><%=num-1%></a></li>
                    <li class="page-item disabled"><a class="page-link" href="#"><%=num%></a></li>
                    <li class="page-item"><a class="page-link" href="<%=request.getContextPath()%>/page/<%=num+1%>"><%=num+1%></a></li>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/<%=num+1%>" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="<%=request.getContextPath()%>/page/<%=lastNum%>" aria-label="End">
                            <span aria-hidden="true">&raquo;&raquo;</span>
                        </a>
                    </li>
                    <% } %>
                </ul>
            </nav>
            <% } %>
        <% } %>
    </div>
</body>
</html>
