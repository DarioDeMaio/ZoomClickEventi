package user;

import java.util.HashSet;
import java.util.Set;

public class GestoreImpiegati extends Gestore{

    private HashSet<Gestore> impiegati;

    public GestoreImpiegati(int telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password, tipoGestore);
        impiegati = new HashSet<>();
    }


    public HashSet<Gestore> getImpiegati() {

        return impiegati;
    }

    public void setImpiegati(HashSet<Gestore> impiegati) {

        this.impiegati = impiegati;
    }
}
