<%@ page import="java.util.HashSet" %>
<%@ page import="party.manager.PartyManager" %>
<%@ page import="party.bean.*" %><%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 06/02/2023
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String id = (String) request.getAttribute("idParty2");
    int idParty =  Integer.parseInt(id);
    HashSet<Artista> artisti = (HashSet<Artista>) request.getAttribute("artisti");
    HashSet<Fornitore> fornitori = (HashSet<Fornitore>) request.getAttribute("fornitori");
%>
<html>
<head>
    <title>Conferma Party</title>

    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script
            src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>

    <style>

        button{
            margin-left:2%;
        }

        .box{
            margin-left: 2%;
        }

        .labelArtisti{
            margin-left: 2%;
            font-weight: bold;
        }

    </style>
</head>
<body>
<%@ include file="../user/navbar.jsp"%>
<% Party p = gParty.findById(idParty); %>

<h1>Pacchetto richiesto: <%=p.getPacchetto().getTitolo()%></h1>
<h2>Prezzo pacchetto: <%=p.getPacchetto().getPrezzo()%></h2>
<h2>Servizi richiesti:<%=p.getServizi()%></h2>
<h2>Città:<%=p.getCitta()%></h2>
<h2>Locale:<%=p.getNomeLocale()%></h2>
<h2>Tipo: <%=p.getTipo()%></h2>

<form method="post" action="prenotazione?action=confermaParty&idParty=<%=idParty%>" onsubmit="return validate(this)">

    <label>DJ</label>
    <select name="dj">
        <option value="">Seleziona un dj</option>
        <%
            for(Artista art:artisti)
            {

                if(art.getTipoArtista().compareTo("DJ")==0)
                {
        %>
        <option value=<%= art.getId() %>> <%= art.getNome() %> <%= art.getCognome() %> </option>
            <%  } %>
        <%  } %>

    </select>
    <input type="number" name="prezzoDj" placeholder="costo dj" value="0">
    <br><br>
    <label>Fotografi</label>
    <select name="fotografi">
        <option value="">Seleziona un fotografo</option>
        <%
            for(Artista art:artisti)
            {
                if(art.getTipoArtista().compareTo("Fotografo")==0)
                {
        %>
        <option value=<%= art.getId() %>> <%= art.getNome() %> <%= art.getCognome() %></option>
            <%  } %>
        <%  } %>

    </select>
    <input type="number" name="prezzoFotografo" placeholder="costo fotografo" value="0">
    <br><br>
    <label>Speaker</label>
    <select name="speaker">
        <option value="">Seleziona uno speaker</option>
        <%
            for(Artista art:artisti)
            {
                if(art.getTipoArtista().compareTo("Speaker")==0)
                {
        %>
        <option value=<%= art.getId() %>> <%= art.getNome() %> <%= art.getCognome() %></option>
            <%  } %>
        <%  } %>

    </select>
    <input type="number" name="prezzoSpeaker" placeholder="costo speaker" value="0">
    <br><br>

    <label class="labelFornitori">Fornitori</label>

    <%
        for(Fornitore f:fornitori){
    %>
    <br>
    <input class="box" name="idFornitori" type="checkbox" value=<%=f.getId() %>> <%=f.getNomeAzienda() %>

    <%} %>
    <br><br>
    <button type="submit" class="btn btn-primary">Conferma</button>
</form>

<a href="prenotazione?action=rifiuta&idParty=<%=idParty%>"><button type="" class="btn btn-primary">Rifiuta</button></a>

<script type="text/javascript" src="js/validationGestoreParty.js"></script>
</body>
</html>
