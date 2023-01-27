package user;

import connection.ConPool;
import pacchetto.GestorePacchetti;
import pacchetto.Pacchetto;
import party.Artista;
import party.Contabile;
import party.Fornitore;
import party.GestoreParty;


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

    public static Utente insertImpiegato(Utente u, String tipoGestore, String tipoArtista)
    {
        try (Connection con = ConPool.getConnection()) {
            u=insertUser(u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), u.getTelefono());

            if(tipoGestore=="artista")
            {
                PreparedStatement ps = con.prepareStatement("INSERT INTO artista (idArtista, tipoArtista) VALUES (?, ?)");
                ps.setInt(1, u.getId());
                ps.setString(2, tipoArtista);

                if (ps.executeUpdate() != 1)
                {
                    throw new RuntimeException("INSERT error.");
                }

                Artista a = (Artista) u;
                a.setId(u.getId());
                a.setTipoArtista(tipoArtista);
                return a;
            }else{
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
                    Artista a = isArtista(u);
                    if(a != null)
                    {
                        return a;
                    }else{
                        Gestore g = (Gestore) isGestore(u);
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

    private static Artista isArtista(Utente u)
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

    private static Gestore isGestore(Utente u)
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
