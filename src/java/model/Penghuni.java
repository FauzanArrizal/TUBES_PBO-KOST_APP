/*
 * File: Penghuni.java
 * Package: model
 * Deskripsi: Kelas POJO yang merepresentasikan entitas 'penghuni'.
 * Selain merepresentasikan kolom dari tabel 'penghuni', kelas ini juga memiliki
 * properti tambahan (nomorKamar) untuk menampung data hasil JOIN dengan tabel 'kamar',
 * menunjukkan adanya relasi antar data.
 */
package model;

public class Penghuni {

    // Properti dari tabel 'penghuni'
    private int idPenghuni;
    private String namaLengkap;
    private String nomorTelepon;
    private String asalKota;
    private String statusPenghuni; // Status 'AKTIF' atau 'TIDAK AKTIF' untuk soft delete
    private int idKamar;           // Foreign Key yang menghubungkan ke tabel 'kamar'

    // Properti tambahan untuk menampung data dari tabel lain (hasil JOIN)
    private String nomorKamar;

    // Constructor kosong (default) diperlukan oleh beberapa framework/library.
    public Penghuni() {
    }

    // --- GETTER DAN SETTER ---
    // Menyediakan akses terkontrol ke semua properti kelas.
    public int getIdPenghuni() {
        return idPenghuni;
    }

    public void setIdPenghuni(int idPenghuni) {
        this.idPenghuni = idPenghuni;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public void setNomorTelepon(String nomorTelepon) {
        this.nomorTelepon = nomorTelepon;
    }

    public String getAsalKota() {
        return asalKota;
    }

    public void setAsalKota(String asalKota) {
        this.asalKota = asalKota;
    }

    public String getStatusPenghuni() {
        return statusPenghuni;
    }

    public void setStatusPenghuni(String statusPenghuni) {
        this.statusPenghuni = statusPenghuni;
    }

    public int getIdKamar() {
        return idKamar;
    }

    public void setIdKamar(int idKamar) {
        this.idKamar = idKamar;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }
}
