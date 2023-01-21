package party;

public class Fornitore {
    private int id, telefono;
    private String nomeAzienda, proprietario, tipoFornitore;

    public Fornitore(int telefono, String nomeAzienda, String proprietario, String tipoFornitore) {

        this.telefono = telefono;
        this.nomeAzienda = nomeAzienda;
        this.proprietario = proprietario;
        this.tipoFornitore = tipoFornitore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getNomeAzienda() {
        return nomeAzienda;
    }

    public void setNomeAzienda(String nomeAzienda) {
        this.nomeAzienda = nomeAzienda;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    public String getTipoFornitore() {
        return tipoFornitore;
    }

    public void setTipoFornitore(String tipoFornitore) {
        this.tipoFornitore = tipoFornitore;
    }
}
