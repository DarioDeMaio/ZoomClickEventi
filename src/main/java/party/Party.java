package party;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import pacchetto.Pacchetto;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Party {
    private int id;
    private String tipo, nomeLocale;
    private Date data, dataPrenotazione;
    private String stato;
    private double prezzoPacchetto;
    private HashMap<Artista, Double> artisti;
    private HashMap<Fornitore, Double> fornitori;

    public Party(String tipo, String nomeLocale, Date data, Date dataPrenotazione, String stato, double prezzoPacchetto) {
        this.tipo = tipo;
        this.nomeLocale = nomeLocale;
        this.data = data;
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
        this.prezzoPacchetto = prezzoPacchetto;
        this.artisti = new HashMap<Artista, Double>();
        this.fornitori = new HashMap<Fornitore, Double>();
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

    public double getPrezzoPacchetto() {
        return prezzoPacchetto;
    }

    public void setPrezzoPacchetto(double prezzoPacchetto) {
        this.prezzoPacchetto = prezzoPacchetto;
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
}
