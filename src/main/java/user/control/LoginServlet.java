package user.control;

import pacchetto.bean.GestorePacchetti;
import pacchetto.manager.PacchettoManager;
import party.bean.Artista;
import party.bean.Contabile;
import party.bean.GestoreParty;
import party.bean.Party;
import party.manager.PartyManager;
import user.bean.Cliente;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;
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

@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {
    public static int id=0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email="", psw="";
        if(request.getParameter("email")!=null) {
            email = request.getParameter("email");
            psw = request.getParameter("password");
        }else{
            email = (String) request.getAttribute("email");
            psw = (String) request.getAttribute("password");
        }

        String indirizzo="";
        HttpSession session = request.getSession();

        Utente u = UserManager.login(email, psw);

        if(u==null) {
            request.setAttribute("bool", true);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/errore.jsp");
            rd.forward(request, response);
        }

        id=u.getId();

        if(UserManager.isCliente(u) != null)
        {
            //setto la collection di parties del cliente
            Cliente c = UserManager.isCliente(u);
            c.setParties(PartyManager.findPartyByIdCliente(id));
            session.setAttribute("utente", c);
            request.setAttribute("nome", c.getNome());
        }else if(UserManager.isArtista(u) != null)
        {
            //quì se è un artista gli carichiamo subito tutti i party a cui ha partecipato
            Artista a = UserManager.isArtista(u);
            HashMap<Party, Double> parties = PartyManager.retrievePartyByIdArtista(id);
            //System.out.println(parties.size());
            a.setParties(parties);
            session.setAttribute("utente", a);
        }else{
            //qui se è un gestore vediamo che tipo di gestore è, così da caricargli subito le varie liste
            Gestore g = (Gestore) UserManager.isGestore(u);
            indirizzo = "/gestore";
            if(g.getTipoGestore().compareTo("contabile") == 0) {
                Contabile c = new Contabile(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                c.setId(g.getId());
                //setto i party per il contabile cosicchè possa visualizzare incassi ecc
                c.setParty(PartyManager.retrieveAllParty());
                session.setAttribute("utente", c);
            }else if(g.getTipoGestore().compareTo("party") == 0) {
                GestoreParty gp = new GestoreParty(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                gp.setId(g.getId());
                //setto i party per il gestore Party cosicché possa accetarli o meno
                gp.setParty(PartyManager.retrieveAllParty());
                session.setAttribute("utente", gp);
            }else if(g.getTipoGestore().compareTo("pacchetti") == 0) {
                GestorePacchetti gp = new GestorePacchetti(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                gp.setId(g.getId());
                //setto i pacchetti in modo tale che questo gestore possa fare operazioni su di essi
                gp.setPacchetti(PacchettoManager.retrieveAll());
                session.setAttribute("utente", gp);
            }else{
                GestoreImpiegati gi = new GestoreImpiegati(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                gi.setId(g.getId());
                //setto gli impiegati: artisti e gestori
                gi.setArtisti(UserManager.retrieveAllArtisti());
                gi.setImpiegati(UserManager.retrieveAllEmployee());
                session.setAttribute("utente", gi);
            }
        }

        session.setAttribute("logged",true);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/header");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
