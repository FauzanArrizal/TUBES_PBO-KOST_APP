/*
 * File: KamarDAO.java
 * Package: dao
 * Deskripsi: Kelas Data Access Object (DAO) untuk entitas Kamar.
 * Bertanggung jawab untuk semua operasi CRUD (Create, Read, Update, Delete)
 * dan logika bisnis lain yang berkaitan dengan data kamar di database.
 */
package dao;

import model.Kamar;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    /**
     * Mengambil semua data kamar dari database, diurutkan berdasarkan nomor
     * kamar.
     *
     * @return Sebuah List yang berisi semua objek Kamar.
     */
    public List<Kamar> getAllKamar() {
        List<Kamar> daftarKamar = new ArrayList<>();
        String sql = "SELECT * FROM kamar ORDER BY nomor_kamar ASC";
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Kamar k = new Kamar();
                k.setIdKamar(rs.getInt("id_kamar"));
                k.setNomorKamar(rs.getString("nomor_kamar"));
                k.setTipeKamar(rs.getString("tipe_kamar"));
                k.setHargaPerBulan(rs.getDouble("harga_per_bulan"));
                k.setStatus(rs.getString("status"));
                daftarKamar.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarKamar;
    }

    /**
     * Menambahkan kamar baru ke dalam database. Status kamar baru secara
     * default diatur menjadi 'KOSONG'.
     *
     * @param kamar Objek Kamar yang berisi data dari form.
     */
    public void tambahKamar(Kamar kamar) {
        String sql = "INSERT INTO kamar (nomor_kamar, tipe_kamar, harga_per_bulan, status) VALUES (?, ?, ?, 'KOSONG')";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar.getNomorKamar());
            ps.setString(2, kamar.getTipeKamar());
            ps.setDouble(3, kamar.getHargaPerBulan());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengambil daftar kamar berdasarkan status tertentu (KOSONG atau TERISI).
     * Digunakan untuk mengisi dropdown pada form tambah penghuni.
     *
     * @param status Status yang dicari ('KOSONG' atau 'TERISI').
     * @return List objek Kamar yang sesuai dengan status.
     */
    public List<Kamar> getKamarByStatus(String status) {
        List<Kamar> daftarKamar = new ArrayList<>();
        String sql = "SELECT * FROM kamar WHERE status = ? ORDER BY nomor_kamar ASC";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Kamar k = new Kamar();
                k.setIdKamar(rs.getInt("id_kamar"));
                k.setNomorKamar(rs.getString("nomor_kamar"));
                k.setTipeKamar(rs.getString("tipe_kamar"));
                k.setHargaPerBulan(rs.getDouble("harga_per_bulan"));
                k.setStatus(rs.getString("status"));
                daftarKamar.add(k);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarKamar;
    }

    /**
     * Menghitung jumlah kamar berdasarkan statusnya. Digunakan untuk dashboard.
     *
     * @param status Status yang akan dihitung.
     * @return Jumlah total kamar dengan status tersebut.
     */
    public int hitungKamarByStatus(String status) {
        String sql = "SELECT COUNT(*) AS total FROM kamar WHERE status = ?";
        int total = 0;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * Memperbarui status sebuah kamar (misalnya dari KOSONG menjadi TERISI).
     * Method ini dipanggil oleh DAO lain (PenghuniDAO) untuk menjalankan logika
     * bisnis.
     *
     * @param idKamar ID kamar yang akan diupdate.
     * @param status Status baru untuk kamar tersebut.
     */
    public void updateStatusKamar(int idKamar, String status) {
        String sql = "UPDATE kamar SET status = ? WHERE id_kamar = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, idKamar);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menghapus data kamar dari database berdasarkan ID. Ada pengaman di sisi
     * SQL untuk memastikan hanya kamar 'KOSONG' yang bisa dihapus.
     *
     * @param idKamar ID dari kamar yang akan dihapus.
     */
    public void hapusKamar(int idKamar) {
        // klausa "AND status = 'KOSONG'" adalah pengaman tambahan di level database.
        String sql = "DELETE FROM kamar WHERE id_kamar = ? AND status = 'KOSONG'";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKamar);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengambil satu data kamar berdasarkan ID-nya. Digunakan untuk mengisi
     * form edit dengan data yang sudah ada.
     *
     * @param idKamar ID kamar yang akan diambil.
     * @return Objek Kamar atau null jika tidak ditemukan.
     */
    public Kamar getKamarById(int idKamar) {
        Kamar kamar = null;
        String sql = "SELECT * FROM kamar WHERE id_kamar = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKamar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kamar = new Kamar();
                kamar.setIdKamar(rs.getInt("id_kamar"));
                kamar.setNomorKamar(rs.getString("nomor_kamar"));
                kamar.setTipeKamar(rs.getString("tipe_kamar"));
                kamar.setHargaPerBulan(rs.getDouble("harga_per_bulan"));
                kamar.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kamar;
    }

    /**
     * Memperbarui data kamar di database.
     *
     * @param kamar Objek Kamar yang berisi data baru dari form edit.
     */
    public void updateKamar(Kamar kamar) {
        String sql = "UPDATE kamar SET nomor_kamar = ?, tipe_kamar = ?, harga_per_bulan = ? WHERE id_kamar = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kamar.getNomorKamar());
            ps.setString(2, kamar.getTipeKamar());
            ps.setDouble(3, kamar.getHargaPerBulan());
            ps.setInt(4, kamar.getIdKamar());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
