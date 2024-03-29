package user.manager;

import connection.ConPool;
import pacchetto.bean.GestorePacchetti;
import party.bean.*;
import user.bean.*;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashSet;

public class UserManager {

    public static Utente insertUser(String nome, String cognome, String email, String password, String telefono)
    {
        if((nome!=null)&&(cognome!=null)&&(email!=null)&&(password!=null)&&(telefono!=null))
        {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO utente (nome, cognome, email, password, telefono) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstLetterUpperCase(nome));
                ps.setString(2, firstLetterUpperCase(cognome));
                ps.setString(3, email);
                ps.setString(4, hash(password));
                ps.setString(5, telefono);

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int idUtente = rs.getInt(1);

                Utente u = new Utente();
                u.setId(idUtente);
                u.setNome(nome);
                u.setCognome(cognome);
                u.setEmail(email);
                u.setPassword(password);
                u.setTelefono(telefono);

                return u;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static String hash(String text)
    {
        try {
            String text1 = text;
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(text1.getBytes(StandardCharsets.UTF_8));
            text1 = String.format("%040x", new BigInteger(1, digest.digest()));
            return text1;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean insertCliente(Utente u)
    {
        if(u!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO cliente (idCliente) VALUES (?)");
                ps.setInt(1, u.getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }else
            return false;
    }

    public static boolean deleteUser(int idEmployee)
    {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM utente WHERE utente.id=?");
            ps.setInt(1, idEmployee);
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("DELETE error.");
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Gestore insertGestore(Utente u, String tipoGestore) {
        if ((u != null) && (tipoGestore != null)) {
            try (Connection con = ConPool.getConnection()) {
                u = insertUser(u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), u.getTelefono());
                PreparedStatement ps = con.prepareStatement("INSERT INTO gestore (idGestore, tipoGestore) VALUES (?, ?)");
                ps.setInt(1, u.getId());
                ps.setString(2, tipoGestore);

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                Gestore g = new Gestore(u.getNome(), u.getCognome(), u.getEmail(), u.getPassword(), u.getTelefono(), tipoGestore);
                g.setId(u.getId());

                if (g.getTipoGestore() == "contabile") {
                    Contabile c = insertContabile(g);
                } else if (g.getTipoGestore() == "impiegati") {
                    GestoreImpiegati gi = insertGestoreImpiegati(g);
                } else if (g.getTipoGestore() == "pacchetti") {
                    GestorePacchetti gp = insertGestorePacchetti(g);
                } else {
                    GestoreParty gParty = insertGestoreParty(g);
                }
                return g;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static Contabile insertContabile(Gestore g)
    {
        if(g!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO contabile (idContabile) VALUES (?)");
                ps.setInt(1, g.getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                Contabile c = new Contabile(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                c.setId(g.getId());
                return c;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static GestoreParty insertGestoreParty(Gestore g)
    {
        if(g!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO gestoreParty (idGestoreParty) VALUES (?)");
                ps.setInt(1, g.getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                GestoreParty gParty = new GestoreParty(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                gParty.setId(g.getId());
                return gParty;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static GestoreImpiegati insertGestoreImpiegati(Gestore g)
    {
        if(g!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO gestoreImpiegati (idGestoreImpiegati) VALUES (?)");
                ps.setInt(1, g.getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                GestoreImpiegati gi = new GestoreImpiegati(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                gi.setId(g.getId());
                return gi;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static GestorePacchetti insertGestorePacchetti(Gestore g)
    {
        if(g!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO gestorePacchetti (idGestorePacchetti) VALUES (?)");
                ps.setInt(1, g.getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("INSERT error.");
                }

                GestorePacchetti gPacchetti = new GestorePacchetti(g.getNome(), g.getCognome(), g.getEmail(), g.getPassword(), g.getTelefono(), g.getTipoGestore());
                gPacchetti.setId(g.getId());
                return gPacchetti;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static boolean updateUser(Utente u)
    {
        if(u!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("Update utente\n" +
                        "SET email=?, password=?, telefono=?\n" +
                        "WHERE id=?");
                ps.setString(1, u.getEmail());
                ps.setString(2, hash(u.getPassword()));
                ps.setString(3, u.getTelefono());
                ps.setInt(4, u.getId());

                if (ps.executeUpdate() != 1) {
                    throw new RuntimeException("UPDATE error.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }else
            return false;
    }

    public static boolean checkIdByEmail(String email)
    {
        if(email!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT email FROM utente WHERE email=?");
                ps.setString(1, email);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return false;
                } else
                    return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return true;
    }

    public static String firstLetterUpperCase(String str)
    {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Utente login(String email, String pwd)
    {
        if(email!=null && pwd!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email=? AND password=?");
                ps.setString(1, email);
                ps.setString(2, hash(pwd));
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {

                    Utente u = new Utente();
                    u.setId(rs.getInt(1));
                    u.setNome(rs.getString(2));
                    u.setCognome(rs.getString(3));
                    u.setEmail(rs.getString(4));
                    u.setPassword(pwd);
                    u.setTelefono(rs.getString(6));

                    return u;
                }
                return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static Cliente isCliente(Utente u)
    {
        if(u!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT u.nome, u.cognome, u.email, u.password, u.id, u.telefono FROM utente as u, cliente as c WHERE u.id=? AND c.idCliente = u.id");
                ps.setInt(1, u.getId());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Cliente c = new Cliente(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), u.getPassword(), rs.getString("telefono"));
                    c.setId(rs.getInt("id"));
                    return c;
                } else
                    return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }


    public static Artista isArtista(Utente u)
    {
        if(u!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT u.nome, u.cognome, u.email, u.password, u.id, u.telefono, a.tipoArtista \n" +
                        "FROM utente AS u, artista AS a\n" +
                        "WHERE u.id IN (\n" +
                        "\tselect a.idArtista\n" +
                        "    WHERE a.idArtista=u.id AND u.id=?\n" +
                        ")");
                ps.setInt(1, u.getId());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    Artista a = new Artista(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), u.getPassword(), rs.getString("telefono"), rs.getString("tipoArtista"));
                    a.setId(rs.getInt("id"));
                    return a;
                } else
                    return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static Gestore isGestore(Utente u)
    {
        if(u!=null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("SELECT u.nome, u.cognome, u.email, u.password, u.id, u.telefono, g.tipoGestore\n" +
                        "FROM utente AS u, gestore AS g\n" +
                        "WHERE u.id IN (\n" +
                        "\tselect g.idgestore\n" +
                        "    WHERE g.idgestore=u.id AND u.id=?\n" +
                        ")");
                ps.setInt(1, u.getId());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String tipoGestore = rs.getString("tipoGestore");
                    Gestore g = new Gestore(rs.getString("nome"), rs.getString("cognome"), rs.getString("email"), u.getPassword(), rs.getString("telefono"), tipoGestore);
                    g.setId(rs.getInt("id"));
                    return g;
                } else
                    return null;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else
            return null;
    }

    public static HashSet<Gestore> retrieveAllEmployee()
    {
        HashSet<Gestore> collection = new HashSet<Gestore>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT u.id, u.nome, u.cognome, u.email, u.password, u.telefono, g.tipoGestore\n" +
                    "FROM gestore AS g, utente AS u\n" +
                    "WHERE g.idGestore = u.id " +
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
            PreparedStatement ps = con.prepareStatement("SELECT u.id, u.nome, u.cognome, u.email, u.password, u.telefono, a.tipoArtista\n" +
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
}
