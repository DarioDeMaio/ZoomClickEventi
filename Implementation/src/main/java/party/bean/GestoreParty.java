package party.bean;

import user.bean.Gestore;

import javax.servlet.http.Part;
import java.util.HashSet;
import java.util.Iterator;

public class GestoreParty extends Gestore {

    private HashSet<Party> parties;


    public GestoreParty(){
        parties = new HashSet<>();
    }
    public GestoreParty(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipoGestore);
        parties = new HashSet<Party>();
    }

    public HashSet<Party> getParty() {
        return parties;
    }

    public void setParty(HashSet<Party> party) {
        this.parties = party;
    }

    public Party findById(int idParty)
    {
        Iterator<Party> it = parties.iterator();
        while(it.hasNext()){
            Party p = it.next();
            if(p.getId()==idParty)
                return p;
        }
        return null;
    }
}
