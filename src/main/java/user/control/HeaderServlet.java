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
    public int start=0;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object daLogout = request.getAttribute("daLogout");
        boolean flag=false;

        if(daLogout!=null)
            flag=true;

        HttpSession session = request.getSession();
        if((session.getAttribute("utente") instanceof Cliente) || (flag == true) || (start==0))
        {

            start=1;
            if(catalogo==null) {
                System.out.println("sono qui");
                catalogo = PacchettoManager.retrieveAll();
            }
            request.setAttribute("catalogo", catalogo);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/HomePage.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
