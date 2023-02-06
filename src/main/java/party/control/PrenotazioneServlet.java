package party.control;

import pacchetto.bean.Pacchetto;
import pacchetto.manager.PacchettoManager;
import party.bean.Party;
import party.manager.PartyManager;
import user.bean.Cliente;
import user.control.HeaderServlet;
import user.control.LoginServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

@WebServlet(name = "prenotazione", value = "/prenotazione")
public class PrenotazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String indirizzo="";
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if(session.getAttribute("logged") == null){
            indirizzo = "/login.jsp";
            RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
            rd.forward(request, response);
        }

        if(action==null){
            double prezzo= Double.parseDouble(request.getParameter("prezzo"));
            String titolo = request.getParameter("titolo");
            int id = Integer.parseInt(request.getParameter("idPacchetto"));

            request.setAttribute("prezzo", prezzo);
            request.setAttribute("titolo", titolo);
            request.setAttribute("idPacchetto", id);
            indirizzo="/prenotazione.jsp";
        }else{
            String tipoEvento = request.getParameter("tipoEvento");
            String festeggiato = request.getParameter("festeggiato");
            String nomeLocale = request.getParameter("locale");
            String citta = request.getParameter("citta");
            String dataEvento = request.getParameter("dataEvento");
            Date dateEvento;
            try {
                dateEvento=new SimpleDateFormat("yyyy-mm-dd").parse(dataEvento);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date currentDateOnly = calendar.getTime();


            String servizi = request.getParameter("servizi");
            int idPacchetto = Integer.parseInt(request.getParameter("idPacchetto"));

            Pacchetto pacchetto= PacchettoManager.findPacchettoById(idPacchetto);


            Party party = new Party(tipoEvento, festeggiato, nomeLocale, citta, dateEvento, currentDateOnly, "In corso", servizi, pacchetto, LoginServlet.id);
            PartyManager.prenotaParty(party);

            Cliente c = (Cliente) request.getSession().getAttribute("utente");
            c.addParty(party);
            indirizzo = "/header";
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
