package dao;

import model.Penghuni;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenghuniDAO {

    public List<Penghuni> getAllPenghuni() {
        List<Penghuni> list = new ArrayList<>();
        String sql = "SELECT * FROM penghuni";

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Penghuni p = new Penghuni();
                p.setIdPenghuni(rs.getInt("id_penghuni"));
                p.setNama(rs.getString("nama"));
                p.setNoHp(rs.getString("no_hp"));
                p.setIdKamar(rs.getInt("id_kamar"));

                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Gagal ambil data penghuni: " + e.getMessage());
        }
        return list;
    }

    public void tambahPenghuni(Penghuni p) {
        String insertSql = "INSERT INTO penghuni (nama, no_hp, id_kamar) VALUES (?, ?, ?)";
        String updateKamarSql = "UPDATE kamar SET status = 'Terisi' WHERE id_kamar = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // mulai transaksi

            // Insert penghuni
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setString(1, p.getNama());
                stmt.setString(2, p.getNoHp());
                stmt.setInt(3, p.getIdKamar());
                stmt.executeUpdate();
            }

            // Update status kamar jadi Terisi
            try (PreparedStatement stmt = conn.prepareStatement(updateKamarSql)) {
                stmt.setInt(1, p.getIdKamar());
                stmt.executeUpdate();
            }

            conn.commit(); // commit transaksi
            System.out.println("Penghuni berhasil ditambahkan.");
        } catch (SQLException e) {
            System.out.println("Gagal menambahkan penghuni: " + e.getMessage());
        }
    }

    public void updatePenghuni(Penghuni p) {
        String getKamarLamaSql = "SELECT id_kamar FROM penghuni WHERE id_penghuni = ?";
        String updatePenghuniSql = "UPDATE penghuni SET nama = ?, no_hp = ?, id_kamar = ? WHERE id_penghuni = ?";
        String kosongkanKamarLamaSql = "UPDATE kamar SET status = 'Kosong' WHERE id_kamar = ?";
        String isiKamarBaruSql = "UPDATE kamar SET status = 'Terisi' WHERE id_kamar = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int kamarLama = -1;

            // Ambil kamar lama
            try (PreparedStatement stmt = conn.prepareStatement(getKamarLamaSql)) {
                stmt.setInt(1, p.getIdPenghuni());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        kamarLama = rs.getInt("id_kamar");
                    }
                }
            }

            // Update data penghuni
            try (PreparedStatement stmt = conn.prepareStatement(updatePenghuniSql)) {
                stmt.setString(1, p.getNama());
                stmt.setString(2, p.getNoHp());
                stmt.setInt(3, p.getIdKamar());
                stmt.setInt(4, p.getIdPenghuni());
                stmt.executeUpdate();
            }

            // Kosongkan kamar lama jika berbeda
            if (kamarLama != -1 && kamarLama != p.getIdKamar()) {
                try (PreparedStatement stmt = conn.prepareStatement(kosongkanKamarLamaSql)) {
                    stmt.setInt(1, kamarLama);
                    stmt.executeUpdate();
                }
            }

            // Set kamar baru jadi terisi
            try (PreparedStatement stmt = conn.prepareStatement(isiKamarBaruSql)) {
                stmt.setInt(1, p.getIdKamar());
                stmt.executeUpdate();
            }

            conn.commit();
            System.out.println("Penghuni berhasil diupdate.");
        } catch (SQLException e) {
            System.out.println("Gagal update penghuni: " + e.getMessage());
        }
    }

    public void hapusPenghuni(int idPenghuni) {
        String getKamarSql = "SELECT id_kamar FROM penghuni WHERE id_penghuni = ?";
        String deleteSql = "DELETE FROM penghuni WHERE id_penghuni = ?";
        String updateKamarSql = "UPDATE kamar SET status = 'Kosong' WHERE id_kamar = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // mulai transaksi

            // Ambil id_kamar dari penghuni
            int idKamar = -1;
            try (PreparedStatement stmt = conn.prepareStatement(getKamarSql)) {
                stmt.setInt(1, idPenghuni);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idKamar = rs.getInt("id_kamar");
                    }
                }
            }

            // Hapus penghuni
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setInt(1, idPenghuni);
                int rows = stmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Penghuni berhasil dihapus.");
                } else {
                    System.out.println("Penghuni dengan ID tersebut tidak ditemukan.");
                    conn.rollback();
                    return;
                }
            }

            // Update status kamar
            if (idKamar != -1) {
                try (PreparedStatement stmt = conn.prepareStatement(updateKamarSql)) {
                    stmt.setInt(1, idKamar);
                    stmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus penghuni: " + e.getMessage());
        }
    }
}
