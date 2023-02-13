package test;


import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static user.manager.UserManager.*;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

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
import party.bean.GestoreParty;
import party.bean.Party;
import user.bean.Contabile;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;
import user.bean.Utente;
import user.manager.UserManager;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class TestPacchettoManager {
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

    //test insertPacchetto

    @Test
    public void testInsertPacchetto_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            Pacchetto p = new Pacchetto("titolo", "eventiConsigliati", 100.0, 1);
            boolean b = PacchettoManager.insertPacchetto(p);
            assertEquals(true, b);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInsertPacchetto_PacchettoNullSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            Pacchetto p = null;
            boolean b = PacchettoManager.insertPacchetto(p);
            assertEquals(false, b);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testInsertPacchetto_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            Pacchetto p = new Pacchetto("titolo", "eventiConsigliati", 100.0, 1);
            assertThrows(RuntimeException.class, () -> {
                PacchettoManager.insertPacchetto(p);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInsertPacchetto_Sql() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Pacchetto p = new Pacchetto("titolo", "eventiConsigliati", 100.0, 1);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PacchettoManager.insertPacchetto(p);
            });
        }
    }

    //test retrieve all

    @Test
    public void testRetrieveAll_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true,false);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("titolo")).thenReturn("titolo");
            when(resultSet.getString("eventiConsigliati")).thenReturn("eventiConsigliati");
            when(resultSet.getDouble("prezzo")).thenReturn(50.0);
            when(resultSet.getString("flag")).thenReturn("flag");
            HashSet<Pacchetto> map = PacchettoManager.retrieveAll();
            assertNotEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testRetrieveAll_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            HashSet<Pacchetto> map = PacchettoManager.retrieveAll();
            assertEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testRetrieveAll_Sql() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PacchettoManager.retrieveAll();
            });
        }
    }

    //test delete pacchetto
    @Test
    public void testDelete_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            boolean b = PacchettoManager.deletePacchetto(1);
            assertEquals(true,b);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testDelete_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(RuntimeException.class, () -> {
                PacchettoManager.deletePacchetto(1);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDelete_Sql(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PacchettoManager.deletePacchetto(1);
            });
        }
    }

    //test findPacchettoById

    @Test
    public void testFindPacchettoById_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("titolo")).thenReturn("titolo");
            when(resultSet.getString("eventiConsigliati")).thenReturn("eventiConsigliati");
            when(resultSet.getDouble("prezzo")).thenReturn(50.0);
            when(resultSet.getString("flag")).thenReturn("flag");
            Pacchetto p = PacchettoManager.findPacchettoById(1);
            assertNotEquals(null,p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFindPacchettoById_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("titolo")).thenReturn("titolo");
            when(resultSet.getString("eventiConsigliati")).thenReturn("eventiConsigliati");
            when(resultSet.getDouble("prezzo")).thenReturn(50.0);
            when(resultSet.getString("flag")).thenReturn("flag");
            Pacchetto p = PacchettoManager.findPacchettoById(1);
            assertEquals(null,p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testFindPacchettoById_Sql(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                PacchettoManager.findPacchettoById(1);
            });
        }
    }
}