/*
 * File: CreateAdminUser.java
 * Package: utils
 * Deskripsi: Skrip utilitas sekali pakai untuk membuat pengguna admin pertama di database.
 * Skrip ini sangat penting karena aplikasi tidak memiliki fitur registrasi publik.
 * Penggunaan utama skrip ini adalah untuk meng-hash password menggunakan jBCrypt
 * sebelum menyimpannya, sehingga password asli tidak pernah disimpan sebagai teks biasa.
 */
package utils;

// Mengimpor library BCrypt untuk hashing password.
import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAdminUser {

    /**
     * Method main ini bisa dijalankan sekali saja langsung dari IDE (Klik kanan
     * -> Run File). Tujuannya adalah untuk mengisi tabel 'users' dengan data
     * admin awal.
     */
    public static void main(String[] args) {
        // Tentukan username dan password yang diinginkan untuk admin.
        String username = "admin";
        String password = "admin123"; // Ini adalah password teks biasa (plaintext).

        // Langkah 1: Mengamankan password.
        // Method BCrypt.hashpw() akan mengubah password teks biasa menjadi sebuah hash
        // yang sangat sulit untuk dikembalikan ke bentuk semula (one-way encryption).
        // BCrypt.gensalt() menambahkan "garam" (salt) acak untuk meningkatkan keamanan.
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Mencetak password yang sudah di-hash ke console untuk tujuan edukasi/verifikasi.
        System.out.println("Password Asli: " + password);
        System.out.println("Password yang di-Hash: " + hashedPassword);

        // Langkah 2: Menyimpan data admin ke database.
        String sql = "INSERT INTO users (username, password_hash, nama_lengkap) VALUES (?, ?, ?)";

        // Menggunakan try-with-resources untuk koneksi dan statement yang aman.
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Mengisi parameter pada query SQL untuk mencegah SQL Injection.
            ps.setString(1, username);

            // Menyimpan HASH dari password, bukan password aslinya.
            ps.setString(2, hashedPassword);
            ps.setString(3, "Pemilik Kost");

            // Menjalankan perintah INSERT dan memeriksa apakah berhasil.
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("\nUser admin berhasil dibuat di database!");
            }

        } catch (SQLException e) {
            // Menangani error jika username 'admin' sudah ada di database.
            System.err.println("Gagal membuat user admin. Mungkin username 'admin' sudah ada?");
            e.printStackTrace();
        }
    }
}
