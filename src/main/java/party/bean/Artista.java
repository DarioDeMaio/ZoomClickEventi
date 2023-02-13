package party.bean;

import user.bean.Utente;

import java.util.HashMap;
import java.util.Iterator;

public class Artista extends Utente {

    private String tipoArtista;
    private HashMap<Party, Double> parties;

    public Artista(){
        parties = new HashMap<>();
    }
    public Artista(String nome, String cognome, String email, String password, String telefono, String tipoArtista) {
        super(nome, cognome, email, password, telefono);
        this.tipoArtista = tipoArtista;
        parties = new HashMap<Party, Double>();
    }

    public HashMap<Party, Double> getParties() {
        return parties;
    }

    public void setParties(HashMap<Party, Double> party) {
        this.parties = party;
    }
    public String getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = tipoArtista;
    }

    public void addParty(Party p, double prezzo)
    {
        if((p!=null) && (!parties.containsKey(p)))
        {
            parties.put(p, prezzo);
            p.addArtista(this, prezzo);
        }
    }
}
