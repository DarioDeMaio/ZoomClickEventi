package user.control;

import pacchetto.bean.Pacchetto;
import pacchetto.manager.PacchettoManager;
import user.bean.Cliente;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashSet;

@WebServlet(name = "header", value = "/header")
public class HeaderServlet extends HttpServlet {
    public static HashSet<Pacchetto> catalogo = null;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Object logged = session.getAttribute("logged");
        boolean cliente = session.getAttribute("utente") instanceof Cliente;

        if(cliente || logged == null)
        {
            if(catalogo==null) {
                catalogo = PacchettoManager.retrieveAll();
            }
            request.setAttribute("catalogo", catalogo);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/user/HomePage.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
