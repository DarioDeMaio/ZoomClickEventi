package user.control;

import party.bean.Fornitore;
import party.bean.Party;
import user.bean.Cliente;
import user.manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

@WebServlet(name = "MiePrenotazioniServlet", value = "/MiePrenotazioniServlet")
public class MiePrenotazioniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromWhere = (String) request.getParameter("from");
        String indirizzo = "/Prenotazioni.jsp";
        HttpSession session = request.getSession();

        if(fromWhere.compareTo("Incorso")==0)
            fromWhere="In attesa";

        request.setAttribute("fromWhere",fromWhere);
        Cliente c = (Cliente) session.getAttribute("utente");
        HashSet<Party> party = c.getParties();
        HashMap<Party, Double> miePrenotazioni = getMap(party);


        request.setAttribute("miePrenotazioni", miePrenotazioni);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    private HashMap<Party, Double> getMap(HashSet<Party> party){
        HashMap<Party, Double> miePrenotazioni = new HashMap<>();
        for(Party p: party){
            double sum = p.getPacchetto().getPrezzo();
            for(Fornitore f: p.getFornitori().keySet()){
                sum += p.getFornitori().get(f);
            }
            miePrenotazioni.put(p,sum);
        }
        return miePrenotazioni;
    }
}
