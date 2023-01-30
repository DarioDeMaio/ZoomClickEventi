package party.bean;

import user.bean.Gestore;

import java.util.HashSet;

public class Contabile extends Gestore {

    private HashSet<Party> parties;

    public Contabile(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipoGestore);
        parties = new HashSet<>();
    }


    public HashSet<Party> getParty() {
        return parties;
    }

    public void setParty(HashSet<Party> party) {
        this.parties = party;
    }

}
