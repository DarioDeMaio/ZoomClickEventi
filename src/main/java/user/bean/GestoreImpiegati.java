package user.bean;

import party.bean.Artista;

import party.Artista;

import java.util.HashSet;

public class GestoreImpiegati extends Gestore{

    private HashSet<Gestore> impiegati;
    private HashSet<Artista> artisti;

    public GestoreImpiegati(String telefono, String nome, String cognome, String email, String password, String tipoGestore) {
        super(telefono, nome, cognome, email, password, tipoGestore);
        impiegati = new HashSet<>();
    }


    public HashSet<Gestore> getImpiegati() {

        return impiegati;
    }

    public void setImpiegati(HashSet<Gestore> impiegati) {

        this.impiegati = impiegati;
    }

    public HashSet<Artista> getArtisti() {

        return artisti;
    }

    public void setArtisti(HashSet<Artista> artisti) {

        this.artisti = artisti;
    }
}
