package party.bean;

import pacchetto.bean.Pacchetto;

import java.util.Date;
import java.util.HashMap;

public class Party {
    private int id;
    private String tipo, nomeLocale, citta;
    private Date data, dataPrenotazione;
    private String stato, servizi;
    private Pacchetto pacchetto;
    private HashMap<Artista, Double> artisti;
    private HashMap<Fornitore, Double> fornitori;

    public Party(String tipo, String nomeLocale, String citta, Date data, Date dataPrenotazione, String stato, String servizi, Pacchetto pacchetto) {
        this.tipo = tipo;
        this.nomeLocale = nomeLocale;
        this.citta = citta;
        this.data = data;
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
        this.servizi = servizi;
        this.artisti = new HashMap<>();
        this.fornitori = new HashMap<>();
        this.pacchetto = pacchetto;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getServizi() {
        return servizi;
    }

    public void setServizi(String servizi) {
        this.servizi = servizi;
    }

    public Pacchetto getPacchetto() {
        return pacchetto;
    }

    public void setPacchetto(Pacchetto pacchetto) {
        this.pacchetto = pacchetto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomeLocale() {
        return nomeLocale;
    }

    public void setNomeLocale(String nomeLocale) {
        this.nomeLocale = nomeLocale;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(Date dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public HashMap<Artista, Double> getArtisti() {
        return artisti;
    }

    public void setArtisti(HashMap<Artista, Double> artisti) {
        this.artisti = artisti;
    }

    public HashMap<Fornitore, Double> getFornitori() {
        return fornitori;
    }

    public void setFornitori(HashMap<Fornitore, Double> fornitori) {
        this.fornitori = fornitori;
    }

    public void addArtista(Artista a, double prezzo) {
        if (!artisti.containsKey(a)) {
            artisti.put(a, prezzo);
            a.addParty(this, prezzo);
        }
    }

    public void addFornitore(Fornitore f, double prezzo)
    {
        if(!fornitori.containsKey(f))
            fornitori.put(f, prezzo);
    }
}
