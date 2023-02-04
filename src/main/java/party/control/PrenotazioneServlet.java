package party.control;

import pacchetto.bean.Pacchetto;
import party.bean.Party;
import party.manager.PartyManager;
import user.bean.Cliente;
import user.control.HeaderServlet;
import user.control.LoginServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;

@WebServlet(name = "prenotazione", value = "/prenotazione")
public class PrenotazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String indirizzo="";
        double prezzo= Double.parseDouble(request.getParameter("prezzo"));
        String titolo = request.getParameter("titolo");
        String action = request.getParameter("prenotazione");

        if(action==""){
            request.setAttribute("prezzo", prezzo);
            request.setAttribute("titolo", titolo);
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

            ZoneId defaultZoneId = ZoneId.systemDefault();
            LocalDate todaysDate = LocalDate.now();
            Date localDate=null;
            localDate.setDate(todaysDate.getDayOfMonth());
            localDate.setYear(todaysDate.getYear());
            localDate.setMonth(todaysDate.getMonthValue());

            String servizi = request.getParameter("servizi");
            int idPacchetto = Integer.parseInt(request.getParameter("idPacchetto"));

            Pacchetto pacchetto=null;
            Iterator<Pacchetto> it = HeaderServlet.catalogo.iterator();
            while(it.hasNext())
            {
                pacchetto = it.next();
                if(idPacchetto == pacchetto.getId()) {
                    break;
                }
            }

            Party party = new Party(tipoEvento, festeggiato, nomeLocale, citta, dateEvento, localDate, "In corso", servizi, pacchetto, LoginServlet.id);
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
