package party.bean;

import user.bean.Gestore;

import java.util.HashSet;

public class Contabile extends Gestore {

    private HashSet<Party> party;

    public Contabile(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipoGestore);
        party = new HashSet<>();
    }


    public HashSet<Party> getParty() {
        return party;
    }

    public void setParty(HashSet<Party> party) {
        this.party = party;
    }

}
