package party.control;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "prenotazione", value = "/prenotazione")
public class PrenotazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double prezzo= Double.parseDouble(request.getParameter("prezzo"));
        String titolo = request.getParameter("titolo");

        request.setAttribute("prezzo", prezzo);
        request.setAttribute("titolo", titolo);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/prenotazione.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
