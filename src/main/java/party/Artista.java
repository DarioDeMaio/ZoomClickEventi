package party;

import user.Utente;

import java.util.HashMap;

public class Artista extends Utente {

    private String tipoArtista;
    private HashMap<Party, Double> party;

    public Artista(int telefono, String nome, String cognome, String email, String password, String tipoArtista) {
        super(telefono, nome, cognome, email, password);
        this.tipoArtista = tipoArtista;
        party = new HashMap<Party, Double>();
    }

    public HashMap<Party, Double> getParty() {
        return party;
    }

    public void setParty(HashMap<Party, Double> party) {
        this.party = party;
    }
    public String getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = tipoArtista;
    }
}
