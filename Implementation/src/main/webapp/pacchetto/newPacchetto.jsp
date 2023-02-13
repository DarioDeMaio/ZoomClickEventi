<%--
  Created by IntelliJ IDEA.
  User: 122nl
  Date: 07/02/2023
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nuovo Pacchetto</title>
</head>
<body>
<%@ include file="../user/navbar.jsp" %>
<form action="pacchettiControl?action=insertPacchetto" method="post" onsubmit="return validate(this)">
  <div class="form-row">
    <div class="form-group col-md-6">
      <label for="inputEmail4">Titolo Pacchetto</label>
      <input type="text" name="titolo" class="form-control" id="inputEmail4" placeholder="Dj-Fotografo / Dj-Speaker / Dj" style="text-transform:capitalize;" required >
    </div>
    <div class="form-group col-md-6">
      <label for="inputPassword4">Prezzo</label>
      <input type="number" name="prezzoPacchetto" class="form-control" id="inputPassword4" placeholder="€ 00.00" style="text-transform:capitalize;" required >
    </div>
  </div>
  <div class="form-row">
    <div class="form-group col-md-6">
      <label for="inputAddress2">Eventi Consigliati</label>
      <input type="text" name="eventi" class="form-control" id="inputAddress2" placeholder="18 esimi / matrimoni / laurea" required >
    </div>
  </div>

  <input type="submit" class="btn btn-primary" value="Inserisci">
</form>
<script type="text/javascript" src="js/validationPacchetti.js"></script>
</body>
</html>
