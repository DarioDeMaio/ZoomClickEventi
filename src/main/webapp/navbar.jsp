<%@ page import="user.bean.Cliente" %>
<%@ page import="party.bean.Artista" %>
<%@ page import="user.bean.Gestore" %>
<%@ page import="user.bean.GestoreImpiegati" %>
<%@ page import="pacchetto.bean.GestorePacchetti" %>
<%@ page import="party.bean.GestoreParty" %>
<%@ page import="party.bean.Contabile" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  Object logged = session.getAttribute("logged");
  boolean loggedBool=false;

  if(logged!=null)
    loggedBool=true;

  Cliente c = null;
  Artista a = null;
  GestoreImpiegati gi = null;
  GestorePacchetti gPacchetti = null;
  GestoreParty gParty = null;
  Contabile contabile = null;

  if(session.getAttribute("utente") instanceof Cliente)
  {
    c = (Cliente) session.getAttribute("utente");
  }else if(session.getAttribute("utente") instanceof Artista)
  {
    a = (Artista) session.getAttribute("utente");
  }else if(session.getAttribute("utente") instanceof GestoreImpiegati){
    gi=(GestoreImpiegati) session.getAttribute("utente");
  }else if(session.getAttribute("utente") instanceof GestoreParty){
    gParty=(GestoreParty) session.getAttribute("utente");
  }else if(session.getAttribute("utente") instanceof GestorePacchetti){
    gPacchetti=(GestorePacchetti) session.getAttribute("utente");
  }else if(session.getAttribute("utente") instanceof Contabile){
    contabile=(Contabile) session.getAttribute("utente");
  }

  String nome=(String) request.getAttribute("nome");
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
            <a class="nav-link" href="header">Home <span class="sr-only"></span></a>
          </li>

          <% if(loggedBool==true){%>
          <li class="nav-item">
            <a href="logout" class="nav-link">Logout</a>
          </li>
          <% } %>
          <li class="nav-item dropdown">
            <% if(loggedBool==false){ %>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                  Accedi
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="login.jsp">Login</a>
              <a class="dropdown-item" href="registrazione.jsp">Registrati</a>
            </div>
            <% }else{
                if(c!=null){
            %>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                Account
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="PersonalInformationServlet?action=home">Profilo</a>
              <a class="dropdown-item" href="MiePrenotazioniServlet?from=Confermato">Mie prenotazioni</a>
              <a class="dropdown-item" href="MiePrenotazioniServlet?from=Incorso">Prenotazioni in corso</a>
            </div>
            <% }else if(a != null){ %>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                Visualizza
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Profilo</a>
              <a class="dropdown-item" href="#">Eventi Passati</a>
              <a class="dropdown-item" href="#">Eventi Futuri</a>
            </div>
            <% }else if(gi != null){%>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                Visualizza
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Profilo</a>
              <a class="dropdown-item" href="#">Impiegati</a>
              <a class="dropdown-item" href="#">Nuovo impiegato</a>
            </div>
            <% } else if(gPacchetti != null){ %>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                Visualizza
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Profilo</a>
              <a class="dropdown-item" href="#">Pacchetti</a>
              <a class="dropdown-item" href="#">Nuovo pacchetto</a>
            </div>
            <% } else if(gParty != null) {%>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                Visualizza
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Profilo</a>
              <a class="dropdown-item" href="#">Prenotazioni in corso</a>
            </div>
            <% } else if(contabile != null){  %>
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" onclick="funct()">
                Visualizza
              </a>
            <div class="dropdown-menu" aria-labelledby="navbarDropdown">
              <a class="dropdown-item" href="#">Profilo</a>
              <a class="dropdown-item" href="#">Eventi Passati</a>
              <a class="dropdown-item" href="#">Eventi Futuri</a>
            </div>
            <% } %>


            </div>
          </li>

        </ul>
  <% } %>

    </nav>

  </div>
</nav>

</body>
</html>
