package test;

import org.junit.Before;
import org.junit.Test;
import party.bean.Artista;
import party.bean.Fornitore;
import party.bean.Party;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestParty {

    private Party p;
    private Artista a;
    private Fornitore f;
    @Before
    public void setUp(){
        p = new Party();
        a = new Artista();
        f = new Fornitore();
        p.addArtista(a,300);
        p.addFornitore(f);
    }

    @Test
    public void testAddArtistaTrue(){
        HashMap<Artista, Double> artisti = p.getArtisti();
        assertEquals(1, artisti.size());
    }
    @Test
    public void testAddArtistaFalse(){
        p.addArtista(a,300);
        HashMap<Artista, Double> artisti = p.getArtisti();
        assertEquals(1, artisti.size());
    }

    @Test
    public void testAddFornitoreTrue(){
        HashSet<Fornitore> fornitori = p.getFornitori();
        assertEquals(1, fornitori.size());
    }
    @Test
    public void testAddFornitoreFalse(){
        p.addFornitore(f);
        HashSet<Fornitore> fornitori = p.getFornitori();
        assertEquals(1, fornitori.size());
    }

}
