package pacchetto;

import user.Gestore;
import user.Utente;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GestorePacchetti extends Gestore {

    private HashSet<Pacchetto> pacchetti;

    public GestorePacchetti(String telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password, tipoGestore);
        pacchetti = new HashSet<Pacchetto>();
    }


    public HashSet<Pacchetto> getPacchetti() {

        return pacchetti;
    }

    public void setPacchetti(HashSet<Pacchetto> pacchetti) {

        this.pacchetti = pacchetti;
    }
}
