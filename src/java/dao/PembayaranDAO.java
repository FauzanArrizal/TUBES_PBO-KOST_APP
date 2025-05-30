package dao;

import model.Pembayaran;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PembayaranDAO {

    public List<Pembayaran> getAllPembayaran() {
        List<Pembayaran> list = new ArrayList<>();
        String sql = "SELECT * FROM pembayaran";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(rs.getInt("id_pembayaran"));
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setJumlah(rs.getDouble("jumlah"));
                p.setTanggalPembayaran(rs.getDate("tanggal_pembayaran"));

                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data pembayaran: " + e.getMessage());
        }
        return list;
    }

    public void tambahPembayaran(Pembayaran p) {
        String sql = "INSERT INTO pembayaran (id_penghuni, jumlah, tanggal_pembayaran) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdPenghuni());
            stmt.setDouble(2, p.getJumlah());
            stmt.setDate(3, p.getTanggalPembayaran());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Pembayaran berhasil ditambahkan.");
            } else {
                System.out.println("Gagal menambahkan pembayaran.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan pembayaran: " + e.getMessage());
        }
    }

    public void updatePembayaran(Pembayaran p) {
        String sql = "UPDATE pembayaran SET id_penghuni = ?, jumlah = ?, tanggal_pembayaran = ? WHERE id_pembayaran = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, p.getIdPenghuni());
            stmt.setDouble(2, p.getJumlah());
            stmt.setDate(3, p.getTanggalPembayaran());
            stmt.setInt(4, p.getIdPembayaran());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Pembayaran berhasil diupdate.");
            } else {
                System.out.println("Pembayaran tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal update pembayaran: " + e.getMessage());
        }
    }

    public void hapusPembayaran(int idPembayaran) {
        String sql = "DELETE FROM pembayaran WHERE id_pembayaran = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPembayaran);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Pembayaran berhasil dihapus.");
            } else {
                System.out.println("Pembayaran dengan ID tersebut tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal menghapus pembayaran: " + e.getMessage());
        }
    }
}
