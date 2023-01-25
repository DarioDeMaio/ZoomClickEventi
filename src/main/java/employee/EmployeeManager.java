package employee;

import connection.ConPool;
import pacchetto.GestorePacchetti;
import party.Artista;
import party.Contabile;
import party.GestoreParty;
import user.Gestore;
import user.GestoreImpiegati;
import user.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeManager {

    public static Artista isArtista(Utente u)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.id, a.tipoArtista \n" +
                    "FROM utente AS u, artista AS a\n" +
                    "WHERE u.id IN (\n" +
                    "\tselect a.idArtista\n" +
                    "    WHERE a.idArtista=u.id AND u.id=?\n" +
                    ")");
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Artista a = (Artista) u;
                a.setTipoArtista(rs.getString("tipoArtista"));
                return a;
            }else
                return null;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Gestore isGestore(Utente u)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.id, g.tipoGestore\n" +
                    "FROM utente AS u, gestore AS g\n" +
                    "WHERE u.id IN (\n" +
                    "\tselect g.idgestore\n" +
                    "    WHERE g.idgestore=u.id AND u.id=?\n" +
                    ")");
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                String tipoGestore = rs.getString("tipoGestore");
                Gestore g = (Gestore) u;
                g.setTipoGestore(tipoGestore);
                return g;
            }else
                return null;

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
