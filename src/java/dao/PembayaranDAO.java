/*
 * File: PembayaranDAO.java
 * Package: dao
 * Deskripsi: Kelas DAO untuk entitas Pembayaran. Mengelola semua data transaksi
 * keuangan dan laporan pendapatan.
 */
package dao;

import model.Pembayaran;
import utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PembayaranDAO {

    /**
     * Mengambil semua riwayat pembayaran, bisa difilter berdasarkan nama
     * penghuni.
     *
     * @param keyword Kata kunci untuk mencari nama penghuni. Jika null atau
     * kosong, ambil semua.
     * @return List objek Pembayaran yang sudah diperkaya dengan data lain.
     */
    public List<Pembayaran> getAllPembayaran(String keyword) {
        List<Pembayaran> daftarPembayaran = new ArrayList<>();
        String sql = "SELECT p.*, ph.nama_lengkap, k.nomor_kamar "
                + "FROM pembayaran p "
                + "JOIN penghuni ph ON p.id_penghuni = ph.id_penghuni "
                + "LEFT JOIN kamar k ON ph.id_kamar = k.id_kamar ";

        // Jika ada kata kunci, tambahkan kondisi WHERE ... LIKE
        if (keyword != null && !keyword.isEmpty()) {
            sql += "WHERE ph.nama_lengkap LIKE ? ";
        }

        sql += "ORDER BY p.tanggal_pembayaran DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set parameter untuk klausa LIKE jika ada keyword
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, "%" + keyword + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setTanggalPembayaran(rs.getDate("tanggal_pembayaran"));
                p.setJumlah(rs.getDouble("jumlah"));
                p.setKeterangan(rs.getString("keterangan"));
                p.setNamaPenghuni(rs.getString("nama_lengkap"));
                p.setNomorKamar(rs.getString("nomor_kamar"));
                daftarPembayaran.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarPembayaran;
    }

    /**
     * Menambahkan data pembayaran baru dan MENGEMBALIKAN ID yang baru dibuat.
     *
     * @param p Objek Pembayaran berisi data dari form.
     * @return ID dari pembayaran yang baru dibuat, atau 0 jika gagal.
     */
    public int tambahPembayaran(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (id_penghuni, tanggal_pembayaran, jumlah, keterangan) VALUES (?, ?, ?, ?)";
        int generatedId = 0;

        // Statement.RETURN_GENERATED_KEYS adalah perintah untuk meminta ID yang baru dibuat oleh database.
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getIdPenghuni());
            ps.setDate(2, new java.sql.Date(p.getTanggalPembayaran().getTime()));
            ps.setDouble(3, p.getJumlah());
            ps.setString(4, p.getKeterangan());

            int affectedRows = ps.executeUpdate();

            // Jika insert berhasil, ambil ID yang digenerate oleh database.
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * Menghitung total pendapatan dalam rentang tanggal tertentu. Digunakan
     * untuk fitur Laporan.
     *
     * @param tanggalMulai Tanggal awal periode.
     * @param tanggalSelesai Tanggal akhir periode.
     * @return List objek Pembayaran dalam rentang tanggal tersebut.
     */
    public List<Pembayaran> getPembayaranByTanggal(Date tanggalMulai, Date tanggalSelesai) {
        List<Pembayaran> daftarPembayaran = new ArrayList<>();
        String sql = "SELECT p.*, ph.nama_lengkap, k.nomor_kamar "
                + "FROM pembayaran p "
                + "JOIN penghuni ph ON p.id_penghuni = ph.id_penghuni "
                + "LEFT JOIN kamar k ON ph.id_kamar = k.id_kamar "
                + "WHERE p.tanggal_pembayaran BETWEEN ? AND ? "
                + "ORDER BY p.tanggal_pembayaran ASC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(tanggalMulai.getTime()));
            ps.setDate(2, new java.sql.Date(tanggalSelesai.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setTanggalPembayaran(rs.getDate("tanggal_pembayaran"));
                p.setJumlah(rs.getDouble("jumlah"));
                p.setKeterangan(rs.getString("keterangan"));
                p.setNamaPenghuni(rs.getString("nama_lengkap"));
                p.setNomorKamar(rs.getString("nomor_kamar"));
                daftarPembayaran.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarPembayaran;
    }

    /**
     * Menghitung total pendapatan HANYA untuk bulan dan tahun berjalan.
     * Menggunakan fungsi SQL (SUM, MONTH, YEAR, CURDATE) untuk efisiensi.
     *
     * @return Total pendapatan dalam double.
     */
    public double hitungPendapatanBulanIni() {
        String sql = "SELECT SUM(jumlah) AS total FROM pembayaran WHERE MONTH(tanggal_pembayaran) = MONTH(CURDATE()) AND YEAR(tanggal_pembayaran) = YEAR(CURDATE())";
        double total = 0.0;
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                total = rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * Menghapus data pembayaran dari database berdasarkan ID-nya.
     *
     * @param idPembayaran ID dari pembayaran yang akan dihapus.
     */
    public void hapusPembayaran(int idPembayaran) {
        String sql = "DELETE FROM pembayaran WHERE id_pembayaran = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPembayaran);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mengambil satu data pembayaran berdasarkan ID-nya. Digunakan untuk
     * mengisi form edit dengan data yang sudah ada.
     *
     * @param idPembayaran ID pembayaran yang akan diambil.
     * @return Objek Pembayaran atau null jika tidak ditemukan.
     */
    public Pembayaran getPembayaranById(int idPembayaran) {
        Pembayaran p = null;
        // Query ini mengambil semua data yang dibutuhkan, termasuk nama penghuni dan nomor kamar
        String sql = "SELECT p.*, ph.nama_lengkap, k.nomor_kamar "
                + "FROM pembayaran p "
                + "JOIN penghuni ph ON p.id_penghuni = ph.id_penghuni "
                + "LEFT JOIN kamar k ON ph.id_kamar = k.id_kamar "
                + "WHERE p.id_pembayaran = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPembayaran);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setTanggalPembayaran(rs.getDate("tanggal_pembayaran"));
                p.setJumlah(rs.getDouble("jumlah"));
                p.setKeterangan(rs.getString("keterangan"));
                p.setNamaPenghuni(rs.getString("nama_lengkap"));
                p.setNomorKamar(rs.getString("nomor_kamar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    /**
     * Memperbarui data pembayaran di database.
     *
     * @param pembayaran Objek Pembayaran yang berisi data baru dari form edit.
     */
    public void updatePembayaran(Pembayaran pembayaran) {
        String sql = "UPDATE pembayaran SET tanggal_pembayaran = ?, jumlah = ?, keterangan = ? WHERE id_pembayaran = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, new java.sql.Date(pembayaran.getTanggalPembayaran().getTime()));
            ps.setDouble(2, pembayaran.getJumlah());
            ps.setString(3, pembayaran.getKeterangan());
            ps.setInt(4, pembayaran.getIdPembayaran());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
