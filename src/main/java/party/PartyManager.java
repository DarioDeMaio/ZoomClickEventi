package party;

import connection.ConPool;
import pacchetto.Pacchetto;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;


public class PartyManager {

    public PartyManager(){

    }

    public void deleteParty(int id){

        try(Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE FROM party WHERE id=?");
            ps.setInt(1,id);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashSet<Party> retrieveAll(){
        HashSet<Party> collection = new HashSet<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  party");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("nomeLocale"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"), rs.getDouble("prezzo"));
                p.setId(rs.getInt("id"));
                collection.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public HashMap<Party, Double> retrieveByIdArtista(int id){
        HashMap<Party, Double> collection = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, tipo, data, dataPrenotazione, nomeLocale, servizi, stato, prezzo" +
                    " FROM  party, ingaggio WHERE ingaggio.idArtista=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("nomeLocale"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"), rs.getDouble("prezzo"));
                p.setId(rs.getInt("id"));
                collection.put(p,rs.getDouble("prezzo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public void prenotaParty(String servizi, String tipoEvento, String città, String nomeLocale, Date dataEvento, java.util.Date dataPrenotazione, int idPacchetto, int idCliente){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO party (tipo, data, dataPrenotazione, nomeLocale, servizi, stato, idPacchetto, idCliente) VALUES (?, ?, ?, ?,?,?,?,?)");
            ps.setString(1,tipoEvento);
            ps.setDate(2,dataEvento);
            ps.setDate(3, (Date) dataPrenotazione);
            ps.setString(4, nomeLocale);
            ps.setString(5, servizi);
            ps.setString(6,"In attesa");
            ps.setInt(7,idPacchetto);
            ps.setInt(8,idPacchetto);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rifiutoParty(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE party SET stato=? WHERE id=?");
            ps.setString(1, "Rifiutato");
            ps.setInt(2, id);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
