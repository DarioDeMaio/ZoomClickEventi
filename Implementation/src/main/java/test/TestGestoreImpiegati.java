package test;

import org.junit.Before;
import org.junit.Test;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestGestoreImpiegati {

    private GestoreImpiegati gi;
    private Gestore g;
    @Before
    public void setUp(){
        gi = new GestoreImpiegati();
        g = new Gestore();
        g.setId(1);
        gi.addImpiegato(g);
    }

    @Test
    public void testAddImpiegatoTrue(){
        HashSet<Gestore> map = gi.getImpiegati();
        assertEquals(1, map.size());
    }

    @Test
    public void testAddImpiegatoFalse(){
        gi.addImpiegato(g);
        HashSet<Gestore> map = gi.getImpiegati();
        assertEquals(1, map.size());
    }

    @Test
    public void testDeleteImpiegatoTrue(){
        int id = 1;
        gi.removeImpiegato(id);
        HashSet<Gestore> map = gi.getImpiegati();
        assertEquals(0, map.size());
    }
    @Test
    public void testDeleteImpiegatoFalse(){
        int id = 0;
        gi.removeImpiegato(id);
        HashSet<Gestore> map = gi.getImpiegati();
        assertEquals(1, map.size());
    }
}
