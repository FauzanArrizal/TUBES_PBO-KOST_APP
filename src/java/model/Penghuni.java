package model;

public class Penghuni {

    private int idPenghuni;
    private String nama;
    private String noHp;
    private int idKamar;  // foreign key

    public Penghuni() {
    }

    public Penghuni(int idPenghuni, String nama, String noHp, int idKamar) {
        this.idPenghuni = idPenghuni;
        this.nama = nama;
        this.noHp = noHp;
        this.idKamar = idKamar;
    }

    public int getIdPenghuni() {
        return idPenghuni;
    }

    public void setIdPenghuni(int idPenghuni) {
        this.idPenghuni = idPenghuni;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }
}
