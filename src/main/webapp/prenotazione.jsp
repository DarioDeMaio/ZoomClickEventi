<%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 02/02/2023
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    String titolo = (String) request.getAttribute("titolo");
    double prezzo = (Double) request.getAttribute("prezzo");

%>
<html>
<head>
    <title>Prenotazione Party</title>

</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="page-header">
    <h1>PACCHETTO: <%=titolo%><br>
        <small>PREZZO: &euro;<%=prezzo%></small>
    </h1>
    <br><br>
</div>

<form action="prenotazione?action=prenotazione&idPacchetto=" method="post">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputEmail4">Tipo Evento</label>
            <input type="text" name="tipoEvento" class="form-control" id="inputEmail4" placeholder="18 esimo / laurea / matrimonio" style="text-transform:capitalize;">
        </div>
        <div class="form-group col-md-6">
            <label for="inputPassword4">Festeggiato</label>
            <input type="text" name="festeggiato" class="form-control" id="inputPassword4" placeholder="Nome/i del/dei festeggiato/festeggiati" style="text-transform:capitalize;">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputAddress2">Nome Locale</label>
            <input type="text" name="locale" class="form-control" id="inputAddress2" placeholder="Nome del Locale">
        </div>
        <div class="form-group col-md-6">
            <label for="exampleFormControlTextarea1">Città</label>
            <input type="text" name="citta" class="form-control" id="inputAddress2" placeholder="Città del locale">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputAddress2">Data Evento</label>
            <input type="date" name="dataEvento" class="form-control" id="inputAddress2" placeholder="Data dell'evento">
        </div>
        <div class="form-group col-md-6">
            <label for="exampleFormControlTextarea1">Servizi aggiuntivi</label>
            <textarea name="servizi" class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="facoltativo"></textarea>
        </div>
    </div>
    <input type="submit" class="btn btn-primary" value="Prenota">
</form>

</body>
</html>
