package utils;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Koneksi berhasil!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
