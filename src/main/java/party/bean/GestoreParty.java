package party.bean;

import user.bean.Gestore;

import javax.servlet.http.Part;
import java.util.HashSet;
import java.util.Iterator;

public class GestoreParty extends Gestore {

    private HashSet<Party> party;

    public GestoreParty(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipoGestore);
        party = new HashSet<Party>();
    }

    public HashSet<Party> getParty() {
        return party;
    }

    public void setParty(HashSet<Party> party) {
        this.party = party;
    }
}
