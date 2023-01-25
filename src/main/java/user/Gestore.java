package user;

public class Gestore extends Utente{

    private String tipoGestore;

    public Gestore(String telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password);
        this.tipoGestore = tipoGestore;
    }

    public void setTipoGestore(String tipo){this.tipoGestore=tipo;}

    public String getTipoGestore(){ return this.tipoGestore;}
}
