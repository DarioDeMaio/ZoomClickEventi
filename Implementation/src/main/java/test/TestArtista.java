package test;


import org.junit.Before;
import org.junit.Test;
import pacchetto.bean.Pacchetto;
import party.bean.Artista;
import party.bean.Party;

import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class TestArtista {
    private Party p;
    private double prezzo;
    private Artista a;
    @Before
    public void setUp(){
        p = new Party("18esimi", "Dario", "Tales pub", "Solofra", new Date(), new Date(), "Confermato", "niente", new Pacchetto(), 2);
        prezzo = 50;
        a = new Artista();
        a.addParty(p, prezzo);
    }
    @Test
    public void addPartyTestTrue(){
        HashMap<Party, Double> parties = a.getParties();
        assertEquals(1, parties.size());
    }

    @Test
    public void addPartyTestFalse(){
        //Testiamo se lo contiene già che succede
        a.addParty(p,prezzo);
        HashMap<Party, Double> parties = a.getParties();
        assertEquals(1, parties.size());
    }

}
