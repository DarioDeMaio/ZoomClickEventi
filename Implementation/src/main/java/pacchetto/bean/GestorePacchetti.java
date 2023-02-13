package pacchetto.bean;

import user.bean.Gestore;

import java.util.HashSet;
import java.util.Iterator;

public class GestorePacchetti extends Gestore {

    private HashSet<Pacchetto> pacchetti;

    public GestorePacchetti(String nome, String cognome, String email, String password, String telefono, String tipoGestore) {
        super(nome, cognome, email, password, telefono, tipoGestore);
        pacchetti = new HashSet<Pacchetto>();
    }

    public GestorePacchetti(){
        pacchetti = new HashSet<>();
    }

    public HashSet<Pacchetto> getPacchetti() {
        return pacchetti;
    }

    public void setPacchetti(HashSet<Pacchetto> pacchetti) {
        this.pacchetti = pacchetti;
    }

    //metodi di business

    public void addPacchetto(Pacchetto p){
        if(!pacchetti.contains(p))
            pacchetti.add(p);
    }
    public void deletePacchetto(int idPacchetto){
        Pacchetto p = findById(idPacchetto);
        if(p!=null)
        {
            if(pacchetti.contains(p))
                pacchetti.remove(p);
        }
    }

    private Pacchetto findById(int id)
    {
        Iterator<Pacchetto> it = pacchetti.iterator();
        while(it.hasNext())
        {
            Pacchetto p = it.next();
            if(p.getId()==id)
                return p;
        }
        return null;
    }

}
