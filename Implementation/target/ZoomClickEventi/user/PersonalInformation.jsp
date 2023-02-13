<%@ page import="user.bean.Utente" %><%--
  Created by IntelliJ IDEA.
  User: demai
  Date: 06/02/2023
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utente u = (Utente) session.getAttribute("utente");
%>
<html>
<head>
    <title>Informazioni Personali</title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<form action="PersonalInformationServlet?action=modifica" method="post" onsubmit="return validate(this)">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputEmail4">Email</label>
            <input type="email" name="email" class="form-control" id="inputEmail4" value="<%=u.getEmail()%>">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputAddress2">Password</label>
            <input type="text" name="psw" class="form-control" id="inputAddress2" value=<%=u.getPassword()%>>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="exampleFormControlTextarea1">Repeat Password</label>
            <input type="text" name="rPsw" class="form-control" id="inputAddress2" value=<%=u.getPassword()%>>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="exampleFormControlTextarea1">Telefono</label>
            <input type="text" name="numero" class="form-control" id="inputAddress2" value="<%=u.getTelefono()%>">
        </div>
    </div>
    <input type="submit" class="btn btn-primary" value="Modifica">
</form>
<script type="text/javascript" src="js/validationUpdateUser.js"></script>
</body>
</html>
