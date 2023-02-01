<%@ page import="user.bean.Cliente" %>
<%@ page import="party.bean.Artista" %><%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 01/02/2023
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  Cliente c = (Cliente) session.getAttribute("utente");
%>

<html>
<head>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" crossorigin="anonymous"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

  <style>

    @media (max-width:780px)
    {
      .benvenuto{
        display:none;
      }
    }

    .benvenuto{
      color:#ff00ff;
    }

    #bottone_search{
      color: white;
      font-size:16px;
      font-weight: bold;
      border: solid 1px purple;

    }

    #bottone_search:hover{
      background: purple;
    }

    #carrello{
      width:50px;
      height:50px;
    }

    #logo-responsive{
      width:110px;
      height:110px;
      border-radius: 50%;
    }

    nav{
      position:fixed;
      top:0;
      widht:100%;
    }

  </style>
</head>
<body>

<nav class="navbar navbar-light bg-dark">
  <div class="container-fluid" id="Ricerca">

    <h2 class="benvenuto">Zoom Click Eventi</h2>


    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">

      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
          <li class="nav-item">
            <a class="nav-link" href="${ pageContext.request.contextPath }/header">Home <span class="sr-only"></span></a>
          </li>

          <li class="nav-item">
            <a class="nav-link">Logout</a>
          </li>

          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
              Accedi
            </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="${ pageContext.request.contextPath }/login.jsp">Login</a>
              <a class="dropdown-item" href="${ pageContext.request.contextPath }/registrazione.jsp">Registrati</a>

            </div>
          </li>

        </ul>

      </div>
    </nav>

  </div>
</nav>

</body>
</html>
