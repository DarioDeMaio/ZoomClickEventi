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
import party.bean.Artista;
import party.bean.GestoreParty;
import user.bean.Contabile;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;
import user.bean.Utente;
import user.manager.UserManager;

import javax.sql.DataSource;

@RunWith(MockitoJUnitRunner.class)
public class TestUserManager {
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

    //insertUser
    @Test
    public void testInsertUser(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);

            Utente result = UserManager.insertUser("nome1","cognome","email","password","telefono");
            assertEquals(1, result.getId());
            assertEquals("nome1", result.getNome());
            assertEquals("cognome", result.getCognome());
            assertEquals("email", result.getEmail());
            assertEquals("password", result.getPassword());
            assertEquals("telefono", result.getTelefono());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testInsertUser_NotSuccessEmail(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);

            Utente result = UserManager.insertUser("nome1","cognome",null,"password","telefono");
            assertEquals(null, result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testInsertUser_InsertError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(RuntimeException.class, () -> {
                UserManager.insertUser("nome", "cognome", "email", "password", "telefono");
            });
        }
    }
    @Test
    public void testInsertUser_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            //when(connection.prepareStatement("INSERT INTO utente (nome, cognome, email, password, telefono) VALUES (?, ?, ?, ?, ?)")).thenThrow(new SQLException());
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.insertUser("nome", "cognome", "email", "password", "telefono");
            });
        }
    }

    //insertCliente
    @Test
    public void testinsertCliente() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            Utente u = new Utente("nome", "cognome", "email", "password", "telefono");
            boolean bool = UserManager.insertCliente(u);
            assertEquals(true, bool);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertCliente_NotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            Utente u = null;
            boolean bool = UserManager.insertCliente(u);
            assertEquals(false, bool);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertCliente_InsertError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            Utente u = new Utente("nome", "cognome", "email", "password", "telefono");
            assertThrows(RuntimeException.class, () -> {
                UserManager.insertCliente(u);
            });
        }
    }

    @Test
    public void testinsertCliente_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(connection.prepareStatement("INSERT INTO cliente (idCliente) VALUES (?)")).thenThrow(new SQLException());
            Utente u = new Utente("nome", "cognome", "email", "password", "telefono");

            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.insertCliente(u);
            });
        }
    }

    //delete user
    @Test
    public void testDeleteUser_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)){
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            boolean result = UserManager.deleteUser(27);
            assert (result == true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteUser_DeleteError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            assertThrows(RuntimeException.class, () -> {
                UserManager.deleteUser(30);
            });
        }
    }

    @Test
    public void testDeleteUser_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(connection.prepareStatement("DELETE FROM utente WHERE utente.id=?")).thenThrow(new SQLException());
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.deleteUser(30);
            });
        }
    }


    //insertContabile

    @Test
    public void testinsertContabile() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "contabile");
            Contabile contabile = UserManager.insertContabile(u);
            assertEquals("nome", contabile.getNome());
            assertEquals("cognome", contabile.getCognome());
            assertEquals("email", contabile.getEmail());
            assertEquals("password", contabile.getPassword());
            assertEquals("telefono", contabile.getTelefono());
            assertEquals("contabile", contabile.getTipoGestore());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertContabile_NotSuccessUserNull() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            Gestore u = null;
            assertEquals(null, UserManager.insertContabile(u));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertContabile_InsertError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);

            //Gestore g = mock(Gestore.class);
            //when(g.getId()).thenReturn(1);

            Contabile ge = new Contabile("nome", "cognome", "email", "password", "telefono", "tipo");
            assertThrows(RuntimeException.class, () -> {
                UserManager.insertContabile(ge);
            });
        }
    }

    @Test
    public void testinsertContabile_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            //when(connection.prepareStatement("INSERT INTO contabile (idContabile) VALUES (?)")).thenThrow(new SQLException());
            Contabile ge = new Contabile("nome", "cognome", "email", "password", "telefono", "tipo");

            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.insertCliente(ge);
            });
        }
    }

    //test insertGestoreParty
    @Test
    public void testinsertGestoreParty() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);


            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "party");
            GestoreParty gp = UserManager.insertGestoreParty(u);

            assertEquals("nome", gp.getNome());
            assertEquals("cognome", gp.getCognome());
            assertEquals("email", gp.getEmail());
            assertEquals("password", gp.getPassword());
            assertEquals("telefono", gp.getTelefono());
            assertEquals("party", gp.getTipoGestore());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertGestoreParty_GestoreNull() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            Gestore u = null;
            assertEquals(null, UserManager.insertContabile(u));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertGestoreParty_InsertError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);



            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "tipo");
            assertThrows(RuntimeException.class, () -> {
                UserManager.insertGestoreParty(u);
            });
        }
    }

    @Test
    public void testinsertGestoreParty_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Contabile ge = new Contabile("nome", "cognome", "email.com", "password", "telefono", "tipo");

            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.insertGestoreParty(ge);
            });
        }
    }

    //test insertgestoreImpiegati

    @Test
    public void testinsertGestoreImpiegati() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);


            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "impiegati");
            GestoreImpiegati gp = UserManager.insertGestoreImpiegati(u);

            assertEquals("nome", gp.getNome());
            assertEquals("cognome", gp.getCognome());
            assertEquals("email", gp.getEmail());
            assertEquals("password", gp.getPassword());
            assertEquals("telefono", gp.getTelefono());
            assertEquals("impiegati", gp.getTipoGestore());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertGestoreImpiegatiNull() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);


            Gestore u = null;
            GestoreImpiegati gp = UserManager.insertGestoreImpiegati(u);



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testinsertGestoreImpiegati_InsertError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "tipo");
            assertThrows(RuntimeException.class, () -> {
                UserManager.insertGestoreImpiegati(u);
            });
        }
    }

    @Test
    public void testinsertGestoreImpiegati_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Contabile ge = new Contabile("nome", "cognome", "email.com", "password", "telefono", "tipo");
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.insertGestoreImpiegati(ge);
            });
        }
    }

    //test inserGestorePacchetti

    @Test
    public void testinsertGestorePacchetti() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);


            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "pacchetti");
            GestorePacchetti gp = UserManager.insertGestorePacchetti(u);

            assertEquals("nome", gp.getNome());
            assertEquals("cognome", gp.getCognome());
            assertEquals("email", gp.getEmail());
            assertEquals("password", gp.getPassword());
            assertEquals("telefono", gp.getTelefono());
            assertEquals("pacchetti", gp.getTipoGestore());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertGestorePacchettiNull() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            Gestore u = null;
            GestorePacchetti gp = UserManager.insertGestorePacchetti(u);
            assertEquals(null, gp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testinsertGestorePacchetti_InsertError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            Gestore u = new Gestore("nome", "cognome", "email", "password", "telefono", "pacchetti");
            assertThrows(RuntimeException.class, () -> {
                UserManager.insertGestorePacchetti(u);
            });
        }
    }

    @Test
    public void testinsertGestorePacchetti_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            //when(connection.prepareStatement("INSERT INTO gestoreParty (idGestoreParty) VALUES (?)")).thenThrow(new SQLException());
            Contabile ge = new Contabile("nome", "cognome", "email.com", "password", "telefono", "pacchetti");
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.insertGestorePacchetti(ge);
            });
        }
    }

    //test updateUser

    @Test
    public void testUpdateUser(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            boolean result = UserManager.deleteUser(27);
            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);
            boolean b = updateUser(u);
            assertEquals(true, b);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateUser_EmailNull(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(1);
            boolean result = UserManager.deleteUser(27);
            Utente u = null;
            boolean b = updateUser(u);
            assertEquals(false, b);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testUpdateUser_UpdateError() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeUpdate()).thenReturn(0);
            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);
            assertThrows(RuntimeException.class, () -> {
                UserManager.updateUser(u);
            });
        }
    }

    @Test
    public void testUpdateUser_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            //when(connection.prepareStatement("INSERT INTO gestoreParty (idGestoreParty) VALUES (?)")).thenThrow(new SQLException());
            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.updateUser(u);
            });
        }
    }

    //login

    @Test public void testLogin_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            when(resultSet.getString(2)).thenReturn("nome");
            when(resultSet.getString(3)).thenReturn("cognome");
            when(resultSet.getString(4)).thenReturn("email");
            when(resultSet.getString(6)).thenReturn("telefono");
            String email="email", psw="password";
            Utente u = UserManager.login(email,psw);

            assertNotEquals(null, u.getNome());
            assertNotEquals(null,u.getCognome());
            assertNotEquals(null, u.getTelefono());
            assertNotEquals(null, u.getId());
            assertEquals("email", u.getEmail());
            assertEquals("password", u.getPassword());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test public void testLogin_NotSuccessEmail(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(1);
            when(resultSet.getString(2)).thenReturn("nome");
            when(resultSet.getString(3)).thenReturn("cognome");
            when(resultSet.getString(4)).thenReturn("email");
            when(resultSet.getString(6)).thenReturn("telefono");
            String email=null, psw="password";
            Utente u = UserManager.login(email,psw);

            assertEquals(null, u);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test public void testLogin_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            when(resultSet.getInt(1)).thenReturn(1);
            when(resultSet.getString(2)).thenReturn("nome");
            when(resultSet.getString(3)).thenReturn("cognome");
            when(resultSet.getString(4)).thenReturn("email");
            when(resultSet.getString(6)).thenReturn("telefono");
            String email="email", psw="password";
            Utente u = UserManager.login(email,psw);

            assertEquals(null, u);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLogin_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.login("email","password");
            });
        }
    }


    //isArtista

    @Test
    public void testIsArtista_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("nome")).thenReturn("nome");
            when(resultSet.getString("cognome")).thenReturn("cognome");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("telefono")).thenReturn("telefono");
            when(resultSet.getString("tipoArtista")).thenReturn("artista");

            Artista a = UserManager.isArtista(u);
            assertEquals(u.getId(), a.getId());
            assertEquals(u.getNome(), a.getNome());
            assertEquals(u.getCognome(), a.getCognome());
            assertEquals(u.getEmail(), a.getEmail());
            assertEquals(u.getPassword(), a.getPassword());
            assertEquals(u.getTelefono(), a.getTelefono());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsArtista_NotSuccessNull() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            Utente u = null;
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("nome")).thenReturn("nome");
            when(resultSet.getString("cognome")).thenReturn("cognome");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("telefono")).thenReturn("telefono");
            when(resultSet.getString("tipoArtista")).thenReturn("artista");

            Artista a = UserManager.isArtista(u);
            assertEquals(null, a);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsArtista_NotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);

            Artista a = UserManager.isArtista(u);
            assertEquals(null,a);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsArtista_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Utente u = new Utente("nome","cognome","email","password","telefono");

            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.isArtista(u);
            });
        }
    }

    // testIsGestore

    @Test
    public void testIsGestore_Success() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("nome")).thenReturn("nome");
            when(resultSet.getString("cognome")).thenReturn("cognome");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("telefono")).thenReturn("telefono");
            when(resultSet.getString("tipoArtista")).thenReturn("artista");

            Gestore a = UserManager.isGestore(u);
            assertEquals(u.getId(), a.getId());
            assertEquals(u.getNome(), a.getNome());
            assertEquals(u.getCognome(), a.getCognome());
            assertEquals(u.getEmail(), a.getEmail());
            assertEquals(u.getPassword(), a.getPassword());
            assertEquals(u.getTelefono(), a.getTelefono());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsGestore_Success_NullUser() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);

            Utente u = null;
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("nome")).thenReturn("nome");
            when(resultSet.getString("cognome")).thenReturn("cognome");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("telefono")).thenReturn("telefono");
            when(resultSet.getString("tipoArtista")).thenReturn("artista");

            Gestore a = UserManager.isGestore(u);
            assertEquals(null, u);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testIsGestore_NotSuccess() {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            Utente u = new Utente("nome","cognome","email","password","telefono");
            u.setId(1);

            Gestore a = UserManager.isGestore(u);
            assertEquals(null,a);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testIsGestore_SQLException() throws SQLException {
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            Utente u = new Utente("nome","cognome","email","password","telefono");

            assertThrows(RuntimeException.class, () -> {
                UserManager.isGestore(u);
            });
        }
    }

    //hash
    @Test
    public void testHash_Success(){
        String str = "hash";
        String hash = UserManager.hash(str);
        assertNotEquals(hash,null);

    }
    @Test
    public void testHash_NotSuccess() throws SQLException {
        String str = null;
        assertThrows(RuntimeException.class, () -> {
            UserManager.hash(str);
        });
    }

    //firtLetterUpperCase

    @Test
    public void testFLUC_Success(){
        String str = "fluc";
        String fluc = UserManager.firstLetterUpperCase(str);
        assertNotEquals(fluc,null);

    }
    @Test
    public void testFLUC_NotSuccess() throws SQLException {
        String str = null;
        assertThrows(RuntimeException.class, () -> {
            UserManager.firstLetterUpperCase(str);
        });
    }

    //testRetirieveAllEmployee

    @Test
    public void testRetrieveAllEmployee_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true,false);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("nome")).thenReturn("nome");
            when(resultSet.getString("cognome")).thenReturn("cognome");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("telefono")).thenReturn("telefono");
            when(resultSet.getString("tipoArtista")).thenReturn("artista");
            HashSet<Gestore> map = UserManager.retrieveAllEmployee();
            assertNotEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testRetrieveAllEmployee_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            HashSet<Gestore> map = UserManager.retrieveAllEmployee();
            assertEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRetrieveAllEmployee_SqlException(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.retrieveAllEmployee();
            });
        }
    }

    //test retrieveAllArtisti

    @Test
    public void testRetrieveAllArtisti_Success(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true,false);
            when(resultSet.getInt("id")).thenReturn(1);
            when(resultSet.getString("nome")).thenReturn("nome");
            when(resultSet.getString("cognome")).thenReturn("cognome");
            when(resultSet.getString("email")).thenReturn("email");
            when(resultSet.getString("password")).thenReturn("password");
            when(resultSet.getString("telefono")).thenReturn("telefono");
            when(resultSet.getString("tipoArtista")).thenReturn("artista");
            HashSet<Artista> map = UserManager.retrieveAllArtisti();
            assertNotEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testRetrieveAllArtisti_NotSuccess(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);
            HashSet<Artista> map = UserManager.retrieveAllArtisti();
            assertEquals(map.size(), 0);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRetrieveAllArtisti_SqlException(){
        try (MockedStatic<ConPool> utilities = Mockito.mockStatic(ConPool.class)) {
            utilities.when(ConPool::getConnection).thenReturn(connection);
            assertThrows(RuntimeException.class, () -> {
                when(connection.prepareStatement(any(String.class))).thenThrow(new SQLException());
                UserManager.retrieveAllArtisti();
            });
        }
    }


}
