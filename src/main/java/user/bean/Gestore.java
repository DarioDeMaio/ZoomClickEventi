package user.bean;

public class Gestore extends Utente{

    private String tipoGestore;

    public Gestore(){

    }
    public Gestore(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono);
        this.tipoGestore = tipoGestore;
    }

    public void setTipoGestore(String tipo){this.tipoGestore=tipo;}

    public String getTipoGestore(){ return this.tipoGestore;}
}
