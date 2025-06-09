/*
 * File: PenghuniDAO.java
 * Package: dao
 * Deskripsi: Kelas DAO untuk entitas Penghuni. Menangani semua operasi database
 * untuk penghuni dan logika bisnis terkait, seperti mengupdate status kamar
 * saat menambah atau menghapus penghuni.
 */
package dao;

import model.Penghuni;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenghuniDAO {

    /**
     * Mengambil semua penghuni yang AKTIF, bisa difilter berdasarkan nama.
     * Menggunakan LEFT JOIN untuk tetap menampilkan nomor kamar meskipun
     * relasinya bisa NULL.
     *
     * @param keyword Kata kunci untuk mencari nama. Jika null, ambil semua.
     * @return List objek Penghuni yang aktif.
     */
    public List<Penghuni> getAllPenghuni(String keyword) {
        List<Penghuni> daftarPenghuni = new ArrayList<>();
        String sql = "SELECT p.*, k.nomor_kamar FROM penghuni p "
                + "LEFT JOIN kamar k ON p.id_kamar = k.id_kamar "
                + "WHERE p.status_penghuni = 'AKTIF' ";

        if (keyword != null && !keyword.isEmpty()) {
            sql += "AND p.nama_lengkap LIKE ? ";
        }
        sql += "ORDER BY p.nama_lengkap ASC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Penghuni p = new Penghuni();
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setNamaLengkap(rs.getString("nama_lengkap"));
                p.setNomorTelepon(rs.getString("nomor_telepon"));
                p.setAsalKota(rs.getString("asal_kota"));
                p.setStatusPenghuni(rs.getString("status_penghuni"));
                p.setIdKamar(rs.getInt("id_kamar"));
                p.setNomorKamar(rs.getString("nomor_kamar"));
                daftarPenghuni.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarPenghuni;
    }

    /**
     * Menambah penghuni baru dan langsung mengubah status kamar menjadi TERISI.
     * Ini adalah contoh logika bisnis yang diimplementasikan di lapisan DAO.
     *
     * @param penghuni Objek Penghuni berisi data lengkap dari form.
     */
    public void tambahPenghuni(Penghuni penghuni) {
        String sqlInsert = "INSERT INTO penghuni (nama_lengkap, nomor_telepon, asal_kota, id_kamar) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlInsert)) {
            ps.setString(1, penghuni.getNamaLengkap());
            ps.setString(2, penghuni.getNomorTelepon());
            ps.setString(3, penghuni.getAsalKota());
            ps.setInt(4, penghuni.getIdKamar());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (penghuni.getIdKamar() > 0) {
            KamarDAO kamarDAO = new KamarDAO();
            kamarDAO.updateStatusKamar(penghuni.getIdKamar(), "TERISI");
        }
    }

    /**
     * Melakukan 'Soft Delete'. Data penghuni tidak dihapus, tetapi statusnya
     * diubah menjadi 'TIDAK AKTIF' dan relasinya dengan kamar diputus (id_kamar
     * = NULL). Setelah itu, status kamar yang ditinggalkan diubah kembali
     * menjadi 'KOSONG'.
     *
     * @param idPenghuni ID penghuni yang akan dinonaktifkan.
     * @param idKamar ID kamar yang akan dikosongkan.
     */
    public void hapusPenghuni(int idPenghuni, int idKamar) {
        String sqlUpdatePenghuni = "UPDATE penghuni SET status_penghuni = 'TIDAK AKTIF', id_kamar = NULL WHERE id_penghuni = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlUpdatePenghuni)) {
            ps.setInt(1, idPenghuni);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (idKamar > 0) {
            KamarDAO kamarDAO = new KamarDAO();
            kamarDAO.updateStatusKamar(idKamar, "KOSONG");
        }
    }

    /**
     * Mengambil satu data penghuni berdasarkan ID-nya untuk keperluan form
     * edit.
     *
     * @param id ID penghuni yang dicari.
     * @return Objek Penghuni atau null jika tidak ada.
     */
    public Penghuni getPenghuniById(int id) {
        Penghuni p = null;
        String sql = "SELECT * FROM penghuni WHERE id_penghuni = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Penghuni();
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setNamaLengkap(rs.getString("nama_lengkap"));
                p.setNomorTelepon(rs.getString("nomor_telepon"));
                p.setAsalKota(rs.getString("asal_kota"));
                p.setIdKamar(rs.getInt("id_kamar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * Memperbarui informasi dasar penghuni (nama, telepon, kota).
     *
     * @param penghuni Objek Penghuni dengan data baru.
     */
    public void updatePenghuni(Penghuni penghuni) {
        String sql = "UPDATE penghuni SET nama_lengkap = ?, nomor_telepon = ?, asal_kota = ? WHERE id_penghuni = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, penghuni.getNamaLengkap());
            ps.setString(2, penghuni.getNomorTelepon());
            ps.setString(3, penghuni.getAsalKota());
            ps.setInt(4, penghuni.getIdPenghuni());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Menghitung jumlah total penghuni yang statusnya 'AKTIF' untuk ditampilkan
     * di dashboard.
     *
     * @return Jumlah total penghuni aktif.
     */
    public int hitungTotalPenghuni() {
        String sql = "SELECT COUNT(*) AS total FROM penghuni WHERE status_penghuni = 'AKTIF'";
        int total = 0;
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
