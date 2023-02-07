<%@ page import="party.bean.Party" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 07/02/2023
  Time: 18:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Incassi</title>
</head>
<body>
<%@ include file="navbar.jsp" %>

<%
    HashMap<Party, Double> map = (HashMap<Party, Double>) request.getAttribute("parties");

    for(Party p: map.keySet()){
%>
<div class="div-generale" class="container d-flex justify-content-center mt-50 mb-50">

    <div class="row">
        <div class="col-md-10">

            <div class="card card-body">
                <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row">


                    <div class="media-body">
                        <h3 class="media-title font-weight-semibold">
                            Nome festeggiato/i: <%=p.getFesteggiato()%>
                        </h3>
                        <h3>Tipo evento: <%=p.getTipo()%></h3>
                        <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                            <li class="list-inline-item"><h6>Data</h6></li>
                            <li><h6><%=p.getData()%></h6></li>
                        </ul>

                        <p class="mb-3">Città: <%=p.getCitta()%></p>
                        <p class="mb-3">Locale: <%=p.getNomeLocale()%></p>

                    </div>

                    <div class="mt-3 mt-lg-0 ml-lg-3 text-center">

                        <h3 class="mb-0 font-weight-semibold">&euro; <%=map.get(p)%>0</h3>

                        <div>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>
                            <i class="fa fa-star"></i>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%}%>

</body>
</html>
