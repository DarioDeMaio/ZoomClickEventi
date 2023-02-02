<%@ page import="user.bean.Cliente" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="party.bean.Party" %>
<%
    Cliente c = (Cliente) session.getAttribute("utente");
    Iterator<Party> it = c.getParties().iterator();
%>

<html>
<body>
    <h2><%=c.toString()%></h2>
    <button><a href="logout">Logout</a></button>
</body>
</html>
