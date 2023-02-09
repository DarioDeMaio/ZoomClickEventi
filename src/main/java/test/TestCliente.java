package test;

import org.junit.Before;
import org.junit.Test;
import party.bean.Party;
import party.manager.PartyManager;
import user.bean.Cliente;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestCliente {

    private Cliente c;
    private Party p;

    @Before
    public void setUp(){
        c = new Cliente();
        p = new Party();
        p.setId(1);
        c.addParty(p);
    }

    @Test
    public void testAddPartyTrue(){
        HashSet<Party> map = c.getParties();
        assertEquals(1, map.size());
    }

    @Test
    public void testAddPartyFalse(){
        c.addParty(p);
        HashSet<Party> map = c.getParties();
        assertEquals(1, map.size());
    }
}
