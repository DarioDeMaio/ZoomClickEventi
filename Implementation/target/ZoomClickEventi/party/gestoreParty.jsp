<%@ page import="party.bean.Party" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 06/02/2023
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Gestore Party</title>

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
        body {
            background: radial-gradient(circle at top left, #F44336, #2196F3, #f5a02f);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;
        }

        #bottone_search2 {
            border: solid 1px purple;
            text-align: center;
            width:50%;
            align-content: center;
            margin-left:5%;
            margin-top: 20%;
            width:100px;
            height: 40px;
            margin-bottom: 3%;
            color:purple;

        }

        #bottone_search2 h6{
            margin-top: 5%;
        }

        #bottone_search2:hover {
            background: purple;
        }

        #bottone_search2:hover .testoCarrello{
            color: white;
        }

        .div-generale{
            margin-top:3%;
            margin-bottom:3%;
            margin-left:2%;
            margin-rigth:2%;
        }

        .linkServlet {
            color: inherit;
            text-decoration: none;
            transition: none;
        }
        .linkServlet:hover{
            color: inherit;
            text-decoration: none;
            transition: none;
        }

    </style>

</head>
<body>

<%@ include file="../user/navbar.jsp"%>
<h1 style="text-align: center; color: white">Party In attesa di riscontri</h1>
<%
    Iterator<Party> it = gParty.getParty().iterator();
    while(it.hasNext()) {
        Party p = it.next();
        if(p.getStato().compareTo("In attesa")==0){
%>
<a href="prenotazione?action=conferma&idParty=<%=p.getId()%>" class="linkServlet">
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
                        <h5 class="mb-3">Acquistato il <%=p.getDataPrenotazione() %> </h5>

                    </div>

                    <div class="mt-3 mt-lg-0 ml-lg-3 text-center">

                        <h3 class="mb-0 font-weight-semibold">&euro; <%=p.getPacchetto().getPrezzo()%>0</h3>

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
</a>
<%}
}%>

</body>
</html>
