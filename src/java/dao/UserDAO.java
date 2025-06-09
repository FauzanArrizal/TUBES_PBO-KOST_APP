/*
 * File: UserDAO.java
 * Package: dao
 * Deskripsi: Kelas DAO untuk entitas User. Bertanggung jawab khusus untuk
 * operasi database yang berkaitan dengan autentikasi pengguna.
 */
package dao;

import model.User;
import utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Mengambil satu data user dari database berdasarkan username-nya. Method
     * ini adalah langkah pertama dalam proses verifikasi login.
     *
     * @param username Username yang akan dicari.
     * @return Objek User yang berisi semua data (termasuk password hash) jika
     * username ditemukan, atau mengembalikan null jika tidak ditemukan.
     */
    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
        // Menggunakan PreparedStatement untuk keamanan terhadap SQL Injection.
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            // Jika query menghasilkan satu baris, berarti user ditemukan.
            if (rs.next()) {
                user = new User();
                user.setIdUser(rs.getInt("id_user"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setNamaLengkap(rs.getString("nama_lengkap"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
