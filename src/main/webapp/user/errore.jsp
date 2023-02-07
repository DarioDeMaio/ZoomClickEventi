<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	Object bool = request.getAttribute("bool");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error Page</title>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<style>

#div-errore {
	margin-left:20%;
	margin-right:20%;
	margin-top:5%;
}

#div-errore2 {
	margin-left:20%;
	margin-right:20%;
	margin-bottom:5%;
}

h2,h1,p{
text-align:center;
}

</style>
</head>
<body>

		<%if(bool != null){%>
			<div class="w3-container w3-purple" id="div-errore">
				<h1>ERRORE</h1>
			</div>
			<div class="w3-container w3-white" id="div-errore2">

			<h2>EMAIL O PASSWORD ERRATI</h2>
			<p>Ritorna a <a href="./login?action=send">LOGIN</a> o <a href="./registra?action=send">REGISTRAZIONE</a></p>
		</div>
		<%}else{%>
		<div class="w3-container w3-purple" id="div-errore">
  			<h1>ERRORE</h1> 
		</div>
		<div class="w3-container w3-white" id="div-errore2">

   			<h2>EMAIL GIÀ ESISTENTE!</h2>
		<p>Ritorna a <a href="./login?action=send">LOGIN</a> o <a href="./registra?action=send">REGISTRAZIONE</a></p>
		</div>
		<%}%>

</body>

</html>