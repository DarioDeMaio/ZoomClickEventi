package user.control;

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
        String indirizzo;
        HttpSession session = request.getSession();
        if(fromWhere.compareTo("Incorso")==0)
            fromWhere="In attesa";

        request.setAttribute("fromWhere",fromWhere);
        indirizzo = "/Prenotazioni.jsp";
        Cliente c = (Cliente) session.getAttribute("utente");
        //HashSet<Party> miePrenotazioni = ((HashSet<Party>)c.getParties());
        HashMap<Party, Double> miePrenotazioni = UserManager.findPartyClientById(c.getId());
        request.setAttribute("miePrenotazioni", miePrenotazioni);
        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
