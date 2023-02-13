<%@ page import="java.util.HashSet" %>
<%@ page import="pacchetto.bean.Pacchetto" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 07/02/2023
  Time: 15:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pacchetti</title>

  <style>

    .bottone_search2 {
      border: solid 1px purple;
      text-align: center;
      width:50%;
      align-content: center;
      width:100px;
      height: 40px;
      margin-bottom: 3%;
      color:purple;

    }

    .bottone_search2 h6{
      margin-top: 5%;
    }

    .bottone_search2:hover {
      background: purple;
    }

    .bottone_search2:hover .testoCarrello{
      color: white;
    }

  </style>
</head>
<body>
<%@ include file="../user/navbar.jsp" %>

<%
  HashSet<Pacchetto> pacchetti = gPacchetti.getPacchetti();
  Iterator<Pacchetto> it = pacchetti.iterator();
  while(it.hasNext())
  {
    Pacchetto p = it.next();
%>

<div class="div-generale" class="container d-flex justify-content-center mt-50 mb-50">
  <div class="row">
    <div class="col-md-10">
        <div class="card card-body">
          <div class="media align-items-center align-items-lg-start text-center text-lg-left flex-column flex-lg-row" class="test">


            <div class="media-body">
              <h3 class="media-title font-weight-semibold">
                <%=p.getTitolo() %>
              </h3>

              <ul class="list-inline list-inline-dotted mb-3 mb-lg-2">
                <li class="list-inline-item"><h6>Pacchetto consigliato per eventi del tipo:</h6></li>
              </ul>

              <p class="mb-3">"<%=p.getEventiConsigliati()%>"</p>


            </div>

            <div class="mt-3 mt-lg-0 ml-lg-3 text-center">

              <h3 class="mb-0 font-weight-semibold">&euro; <%=p.getPrezzo() %>0</h3>

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
      <a href="pacchettiControl?action=delete&id=<%= p.getId()%>"><button type="submit" class="bottone_search2" class="btn btn-outline-success"><h6 class = "testoCarrello">Rimuovi</h6></button></a>
    </div>
  </div>
</div>
<%}%>
</body>
</html>
