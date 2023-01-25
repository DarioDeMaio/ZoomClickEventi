package party;

import connection.ConPool;
import pacchetto.Pacchetto;
import pacchetto.PacchettoManager;

import java.sql.*;
import java.util.Date;
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
                Pacchetto pacchetto = PacchettoManager.findPacchettoById(idPacchetto);
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto);
                p.setId(rs.getInt("id"));
                p.setArtisti(findArtistaByIdParty(p.getId()));
                p.setFornitori(findFornitoreByIdParty(p.getId()));
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
                Pacchetto pacchetto = PacchettoManager.findPacchettoById(idPacchetto);
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto);
                p.setId(rs.getInt("id"));
                p.setArtisti(findArtistaByIdParty(p.getId()));
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

    public static HashMap<Artista,Double> findArtistaByIdParty(int idParty){

        HashMap<Artista,Double> collection = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select ingaggio.prezzo, artista.tipoArtista, utente.id, utente.nome, utente.cognome, utente.email, utente.telefono, utente.password\n" +
                    "from artista, ingaggio, utente\n" +
                    "where ingaggio.idParty=? AND utente.id = ingaggio.idArtista AND ingaggio.idArtista = artista.idArtista");
            ps.setInt(1, idParty);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Artista a = new Artista(rs.getString("telefono"), rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("tipoArtista"));
                a.setId(rs.getInt("id"));
                collection.put(a,rs.getDouble("prezzo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public static HashMap<Fornitore,Double> findFornitoreByIdParty(int idParty){

        HashMap<Fornitore,Double> collection = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select richiede.prezzo, fornitori.id, fornitori.nomeAzienda, fornitori.proprietario, fornitori.telefono, fornitori.tipoFornitore\n" +
                    "from richiede, fornitori, party\n" +
                    "where party.id=? AND richiede.idParty = party.id AND fornitori.id=richiede.idFornitore");
            ps.setInt(1, idParty);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Fornitore f = new Fornitore(rs.getString("telefono"), rs.getString("nomeAzienda"), rs.getString("proprietario"), rs.getString("tipoFornitore"));
                f.setId(rs.getInt("id"));
                collection.put(f,rs.getDouble("prezzo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

}
