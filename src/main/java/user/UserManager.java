package user;

import connection.ConPool;
import employee.EmployeeManager;
import party.Artista;


import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    public static Utente insertUser(String nome, String cognome, String email, String password, String telefono)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente (nome, cognome, email, password, telefono) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, firstLetterUpperCase(nome));
            ps.setString(2, firstLetterUpperCase(cognome));
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, telefono);

            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            int idUtente = rs.getInt(1);
            System.out.println(idUtente);

            Utente u = new Utente(nome, cognome, email, password, telefono);
            u.setId(idUtente);

            return u;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(Utente u)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("Update utente\n" +
                    "SET email=?, password=?, telefono=?\n" +
                    "WHERE id=?");
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getTelefono());
            ps.setInt(4, u.getId());
            ResultSet rs = ps.executeQuery();

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("UPDATE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIdByEmail(String email)
    {
        try (Connection con = ConPool.getConnection()) {
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT email FROM utente");

            while(rs.next())
            {
                if(email.equals(rs.getString(1)))
                    return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String firstLetterUpperCase(String str)
    {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public Utente login(String email, String pwd)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email=? AND password=SHA1(?)");
            ps.setString(1, email);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                Utente u = new Utente();
                u.setId(rs.getInt(1));
                u.setNome(rs.getString(2));
                u.setCognome(rs.getString(3));
                u.setEmail(rs.getString(4));
                u.setPassword(rs.getString(5));
                u.setTelefono(rs.getString(6));

                if(isCliente(u)) {
                    Cliente c = (Cliente) u;
                    return c;
                }else {
                    Artista a = EmployeeManager.isArtista(u);
                    if(a != null)
                    {
                        return a;
                    }else{
                        Gestore g = (Gestore) EmployeeManager.isGestore(u);
                        return g;
                    }
                }
            }else
                return null;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isCliente(Utente u)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.id\n" +
                    "FROM utente AS u\n" +
                    "WHERE u.id IN (\n" +
                    "\tselect c.idCliente\n" +
                    "    FROM cliente AS c\n" +
                    "    WHERE c.idCliente=u.id AND u.id=?\n" +
                    ")");
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                return true;
            }else
                return false;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
