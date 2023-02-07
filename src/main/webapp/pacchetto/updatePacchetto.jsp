<%@ page import="pacchetto.bean.Pacchetto" %><%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 07/02/2023
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Pacchetto p = (Pacchetto) request.getAttribute("pacchetto");
%>
<html>
<head>
    <title>Modifica Pacchetto</title>
</head>
<body>
<%@ include file="../user/navbar.jsp" %>
<form action="pacchettiControl?action=updatePacchetto&id=<%= p.getId() %>" method="post">

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputEmail4">Titolo</label>
            <input type="text" value="<%= p.getTitolo() %>" name="titolo" class="form-control" id="inputEmail4" readonly="readonly">
        </div>
        <div class="form-group col-md-6">
            <label for="inputPassword4">Prezzo</label>
            <input type="number" value=<%=p.getPrezzo() %> name="prezzo" class="form-control" id="inputPassword4">
        </div>
    </div>

    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputAddress2">Eventi Consigliati</label>
            <input type="text" name="eventi" value="<%=p.getEventiConsigliati() %>0" class="form-control" id="inputAddress2">
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Modifica</button>
</form>
</body>
</html>
