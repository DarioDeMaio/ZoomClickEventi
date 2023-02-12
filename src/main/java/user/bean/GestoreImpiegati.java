package user.bean;

import party.bean.Artista;

import party.bean.Artista;
import party.bean.GestoreParty;

import java.util.HashSet;

public class GestoreImpiegati extends Gestore{

    private HashSet<Gestore> impiegati;
    private HashSet<Artista> artisti;

    public GestoreImpiegati(){
        impiegati = new HashSet<Gestore>();
    }
    public GestoreImpiegati(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipoGestore);
        impiegati = new HashSet<>();
        artisti = new HashSet<>();
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

    //metodi di business

    public void addImpiegato(Gestore g){
        if(!impiegati.contains(g))
            impiegati.add(g);
    }

    public void removeImpiegato(int id){
       Gestore g = findGestoreById(id);
       if(g != null)
        impiegati.remove(g);
    }

    private Gestore findGestoreById(int id){
        for(Gestore g : impiegati)
            if(g.getId()==id)
                return g;
        return null;
    }




}
