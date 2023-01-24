package user;

import connection.ConPool;


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
}
