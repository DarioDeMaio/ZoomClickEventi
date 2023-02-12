package user.control;

import party.bean.Artista;
import user.bean.Contabile;
import party.bean.Party;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;
import user.bean.Utente;
import user.manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@WebServlet(name = "EmployeeServlet", value = "/EmployeeServlet")
public class EmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String indirizzo=null;
        if(action.compareTo("eventiPassati")==0){
            HashMap<Party, Double> map = redirectToEventiFuturi(request);
            request.setAttribute("bool", false);
            request.setAttribute("map", map);
            indirizzo = "/party/Artista.jsp";
        }else if(action.compareTo("eventiFuturi")==0){
            HashMap<Party, Double> map = redirectToEventiParty(request);
            request.setAttribute("map", map);
            indirizzo = "/party/Artista.jsp";
        }else if(action.compareTo("insertGestore")==0){
            indirizzo = "/user/GestoreImpiegati.jsp";
        }else if(action.compareTo("listDelete")==0){
            GestoreImpiegati gi = (GestoreImpiegati) session.getAttribute("utente");
            indirizzo = "/user/ListImpiegati.jsp";
        }else if(action.compareTo("add")==0){
            indirizzo = redirectToInserGestore(request);
        }else if(action.compareTo("delete")==0){
            indirizzo = redirectToDelete(request);
        }else if(action.compareTo("incassi")==0) {
            indirizzo = incassi(request);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    private String redirectToDelete(HttpServletRequest request) {
        HttpSession session = request.getSession();
        GestoreImpiegati gi = (GestoreImpiegati) session.getAttribute("utente");
        String id = request.getParameter("idImpiegato");
        int idImpiegato = Integer.parseInt(id);
        gi.removeImpiegato(idImpiegato);
        System.out.println(idImpiegato);
        UserManager.deleteUser(idImpiegato);
        return "/EmployeeServlet?action=listDelete";
    }

    private String redirectToInserGestore(HttpServletRequest request) {
        HttpSession session = request.getSession();
        GestoreImpiegati gi = (GestoreImpiegati) session.getAttribute("utente");
        String nome= request.getParameter("nome");
        String cognome= request.getParameter("cognome");
        String psw= request.getParameter("password");
        String email= request.getParameter("email");
        String telefono= request.getParameter("telefono");
        String tipo = request.getParameter("tipo");

        if(UserManager.checkIdByEmail(email)) {
            Utente u = new Utente(nome, cognome, email, psw, telefono);
            Gestore g = UserManager.insertGestore(u,tipo);
            gi.addImpiegato(g);

        }else
            return "/user/errore.jsp";


        return "/header";
    }

    private HashMap<Party, Double> redirectToEventiParty(HttpServletRequest request) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDateOnly = calendar.getTime();

        HttpSession session = request.getSession();
        Artista a = (Artista) session.getAttribute("utente");
        HashMap<Party, Double> map = new HashMap<>();

        for(Party p: a.getParties().keySet()){
            if(currentDateOnly.before(p.getData()))
                map.put(p, a.getParties().get(p));
        }
        return map;
    }

    private HashMap<Party, Double> redirectToEventiFuturi(HttpServletRequest request) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDateOnly = calendar.getTime();

        HttpSession session = request.getSession();
        Artista a = (Artista) session.getAttribute("utente");
        HashMap<Party, Double> map = new HashMap<>();
        for(Party p: a.getParties().keySet()){
            if(currentDateOnly.after(p.getData()))
                map.put(p, a.getParties().get(p));
        }
        return map;
    }

    private String incassi(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        Contabile c = (Contabile) session.getAttribute("utente");

        HashMap<Party, Double> incassi = new HashMap<Party, Double>();
        for(Party p: c.getParty()){
            double amount = p.getPacchetto().getPrezzo();

            for(Artista a: p.getArtisti().keySet()){
                amount -= p.getArtisti().get(a);
            }
            incassi.put(p,amount);
        }

        request.setAttribute("parties", incassi);

        return "/party/incassi.jsp";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
