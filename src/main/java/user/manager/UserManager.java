package user.manager;

import connection.ConPool;
import pacchetto.bean.GestorePacchetti;
import party.bean.Artista;
import party.bean.Contabile;
import party.bean.Fornitore;
import party.bean.GestoreParty;
import user.bean.Cliente;
import user.bean.Gestore;
import user.bean.GestoreImpiegati;
import user.bean.Utente;


import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

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

            Utente u = new Utente(nome, cognome, email, password, telefono);
            u.setId(idUtente);

            return u;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Gestore insertImpiegato(Utente u, String tipoGestore)
    {
        try (Connection con = ConPool.getConnection()) {
            u=insertUser(u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), u.getTelefono());

            Gestore g = insertGestore(u, tipoGestore);
            if(g.getTipoGestore()=="contabile")
            {
                Contabile c = insertContabile(g);
                return c;
            }else if(g.getTipoGestore()=="impiegati")
            {
                GestoreImpiegati gi = insertGestoreImpiegati(g);
                return gi;
            }else if(g.getTipoGestore()=="pacchetti")
            {
                GestorePacchetti gp = insertGestorePacchetti(g);
                return gp;
            }else{
                GestoreParty gParty = insertGestoreParty(g);
                return gParty;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static Gestore insertGestore(Utente u, String tipoGestore)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO gestore (idGestore, tipoGestore) VALUES (?, ?)");
            ps.setInt(1, u.getId());
            ps.setString(2, tipoGestore);

            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }

            Gestore g = (Gestore) u;
            g.setId(u.getId());
            g.setTipoGestore(tipoGestore);

            return g;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Contabile insertContabile(Gestore g)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO contabile (idContabile) VALUES (?)");
            ps.setInt(1, g.getId());

            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }

            Contabile c = (Contabile) g;
            c.setId(g.getId());

            return c;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static GestoreParty insertGestoreParty(Gestore g)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO gestoreParty (idGestoreParty) VALUES (?)");
            ps.setInt(1, g.getId());

            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }

            GestoreParty gParty = (GestoreParty) g;
            gParty.setId(g.getId());

            return gParty;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static GestoreImpiegati insertGestoreImpiegati(Gestore g)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO gestoreImpiegati (idGestoreImpiegati) VALUES (?)");
            ps.setInt(1, g.getId());

            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }

            GestoreImpiegati gi = (GestoreImpiegati) g;
            gi.setId(g.getId());

            return gi;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static GestorePacchetti insertGestorePacchetti(Gestore g)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO gestorePacchetti (idGestorePacchetti) VALUES (?)");
            ps.setInt(1, g.getId());

            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }

            GestorePacchetti gPacchetti = (GestorePacchetti) g;
            gPacchetti.setId(g.getId());

            return gPacchetti;
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

    public static boolean checkIdByEmail(String email)
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

    public static Utente login(String email, String pwd)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email=? AND password=?/*SHA1(?)*/");
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

                return u;
            }
                return null;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Cliente isCliente(Utente u)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.nome, u.cognome, u.email, u.password, u.id, u.telefono FROM utente as u, cliente as c WHERE u.id=? AND c.idCliente = u.id");
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Cliente c = new Cliente(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"));
                c.setId(rs.getInt("id"));
                return c;
            }else
                return null;
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static Artista isArtista(Utente u)
    {
        try(Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.nome, u.cognome, u.email, u.password, u.id, u.telefono, a.tipoArtista \n" +
                    "FROM utente AS u, artista AS a\n" +
                    "WHERE u.id IN (\n" +
                    "\tselect a.idArtista\n" +
                    "    WHERE a.idArtista=u.id AND u.id=?\n" +
                    ")");
            ps.setInt(1, u.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
                Artista a = new Artista(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"), rs.getString("tipoArtista"));
                a.setId(rs.getInt("id"));
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
            PreparedStatement ps = con.prepareStatement("SELECT u.nome, u.cognome, u.email, u.password, u.id, u.telefono, g.tipoGestore\n" +
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
                Gestore g = new Gestore(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"), tipoGestore);
                g.setId(rs.getInt("id"));
                return g;
            }else
                return null;

        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Artista,Double> findArtistaByIdParty(int idParty){

        HashMap<Artista,Double> collection = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("select ingaggio.prezzo, artista.tipoArtista, utente.id, utente.nome, utente.cognome, utente.email, utente.telefono, utente.password, utente.tipo\n" +
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
    public Fornitore insertFornitore(String nomeAzienda,String proprietario, String telefono, String tipoFornitore){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO fornitori (nomeAzienda, proprietario, telefono, tipoFornitore) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, nomeAzienda);
            ps.setString(2, proprietario);
            ps.setString(3, telefono);
            ps.setString(4, tipoFornitore);
            if (ps.executeUpdate() != 1)
            {
                throw new RuntimeException("INSERT error.");
            }
            Fornitore f = new Fornitore(telefono, nomeAzienda, proprietario, tipoFornitore);
            ResultSet rs= ps.getGeneratedKeys();
            rs.next();
            f.setId(rs.getInt("id"));
            return f;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashSet<Gestore> retrieveAllEmployee()
    {
        HashSet<Gestore> collection = new HashSet<Gestore>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.id, u.nome, u.cognome, u.email, u.password, u.telefono, u.tipo, g.tipoGestore\n" +
                    "FROM gestore AS g, utente AS u\n" +
                    "WHERE g.idGestore = u.id" +
                    "ORDER BY g.tipoGestore");

            ResultSet rs= ps.executeQuery();

            while(rs.next())
            {
                Gestore g = new Gestore(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"), rs.getString("tipoGestore"));
                g.setId(rs.getInt("id"));
                collection.add(g);
            }
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashSet<Artista> retrieveAllArtisti()
    {
        HashSet<Artista> collection = new HashSet<Artista>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.id, u.nome, u.cognome, u.email, u.password, u.telefono, u.tipo, a.tipoArtista\n" +
                    "FROM artista AS a, utente AS u\n" +
                    "WHERE a.idArtista = u.id");

            ResultSet rs= ps.executeQuery();

            while(rs.next())
            {
                Artista a = new Artista(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"), rs.getString("tipoArtista"));
                a.setId(rs.getInt("id"));
                collection.add(a);
            }
            return collection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Artista, Double> findArtistiById(HashMap<Integer, Double> map) {
        HashMap<Artista, Double> mapFinal = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            for(Integer i : map.keySet()) {
                PreparedStatement ps = con.prepareStatement("SELECT u.id, u.nome, u.cognome, u.email, u.password, u.telefono, u.tipo, a.tipoArtista\n" +
                        "FROM artista AS a, utente AS u\n" +
                        "WHERE a.idArtista = u.id AND a.idArtista = ?");
                ps.setInt(1, i);
                ResultSet rs = ps.executeQuery();
                Artista a = new Artista(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), rs.getString("password"), rs.getString("telefono"), rs.getString("tipoArtista"));
                a.setId(rs.getInt("id"));
                mapFinal.put(a, map.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mapFinal;
    }

    public static HashMap<Fornitore, Double> findFornitoriById(HashMap<Integer, Double> map) {
        HashMap<Fornitore, Double> mapFinal = new HashMap<>();
        try (Connection con = ConPool.getConnection()) {
            for(Integer i : map.keySet()) {
                PreparedStatement ps = con.prepareStatement("SELECT f.id, f.nomeAzienda, f.proprietario, f.telefono, f.tipoFornitore\n" +
                        "FROM fornitori AS f\n" +
                        "WHERE f.idFornitore = ?");
                ps.setInt(1, i);
                ResultSet rs = ps.executeQuery();
                Fornitore fornitore = new Fornitore(rs.getString("telefono"), rs.getString("nomeAzienda"), rs.getString("proprietario"), rs.getString("tipoFornitore"));
                fornitore.setId(rs.getInt("id"));
                mapFinal.put(fornitore, map.get(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mapFinal;
    }

    public static double visualizzaIncassi(int idParty){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (p.prezzoPacchetto-SUM(i.prezzo)) AS incasso\n" +
                    "FROM ingaggio AS i, party AS p\n" +
                    "WHERE p.id=?");
            ps.setInt(1, idParty);
            ResultSet rs = ps.executeQuery();
            return rs.getDouble("incasso");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
