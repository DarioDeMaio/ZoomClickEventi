package user.bean;

import party.bean.Party;

import java.util.HashSet;

public class Cliente extends Utente{

    private HashSet<Party> parties;
    public Cliente(String nome, String cognome, String email, String password, String telefono) {
        super(nome, cognome, email, password, telefono);
    }

    public HashSet<Party> getPartiesInCorso() {
        return parties;
    }

    public HashSet<Party> getParties() {
        return parties;
    }

    public void setParties(HashSet<Party> parties) {
        this.parties = parties;
    }

    public void addParty(Party p)
    {
        if(!parties.contains(p))
            parties.add(p);
    }

    public void deleteParty(Party p)
    {
        if(parties.contains(p))
            parties.remove(p);
    }
}
