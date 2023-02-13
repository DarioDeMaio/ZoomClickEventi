package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static party.manager.PartyManager.findArtistaByIdParty;
import static party.manager.PartyManager.findFornitoreByIdParty;
import static user.manager.UserManager.*;


import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import connection.ConPool;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import pacchetto.bean.GestorePacchetti;
import pacchetto.bean.Pacchetto;
import pacchetto.manager.PacchettoManager;
import party.bean.Artista;
import party.bean.Fornitore;
import party.bean.GestoreParty;
import party.bean.Party;
import party.manager.PartyManager;
import user.bean.Contabile;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;
import user.bean.Utente;
import user.manager.UserManager;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class TestPartyManager {

    @Mock
    public Connection connection;
    @Mock
    public PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;


    @Before
    public void setUp() throws SQLException {
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
    }

    //delete party
    @Test
    public void testDeleteParty_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)){
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            boolean result = PartyManager.deleteParty(27);
            assert (result == true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteParty_DeleteError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(RuntimeException.class, () -> {
                PartyManager.deleteParty(27);
            });
        }
    }

    @Test
    public void testDeleteParty_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.deleteParty(27);
            });
        }
    }

    //test retrieveAllParty

    @Test
    public void testRetrieveAllParty_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true,false);
            //MockedStatic<PartyManager> pm = Mockito.mockStatic(PartyManager.class);
            HashMap<Artista, Double> artisti = new HashMap<>();
            artisti.put(new Artista(),100.0);
            artisti.put(new Artista(),100.0);
            //pm.when(()->PartyManager.findArtistaByIdParty(1)).thenReturn(artisti);

            HashSet<Fornitore> fornitori = new HashSet<>();
            fornitori.add(new Fornitore());
            fornitori.add(new Fornitore());
            //when(findFornitoreByIdParty(1)).thenReturn(fornitori);
            //pm.when(()->PartyManager.findFornitoreByIdParty(1)).thenReturn(fornitori);


            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("tipo")).thenReturn("tipo");
            when(resultSet.getString("festeggiato")).thenReturn("festeggiato");
            when(resultSet.getString("nomeLocale")).thenReturn("nomeLocale");
            when(resultSet.getString("città")).thenReturn("città");
            when(resultSet.getDate("data")).thenReturn(new Date(100));
            when(resultSet.getDate("data")).thenReturn(new Date(100));
            when(resultSet.getString("stato")).thenReturn("stato");
            when(resultSet.getString("servizi")).thenReturn("servizi");
            when(resultSet.getString("idCliente")).thenReturn("idCliente");

            HashSet<Party> map = PartyManager.retrieveAllParty();
            //pm.close();
            assertNotEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testRetrieveAllParty_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(RuntimeException.class, () -> {
                PartyManager.retrieveAllParty();
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testRetrieveAllParty_SQL(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.deleteParty(27);
            });
        }
    }

    //test prenota party

    @Test
    public void testPrenotaParty_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            Party p = new Party("tipo", "festeggiato", "nomeLocale", "città", new java.util.Date(), new java.util.Date(), "stato", "servizi", new Pacchetto(), 1);
            PartyManager.prenotaParty(p);
            assertEquals(1, p.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrenotaParty_PartyNullNotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            Party p = null;
            PartyManager.prenotaParty(p);
            assertEquals(null, p);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testPrenotaParty_NotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            when(resultSet.getInt(1)).thenReturn(1);
            Party p = new Party("tipo", "festeggiato", "nomeLocale", "città", new java.util.Date(), new java.util.Date(), "stato", "servizi", new Pacchetto(), 1);
            assertThrows(RuntimeException.class, () -> {
                PartyManager.prenotaParty(p);
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testPrenotaParty_SQL(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Party p = new Party("tipo", "festeggiato", "nomeLocale", "città", new java.util.Date(), new java.util.Date(), "stato", "servizi", new Pacchetto(), 1);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.prenotaParty(p);
            });
        }
    }

    //test rifiutoParty
    @Test
    public void testRifiutoParty_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            boolean b = PartyManager.rifiutoParty(1);
            assertEquals(true,b);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRifiutoParty_NotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(RuntimeException.class, () -> {
                PartyManager.rifiutoParty(1);
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRifiutoParty_SQL() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.rifiutoParty(1);
            });

        }
    }

    //test confermaParty
    @Test
    public void testConfermaParty_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.getInt(1)).thenReturn(1);
            Party p = new Party("tipo", "festeggiato", "nomeLocale", "città", new java.util.Date(), new java.util.Date(), "stato", "servizi", new Pacchetto(), 1);
            assertEquals(true, PartyManager.confermaParty(p));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testConfermaParty_NotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.getInt(1)).thenReturn(1);
            Party p = new Party("tipo", "festeggiato", "nomeLocale", "città", new java.util.Date(), new java.util.Date(), "stato", "servizi", new Pacchetto(), 1);
            assertThrows(RuntimeException.class, () -> {
                PartyManager.rifiutoParty(1);
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testConfermaParty_SQL() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Party p = new Party("tipo", "festeggiato", "nomeLocale", "città", new java.util.Date(), new java.util.Date(), "stato", "servizi", new Pacchetto(), 1);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.confermaParty(p);
            });

        }
    }

    //test findFornitoreByIdParty

    @Test
    public void testFindFornitoreByIdParty_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true, false);
            HashSet<Fornitore> map = PartyManager.findFornitoreByIdParty(1);
            assertEquals(1, map.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testFindFornitoreByIdParty_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            HashSet<Fornitore> map = PartyManager.findFornitoreByIdParty(1);
            assertEquals(0, map.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testFindFornitoreByIdParty_SQL() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.findFornitoreByIdParty(1);
            });

        }
    }

    //test findArtistaByIdParty
    @Test
    public void testFindArtistaByIdParty_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true, false);
            HashMap<Artista, Double> map = PartyManager.findArtistaByIdParty(1);
            assertEquals(1, map.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testFindArtistaByIdParty_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            HashMap<Artista, Double> map = PartyManager.findArtistaByIdParty(1);
            assertEquals(0, map.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testFindArtistaByIdParty_SQL() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.findArtistaByIdParty(1);
            });

        }
    }

    //test retrieveAllFornitori
    @Test
    public void testretrieveAllFornitori_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true, false);
            HashSet<Fornitore> map = PartyManager.retrieveAllFornitori();
            assertEquals(1, map.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testretrieveAllFornitori_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            HashSet<Fornitore> map = PartyManager.retrieveAllFornitori();
            assertEquals(0, map.size());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testRetrieveAllFornitori_SQL(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PartyManager.findArtistaByIdParty(1);
            });

        }
    }

}
