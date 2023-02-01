package user.control;

import party.bean.Artista;
import party.bean.Party;
import party.manager.PartyManager;
import user.bean.Cliente;
import user.bean.Gestore;
import user.bean.Utente;
import user.manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;

import static user.manager.UserManager.isArtista;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    public static int id=0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email="", psw="";
        if(request.getParameter("email")!=null) {
            email = request.getParameter("email");
            psw = request.getParameter("password");
        }else {
            email = (String) request.getAttribute("email");
            psw = (String) request.getAttribute("password");
        }

        String indirizzo="";
        HttpSession session = request.getSession();

        Utente u = (Utente) UserManager.login(email, psw);

        if(u==null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("errore");
            rd.forward(request, response);
        }

        id=u.getId();

        if(u.getTipo().compareTo("c")==0)
        {
            Cliente c = (Cliente) u;
            session.setAttribute("utente", c);
            indirizzo = "cliente";
        }else if(u.getTipo().compareTo("a")==0)
        {
            //quì se è un artista gli carichiamo subito tutti i party a cui ha partecipato
            Artista a = UserManager.isArtista(u);
            HashMap<Party, Double> parties = PartyManager.retrievePartyByIdArtista(id);
            a.setParties(parties);
            session.setAttribute("utente", a);
            indirizzo = "artista";
        }else{
            //qui se è un gestore vediamo che tipo di gestore è, così da caricargli subito le varie liste
            Gestore g = (Gestore) UserManager.isGestore(u);
            session.setAttribute("utente", g);
            indirizzo = "gestore";
        }

        /*RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);*/

        System.out.println(indirizzo);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
