package party.bean;

import user.bean.Gestore;

import javax.servlet.http.Part;
import java.util.HashSet;
import java.util.Iterator;

public class GestoreParty extends Gestore {

    private HashSet<Party> party;

    public GestoreParty(String telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password, tipoGestore);
        party = new HashSet<Party>();
    }


    public HashSet<Party> getParty() {
        return party;
    }

    public void setParty(HashSet<Party> party) {
        this.party = party;
    }

    public HashSet<Party> retrievePartyInAttesa(){
        HashSet<Party> inAttesa = new HashSet<>();
        for(Party p : party)
            if(p.getStato().equals("In attesa"))
                inAttesa.add(p);
        return inAttesa;
    }

    public HashSet<Party> retrievePartyConfermati(){
        HashSet<Party> confermati = new HashSet<>();
        for(Party p : party)
            if(p.getStato().equals("Confermato"))
                confermati.add(p);
        return confermati;
    }

}
