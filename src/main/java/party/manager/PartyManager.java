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
import java.util.Iterator;


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
                p = new Party(rs.getString("tipo"), rs.getString("festeggiato"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto, rs.getInt("idCliente"));
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
            PreparedStatement ps = con.prepareStatement("SELECT id, tipo, festeggiato, data, citta, dataPrenotazione, nomeLocale, servizi, stato, prezzo, idPacchetto, idCliente" +
                    " FROM  party, ingaggio WHERE ingaggio.idArtista=? AND party.id=ingaggio.idParty");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPacchetto = rs.getInt("idPacchetto");
                Pacchetto pacchetto = PacchettoManager.findPacchettoById(idPacchetto);
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("festeggiato"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto, rs.getInt("idCliente"));
                p.setId(rs.getInt("id"));
                p.setArtisti(findArtistaByIdParty(p.getId()));
                collection.put(p,rs.getDouble("prezzo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public static void prenotaParty(Party p){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO party (tipo, data, dataPrenotazione, nomeLocale, servizi, stato, idPacchetto, idCliente, citta, festeggiato) VALUES (?, ?, ?, ?, ?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,p.getTipo());
            ps.setDate(2, new Date(p.getData().getTime()));
            ps.setDate(3, new Date(p.getDataPrenotazione().getTime()));
            ps.setString(4, p.getNomeLocale());
            ps.setString(5, p.getServizi());
            ps.setString(6,"In attesa");
            ps.setInt(7,p.getPacchetto().getId());
            ps.setInt(8,p.getIdCliente());
            ps.setString(9,p.getCitta());
            ps.setString(10,p.getFesteggiato());
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

            for(Fornitore f : p.getFornitori()){
                PreparedStatement ps2 = con.prepareStatement("INSERT INTO richiede (idFornitore, idParty) VALUES (?,?)");
                ps2.setInt(1, f.getId());
                ps2.setInt(2, p.getId());
                if (ps2.executeUpdate() != 1) {
                    throw new RuntimeException("Inserimento fornitore error.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashSet<Party> findPartyByIdCliente(int idCliente){
        HashSet<Party> collection = new HashSet<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT *" +
                    " FROM party WHERE idCliente = ?");
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idPacchetto = rs.getInt("idPacchetto");
                Pacchetto pacchetto = PacchettoManager.findPacchettoById(idPacchetto);
                Party p;
                p = new Party(rs.getString("tipo"), rs.getString("festeggiato"), rs.getString("nomeLocale"), rs.getString("citta"), rs.getDate("data"), rs.getDate("dataPrenotazione"), rs.getString("stato"),rs.getString("servizi"), pacchetto, idCliente);
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

    public static HashSet<Fornitore> retrieveAllFornitori()
    {
        HashSet<Fornitore> collection = new HashSet<Fornitore>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT f.id, f.nomeAzienda, f.proprietario, f.telefono, f.tipoFornitore, f.prezzo\n" +
                    "FROM fornitori AS f");

            ResultSet rs= ps.executeQuery();

            while(rs.next())
            {
                Fornitore f = new Fornitore(rs.getString("telefono"), rs.getString("nomeAzienda"), rs.getString("proprietario"), rs.getString("tipoFornitore"), rs.getDouble("prezzo"));
                f.setId(rs.getInt("id"));
                collection.add(f);
            }
            return collection;
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
                Artista a = new Artista(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"), rs.getString("tipoArtista"));
                a.setId(rs.getInt("id"));
                collection.put(a,rs.getDouble("prezzo"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

    public static HashSet<Fornitore> findFornitoreByIdParty(int idParty){

        HashSet<Fornitore> collection = new HashSet<Fornitore>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select fornitori.prezzo, fornitori.id, fornitori.nomeAzienda, fornitori.proprietario, fornitori.telefono, fornitori.tipoFornitore\n" +
                    "from richiede, fornitori, party\n" +
                    "where party.id=? AND richiede.idParty = party.id AND fornitori.id=richiede.idFornitore");
            ps.setInt(1, idParty);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Fornitore f = new Fornitore(rs.getString("telefono"), rs.getString("nomeAzienda"), rs.getString("proprietario"), rs.getString("tipoFornitore"), rs.getDouble("prezzo"));
                f.setId(rs.getInt("id"));
                collection.add(f);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return collection;
    }

}
