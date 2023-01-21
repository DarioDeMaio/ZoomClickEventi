package pacchetto;

public class Pacchetto {
    private int id;
    private String titolo, eventiConsigliati;
    private double prezzo;
    private int flag;

    public Pacchetto(String titolo, String eventiConsigliati, double prezzo, int flag) {
        this.titolo = titolo;
        this.eventiConsigliati = eventiConsigliati;
        this.prezzo = prezzo;
        this.flag = flag;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getEventiConsigliati() {
        return eventiConsigliati;
    }

    public void setEventiConsigliati(String eventiConsigliati) {
        this.eventiConsigliati = eventiConsigliati;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }


}
