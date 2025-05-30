package model;

public class Kamar {

    private int idKamar;
    private String noKamar;
    private String tipe;
    private double harga;
    private String status;

    public Kamar() {
    }

    public Kamar(int idKamar, String noKamar, String tipe, double harga, String status) {
        this.idKamar = idKamar;
        this.noKamar = noKamar;
        this.tipe = tipe;
        this.harga = harga;
        this.status = status;
    }

    // Getter & Setter
    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }

    public String getNoKamar() {
        return noKamar;
    }

    public void setNoKamar(String noKamar) {
        this.noKamar = noKamar;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
