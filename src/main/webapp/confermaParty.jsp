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
</head>
<body>
<%@ include file="navbar.jsp"%>
<% Party p = gParty.findById(idParty); %>
<form action="/prenotazione?action=confermaParty&idParty=<%=idParty%>" method="post" enctype="multipart/form-data">

    <label>DJ</label>
    <select name="dj">
        <%
            for(Artista art:artisti)
            {
                if(art.getTipoArtista().compareTo("DJ")==0)
                {
        %>
        <option value=<%= art.getId() %>> <%= art.getNome() %> <%= art.getCognome() %></option>
            <%  } %>
        <%  } %>

    </select>

    <label>Fotografi</label>
    <select name="fotografi">
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

    <label>Speaker</label>
    <select name="speaker">
        <%
            for(Artista art:artisti)
            {
                if(art.getTipoArtista().compareTo("Speaker")==0)
                {
        %>
        <option value=<%= art.getId() %>> <%= art.getNome() %> <%= art.getCognome() %></option>
            <%  } %>
        <%  } %>

    </select><br><br>

    <label class="labelFornitori">Fornitori</label>

    <%
        for(Fornitore f:fornitori){
    %>
    <br>
    <input class="box" name="idFornitori" type="checkbox" value=<%=f.getId() %>> <%=f.getNomeAzienda() %>

    <%} %>

    <button type="submit" class="btn btn-primary">Conferma</button>
</form>
</body>
</html>
