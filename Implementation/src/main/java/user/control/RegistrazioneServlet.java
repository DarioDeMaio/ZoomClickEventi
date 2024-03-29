package user.control;

import user.bean.Utente;
import user.manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "registra", value = "/registra")
public class RegistrazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action != null && action.compareTo("send")==0)
        {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/user/registrazione.jsp");
            rd.forward(request, response);
        }

        String nome= request.getParameter("nome");
        String cognome= request.getParameter("cognome");
        String psw= request.getParameter("password");
        String email= request.getParameter("email");
        String telefono= request.getParameter("telefono");

        String indirizzo="";

        if(UserManager.checkIdByEmail(email))
        {
            Utente u = UserManager.insertUser(nome, cognome, email, psw, telefono);
            UserManager.insertCliente(u);
            indirizzo = "/login";
            request.setAttribute("email", email);
            request.setAttribute("password", u.getPassword());
        }else{
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user/errore.jsp");
            dispatcher.forward(request, response);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(indirizzo);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
