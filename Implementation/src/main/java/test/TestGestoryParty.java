package test;

import org.junit.Before;
import org.junit.Test;
import party.bean.GestoreParty;
import party.bean.Party;
import static org.junit.Assert.assertEquals;
import java.util.HashSet;

public class TestGestoryParty {

    private GestoreParty gParty;
    private Party p;

    @Before
    public void setUp(){
        gParty = new GestoreParty();
        p = new Party();
        p.setId(1);
        HashSet<Party> map = new HashSet<>();
        map.add(p);
        gParty.setParty(map);
    }

    @Test
    public void findPartyTrue(){
        int id = 1;
        Party party = gParty.findById(id);
        assertEquals(p, party);
    }

    @Test
    public void findPartyFalse(){
        int id = 0;
        Party party = gParty.findById(id);
        assertEquals(null, party);
    }

}
