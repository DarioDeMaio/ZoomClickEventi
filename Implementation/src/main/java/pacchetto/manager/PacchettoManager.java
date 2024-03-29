package pacchetto.manager;

import connection.ConPool;
import pacchetto.bean.Pacchetto;

import java.sql.*;
import java.util.HashSet;

public class PacchettoManager {

    public static boolean insertPacchetto(Pacchetto p) {
        if(p!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO pacchetti (titolo, eventiConsigliati, prezzo, flag) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, p.getTitolo());
                ps.setString(2, p.getEventiConsigliati());
                ps.setDouble(3, p.getPrezzo());
                ps.setInt(4, 1);
                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int idPacchetto = rs.getInt(1);
                p.setId(idPacchetto);
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return false;
    }

    public static HashSet<Pacchetto> retrieveAll()
    {
        HashSet<Pacchetto> collection = new HashSet<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  pacchetti WHERE flag=1");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pacchetto p = new Pacchetto(rs.getString("titolo"), rs.getString("eventiConsigliati"), rs.getDouble("prezzo"), rs.getInt("flag"));
                p.setId(rs.getInt("id"));
                collection.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public static boolean deletePacchetto(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE pacchetti SET flag=? WHERE id=?");
            ps.setInt(1, 0);
            ps.setInt(2, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pacchetto findPacchettoById(int id){
        Pacchetto p = null;
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  pacchetti WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                p = new Pacchetto(rs.getString("titolo"), rs.getString("eventiConsigliati"), rs.getDouble("prezzo"), rs.getInt("flag"));
                p.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return p;
    }


}
