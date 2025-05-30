package model;

import java.sql.Date;

public class Pembayaran {
    private int idPembayaran;
    private int idPenghuni;
    private double jumlah;
    private Date tanggalPembayaran;

    public Pembayaran() {}

    public Pembayaran(int idPembayaran, int idPenghuni, double jumlah, Date tanggalPembayaran) {
        this.idPembayaran = idPembayaran;
        this.idPenghuni = idPenghuni;
        this.jumlah = jumlah;
        this.tanggalPembayaran = tanggalPembayaran;
    }

    public Pembayaran(int idPenghuni, double jumlah, Date tanggalPembayaran) {
        this.idPenghuni = idPenghuni;
        this.jumlah = jumlah;
        this.tanggalPembayaran = tanggalPembayaran;
    }

    // getter & setter

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

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public Date getTanggalPembayaran() {
        return tanggalPembayaran;
    }

    public void setTanggalPembayaran(Date tanggalPembayaran) {
        this.tanggalPembayaran = tanggalPembayaran;
    }
}
