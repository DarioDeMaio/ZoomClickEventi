package user.control;

import user.bean.Cliente;
import user.bean.Utente;
import user.manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "PersonalInformationServlet", value = "/PersonalInformationServlet")
public class PersonalInformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        String indirizzo = null;

        if(action.compareTo("home")==0){
            indirizzo = "/PersonalInformation.jsp";
        }else {
            indirizzo = modificaInformazioni(request);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    private String modificaInformazioni(HttpServletRequest request){

        HttpSession session = request.getSession();
        String email = (String) request.getParameter("email");
        String psw = (String) request.getParameter("psw");
        String repeatPsw = (String) request.getParameter("rPsw");
        String numero = (String) request.getParameter("numero");

        if(repeatPsw.compareTo(psw)==0){
            Cliente c = (Cliente) session.getAttribute("utente");
            c.setEmail(email);
            c.setTelefono(numero);
            c.setPassword(psw);
            UserManager.updateUser(c);
        }else
            return "/errore.jsp";

        return "/header";
    }

}
