package user.control;

import party.bean.Artista;
import party.bean.Party;
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
        String action = request.getParameter("action");
        String indirizzo=null;
        if(action.compareTo("eventiPassati")==0){
            HashMap<Party, Double> map = redirectToEventiFuturi(request);
            request.setAttribute("bool", false);
            request.setAttribute("map", map);
            indirizzo = "/Artista.jsp";
        }else if(action.compareTo("eventiFuturi")==0){
            HashMap<Party, Double> map = redirectToEventiParty(request);
            request.setAttribute("map", map);
            indirizzo = "/Artista.jsp";
        }else if(action.compareTo("insertGestore")==0){
            indirizzo = "/GestoreImpiegati.jsp";
        }else if(action.compareTo("listDelete")==0){
            indirizzo = "/ListImpiegati.jsp";
        }else if(action.compareTo("add")==0){
            indirizzo = redirectToInserGestore(request);
        }else if(action.compareTo("delete")==0){
            indirizzo = redirectToDelete(request);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    private String redirectToDelete(HttpServletRequest request) {

        return "/header";
    }

    private String redirectToInserGestore(HttpServletRequest request) {
        String nome= request.getParameter("nome");
        String cognome= request.getParameter("cognome");
        String psw= request.getParameter("password");
        String email= request.getParameter("email");
        String telefono= request.getParameter("telefono");
        String tipo = request.getParameter("tipo");

        if(UserManager.checkIdByEmail(email)) {
            Utente u = UserManager.insertUser(nome, cognome, email, psw, telefono);
            UserManager.insertGestore(u,tipo);
        }else
            return "/errore.jsp";


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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
