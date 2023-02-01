package user.bean;

public class Gestore extends Utente{

    private String tipoGestore;

    public Gestore(String nome, String cognome, String email, String password, String telefono, String tipo, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipo);
        this.tipoGestore = tipoGestore;
    }

    public void setTipoGestore(String tipo){this.tipoGestore=tipo;}

    public String getTipoGestore(){ return this.tipoGestore;}
}
