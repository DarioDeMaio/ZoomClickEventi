package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.OrderWith;
import pacchetto.bean.GestorePacchetti;
import pacchetto.bean.Pacchetto;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestGestorePacchetti {

    private GestorePacchetti gp;
    private Pacchetto p = new Pacchetto();
    @Before
    public void setUp(){
        gp = new GestorePacchetti();
        p = new Pacchetto();
        p.setId(1);
        gp.addPacchetto(p);
    }

    @Test
    public void testAddPacchettoTrue(){
        HashSet<Pacchetto> pacchetti = gp.getPacchetti();
        assertEquals(1, pacchetti.size());
    }

    @Test
    public void testAddPacchettoFalse(){
        //Provo ad aggiungerlo anche se già è stato fatto
        gp.addPacchetto(p);
        HashSet<Pacchetto> pacchetti = gp.getPacchetti();
        assertEquals(1, pacchetti.size());
    }

    @Test
    public void testDeletePacchettoTrue(){
        int id = 1;
        gp.deletePacchetto(id);
        HashSet<Pacchetto> pacchetti = gp.getPacchetti();
        assertEquals(0, pacchetti.size());
    }

    @Test
    public void testDeletePacchettoFalse(){
        int id = 0;
        gp.deletePacchetto(id);
        HashSet<Pacchetto> pacchetti = gp.getPacchetti();
        assertEquals(1, pacchetti.size());
    }


}
