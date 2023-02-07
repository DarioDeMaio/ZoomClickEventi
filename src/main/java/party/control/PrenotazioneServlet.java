package party.control;

import pacchetto.bean.GestorePacchetti;
import pacchetto.bean.Pacchetto;
import pacchetto.manager.PacchettoManager;
import party.bean.*;
import party.manager.PartyManager;
import user.bean.Cliente;
import user.bean.GestoreImpiegati;
import user.control.HeaderServlet;
import user.control.LoginServlet;
import user.manager.UserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.http.HttpRequest;
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

        if(action==null)
        {
            if(session.getAttribute("utente") instanceof Cliente)
                indirizzo=redirectToPrenotazione(request);
            else if(session.getAttribute("utente") instanceof GestoreParty)
                indirizzo=redirectToGestoreParty(request);
        } else if(action.compareTo("prenotazione")==0){
            indirizzo=prenotaParty(request);
        }else if(action.compareTo("conferma")==0){
            indirizzo=redirectToConfermaParty(request);
        }else if(action.compareTo("confermaParty")==0){
            indirizzo = confermaParty(request);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(indirizzo);
        rd.forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String redirectToConfermaParty(HttpServletRequest request)
    {
        HashSet<Artista> artisti = UserManager.retrieveAllArtisti();
        HashSet<Fornitore> fornitori = PartyManager.retrieveAllFornitori();
        String idParty = request.getParameter("idParty");
        request.setAttribute("idParty2", idParty);
        request.setAttribute("artisti", artisti);
        request.setAttribute("fornitori", fornitori);
        return "/confermaParty.jsp";
    }
    private String redirectToGestoreParty(HttpServletRequest request)
    {
        return "/gestoreParty.jsp";
    }
    private String redirectToPrenotazione(HttpServletRequest request)
    {
        double prezzo= Double.parseDouble(request.getParameter("prezzo"));
        String titolo = request.getParameter("titolo");
        int id = Integer.parseInt(request.getParameter("idPacchetto"));

        request.setAttribute("prezzo", prezzo);
        request.setAttribute("titolo", titolo);
        request.setAttribute("idPacchetto", id);

        return "/prenotazione.jsp";
    }

    private String prenotaParty(HttpServletRequest request)
    {
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

        Party party = new Party(tipoEvento, festeggiato, nomeLocale, citta, dateEvento, currentDateOnly, "In attesa", servizi, pacchetto, LoginServlet.id);
        PartyManager.prenotaParty(party);

        Cliente c = (Cliente) request.getSession().getAttribute("utente");
        c.addParty(party);

        return "/header";
    }

    private String confermaParty(HttpServletRequest request) {
        HttpSession session = request.getSession();
        GestoreParty gParty = (GestoreParty) session.getAttribute("utente");
        int idDj = 0, idFotografo = 0, idSpeaker = 0;
        double prezzoDj = 0, prezzoFotografo = 0, prezzoSpeaker = 0;
        if (request.getParameter("dj") != null) {
            String dj = request.getParameter("dj");
            idDj = Integer.parseInt(dj);
            prezzoDj = Double.parseDouble(request.getParameter("prezzoDj"));
        } else if (request.getParameter("fotografi") != null) {
            String fotografo = request.getParameter("fotografi");
            idFotografo = Integer.parseInt(fotografo);
            prezzoFotografo = Double.parseDouble(request.getParameter("prezzoFotografo"));
        } else if (request.getParameter("speaker") != null) {
            String speaker = request.getParameter("speaker");
            idSpeaker = Integer.parseInt(speaker);
            prezzoSpeaker = Double.parseDouble(request.getParameter("prezzoSpeaker"));
        }

        int idParty = Integer.parseInt(request.getParameter("idParty"));

        Party p = gParty.findById(idParty);

        HashSet<Artista> artisti = UserManager.retrieveAllArtisti();
        HashSet<Fornitore> fornitori = PartyManager.retrieveAllFornitori();

        for(Artista a:artisti)
        {
            if(a.getId()==idDj)
                p.addArtista(a, prezzoDj);
            else if(a.getId()==idFotografo)
                p.addArtista(a, prezzoFotografo);
            else if(a.getId()==idSpeaker)
                p.addArtista(a, prezzoSpeaker);
        }

        String[] idFornitori = request.getParameterValues("idFornitori");


        for(Fornitore f:fornitori){
            for(int i = 0; i<idFornitori.length; i++)
            if((Integer.parseInt(idFornitori[i])) == f.getId()) {
                p.addFornitore(f);
            }
        }
        p.setStato("Confermato");
        PartyManager.confermaParty(p);

        return "/header";
    }
}
