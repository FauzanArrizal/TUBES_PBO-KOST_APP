/*
 * File: Kamar.java
 * Package: model
 * Deskripsi: Kelas POJO (Plain Old Java Object) yang merepresentasikan entitas 'kamar' 
 * dari database. Setiap objek dari kelas ini akan menampung data untuk satu baris 
 * dari tabel 'kamar'. Ini adalah bagian dari lapisan Model dalam arsitektur MVC.
 */
package model;

public class Kamar {

    // Properti-properti ini merepresentasikan kolom-kolom pada tabel 'kamar'.
    // Penggunaan tipe data yang sesuai (int, String, double) memastikan integritas data.
    private int idKamar;
    private String nomorKamar;
    private String tipeKamar;
    private double hargaPerBulan;
    private String status; // Status kamar: 'KOSONG' atau 'TERISI'

    // --- GETTER DAN SETTER ---
    // Method-method ini menyediakan akses terkontrol (encapsulation) ke properti privat di atas.
    // Getter digunakan untuk membaca nilai, dan Setter untuk mengubah nilai.
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

    public String getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(String tipeKamar) {
        this.tipeKamar = tipeKamar;
    }

    public double getHargaPerBulan() {
        return hargaPerBulan;
    }

    public void setHargaPerBulan(double hargaPerBulan) {
        this.hargaPerBulan = hargaPerBulan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
