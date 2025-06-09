/*
 * File: DatabaseConnection.java
 * Package: utils
 * Deskripsi: Kelas utilitas untuk mengelola koneksi ke database MySQL.
 * Kelas ini menerapkan pola Singleton sederhana untuk memastikan bahwa hanya ada 
 * satu mekanisme koneksi yang digunakan di seluruh aplikasi, sehingga lebih efisien 
 * dan mudah dikelola.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // URL koneksi JDBC untuk MySQL. Formatnya: jdbc:mysql://[host]:[port]/[nama_database]
    private static final String URL = "jdbc:mysql://localhost:3306/db_kost";

    // Username untuk login ke database. Defaultnya adalah "root".
    private static final String USER = "root"; // Ganti jika perlu

    // Password untuk login ke database. Defaultnya kosong.
    private static final String PASSWORD = ""; // Ganti jika perlu

    /**
     * Method static untuk mendapatkan objek koneksi ke database. Method ini
     * dipanggil oleh semua kelas DAO setiap kali mereka perlu berinteraksi
     * dengan database.
     *
     * @return Objek Connection jika berhasil, atau null jika terjadi error.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Langkah 1: Mendaftarkan driver JDBC MySQL ke dalam aplikasi.
            // Ini memberitahu Java bagaimana cara berkomunikasi dengan database MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Langkah 2: Membuka koneksi ke database menggunakan URL, username, dan password.
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            // Menangani dua jenis exception:
            // ClassNotFoundException: Jika file JAR driver MySQL tidak ditemukan.
            // SQLException: Jika terjadi kegagalan saat menyambung ke database (misalnya, password salah atau server down).
            e.printStackTrace();
        }

        // Mengembalikan objek koneksi yang sudah berhasil dibuat.
        return connection;
    }
}
