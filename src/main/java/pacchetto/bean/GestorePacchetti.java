package pacchetto.bean;

import user.bean.Gestore;

import java.util.HashSet;

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
