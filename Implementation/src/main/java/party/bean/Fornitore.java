package party.bean;

import java.util.Iterator;

public class Fornitore {
    private int id;
    private double prezzo;
    private String nomeAzienda, proprietario, tipoFornitore, telefono;

    public Fornitore(){

    }
    public Fornitore(String telefono, String nomeAzienda, String proprietario, String tipoFornitore, double prezzo) {
        this.telefono = telefono;
        this.nomeAzienda = nomeAzienda;
        this.proprietario = proprietario;
        this.tipoFornitore = tipoFornitore;
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
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
