package dao;

import model.Kamar;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KamarDAO {

    public List<Kamar> getAllKamar() {
        List<Kamar> list = new ArrayList<>();
        String sql = "SELECT * FROM kamar";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Kamar k = new Kamar();
                k.setIdKamar(rs.getInt("id_kamar"));
                k.setNoKamar(rs.getString("no_kamar"));
                k.setTipe(rs.getString("tipe"));
                k.setHarga(rs.getDouble("harga"));
                k.setStatus(rs.getString("status"));

                list.add(k);
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data kamar: " + e.getMessage());
        }
        return list;
    }

    public void tambahKamar(Kamar k) {
        String sql = "INSERT INTO kamar (no_kamar, tipe, harga, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, k.getNoKamar());
            stmt.setString(2, k.getTipe());
            stmt.setDouble(3, k.getHarga());
            stmt.setString(4, k.getStatus());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Kamar berhasil ditambahkan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal tambah kamar: " + e.getMessage());
        }
    }

    public void updateKamar(Kamar k) {
        String sql = "UPDATE kamar SET no_kamar = ?, tipe = ?, harga = ?, status = ? WHERE id_kamar = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, k.getNoKamar());
            stmt.setString(2, k.getTipe());
            stmt.setDouble(3, k.getHarga());
            stmt.setString(4, k.getStatus());
            stmt.setInt(5, k.getIdKamar());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Kamar berhasil diupdate.");
            } else {
                System.out.println("Kamar tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal update kamar: " + e.getMessage());
        }
    }

    public void hapusKamar(int idKamar) {
        String sql = "DELETE FROM kamar WHERE id_kamar = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idKamar);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Kamar berhasil dihapus.");
            } else {
                System.out.println("Kamar tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Gagal hapus kamar: " + e.getMessage());
        }
    }
}
