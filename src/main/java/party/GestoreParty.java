package party;

import pacchetto.Pacchetto;
import user.Gestore;

import java.util.HashSet;

public class GestoreParty extends Gestore {

    private HashSet<Party> party;

    public GestoreParty(int telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password, tipoGestore);
        party = new HashSet<Party>();
    }


    public HashSet<Party> getParty() {
        return party;
    }

    public void setParty(HashSet<Party> party) {
        this.party = party;
    }

}
