package user;

import connection.ConPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDAO {


    public String doLogin(String username, String psw) {

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM persona WHERE email=? AND password=SHA1(?)");
            ps.setString(1, username);
            ps.setString(2, psw);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

            } else
                return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
