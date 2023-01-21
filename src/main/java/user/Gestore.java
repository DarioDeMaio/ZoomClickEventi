package user;

public class Gestore extends Utente{

    private String tipoGestore;

    public Gestore(int telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password);
        this.tipoGestore = tipoGestore;
    }
}
