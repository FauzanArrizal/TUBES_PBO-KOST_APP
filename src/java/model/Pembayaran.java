/*
 * File: Pembayaran.java
 * Package: model
 * Deskripsi: Kelas POJO yang merepresentasikan entitas 'pembayaran' (transaksi).
 * Kelas ini adalah contoh yang baik dari model yang menampung data dari beberapa tabel
 * sekaligus (pembayaran, penghuni, kamar) untuk kebutuhan penampilan data yang informatif.
 */
package model;

import java.util.Date; // Mengimpor kelas Date untuk menangani data tanggal.

public class Pembayaran {

    // Properti dari tabel 'pembayaran'
    private int idPembayaran;
    private int idPenghuni; // Foreign Key ke tabel 'penghuni'
    private Date tanggalPembayaran;
    private double jumlah;
    private String keterangan;

    // Properti tambahan dari hasil JOIN untuk tampilan yang lebih ramah pengguna
    private String namaPenghuni;
    private String nomorKamar;

    // --- GETTER DAN SETTER ---
    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public int getIdPenghuni() {
        return idPenghuni;
    }

    public void setIdPenghuni(int idPenghuni) {
        this.idPenghuni = idPenghuni;
    }

    public Date getTanggalPembayaran() {
        return tanggalPembayaran;
    }

    public void setTanggalPembayaran(Date tanggalPembayaran) {
        this.tanggalPembayaran = tanggalPembayaran;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNamaPenghuni() {
        return namaPenghuni;
    }

    public void setNamaPenghuni(String namaPenghuni) {
        this.namaPenghuni = namaPenghuni;
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public void setNomorKamar(String nomorKamar) {
        this.nomorKamar = nomorKamar;
    }
}
