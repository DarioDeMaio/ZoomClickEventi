package party.manager;

import connection.ConPool;
import pacchetto.bean.Pacchetto;
import pacchetto.manager.PacchettoManager;
import party.bean.Artista;
import party.bean.Fornitore;
import party.bean.Party;
import user.manager.UserManager;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;


public class PartyManager {


    public static void deleteParty(int id){

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

    //gestore party, cliente
    public static HashSet<Party> retrieveAllParty(){
        HashSet<Party> collection = new HashSet<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM  party");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idPacchetto = rs.getInt("idPacchetto");
                Pacchetto pacchetto = PacchettoManager.findPacchettoByid(idPacchetto);
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto);
                p.setId(rs.getInt("id"));
                p.setArtisti(UserManager.findArtistaByIdParty(p.getId()));
                p.setFornitori(UserManager.findFornitoreByIdParty(p.getId()));
                collection.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    //artista
    public static HashMap<Party, Double> retrievePartyByIdArtista(int id){

        HashMap<Party, Double> collection = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT id, tipo, data, dataPrenotazione, nomeLocale, servizi, stato, prezzo, idPacchetto" +
                    " FROM  party, ingaggio WHERE ingaggio.idArtista=?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPacchetto = rs.getInt("idPacchetto");
                Pacchetto pacchetto = PacchettoManager.findPacchettoByid(idPacchetto);
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto);
                p.setId(rs.getInt("id"));
                p.setArtisti(UserManager.findArtistaByIdParty(p.getId()));
                collection.put(p,rs.getDouble("prezzoPacchetto"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public static void prenotaParty(Party p, int idCliente){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO party (tipo, data, dataPrenotazione, nomeLocale, servizi, stato, idPacchetto, idCliente, prezzoPacchetto, citta) VALUES (?, ?, ?, ?, ?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,p.getTipo());
            ps.setDate(2,(java.sql.Date) p.getData());
            ps.setDate(3, (java.sql.Date) p.getDataPrenotazione());
            ps.setString(4, p.getNomeLocale());
            ps.setString(5, p.getServizi());
            ps.setString(6,"In attesa");
            ps.setInt(7,p.getPacchetto().getId());
            ps.setInt(8,idCliente);
            ps.setDouble(9,p.getPacchetto().getPrezzo());
            ps.setString(10,p.getCitta());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            int idParty = rs.getInt(1);
            p.setId(idParty);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void rifiutoParty(int id){
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

    public static void confermaParty(Party p){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE party SET stato=? WHERE id=?");
            ps.setString(1, "Confermato");
            ps.setInt(2, p.getId());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("Conferma error.");
            }

            for(Artista a : p.getArtisti().keySet()){
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO ingaggio (idArtista, idParty, prezzo) VALUES (?,?,?)");
                ps2.setInt(1, a.getId());
                ps2.setInt(2, p.getId());
                ps2.setDouble(3, p.getArtisti().get(a));
                if (ps2.executeUpdate() != 1) {
                    throw new RuntimeException("Inserimento artista error.");
                }
            }

            for(Fornitore f : p.getFornitori().keySet()){
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO richiede (idFornitore, idParty, prezzo) VALUES (?,?,?)");
                ps2.setInt(1, f.getId());
                ps2.setInt(2, p.getId());
                ps2.setDouble(3, p.getFornitori().get(f));
                if (ps2.executeUpdate() != 1) {
                    throw new RuntimeException("Inserimento fornitore error.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
