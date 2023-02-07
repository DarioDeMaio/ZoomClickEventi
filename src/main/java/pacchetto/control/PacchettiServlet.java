package pacchetto.control;

import pacchetto.bean.GestorePacchetti;
import pacchetto.bean.Pacchetto;
import pacchetto.manager.PacchettoManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "pacchettiControl", value = "/pacchettiControl")

public class PacchettiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String action = request.getParameter("action");
        String indirizzo = "";

        if(action == null)
        {
            indirizzo = "/pacchetto/gestorePacchetti.jsp";
        }else if(action.compareTo("delete")==0)
        {
            indirizzo = delete(request);
        }else if(action.compareTo("insert")==0)
        {
            indirizzo = sendToInsert(request);
        }else if(action.compareTo("insertPacchetto")==0)
        {
            indirizzo = insert(request);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String delete(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        GestorePacchetti gp = (GestorePacchetti) session.getAttribute("utente");

        int id = Integer.parseInt(request.getParameter("id"));
        Pacchetto p = gp.findById(id);
        gp.deletePacchetto(p);
        PacchettoManager.deletePacchetto(id);
        return "/pacchetto/gestorePacchetti.jsp";
    }

    private String sendToInsert(HttpServletRequest request)
    {
        return "/pacchetto/newPacchetto.jsp";
    }
    private String insert(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        GestorePacchetti gp = (GestorePacchetti) session.getAttribute("utente");

        String titolo = request.getParameter("titolo");
        double prezzo = Double.parseDouble(request.getParameter("prezzoPacchetto"));
        String eventi = request.getParameter("eventi");

        Pacchetto p = new Pacchetto(titolo, eventi, prezzo, 1);

        PacchettoManager.insertPacchetto(p);

        gp.addPacchetto(p);

        return "/pacchetto/gestorePacchetti.jsp";
    }
}
