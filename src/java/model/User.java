/*
 * File: User.java
 * Package: model
 * Deskripsi: Kelas POJO yang merepresentasikan entitas 'users' untuk keperluan autentikasi.
 * Kelas ini menyimpan informasi login admin, termasuk hash dari password, bukan password aslinya,
 * untuk menjaga keamanan sistem.
 */
package model;

public class User {

    private int idUser;
    private String username;
    private String passwordHash; // Menyimpan password yang sudah di-hash, bukan teks biasa
    private String namaLengkap;

    // --- GETTER DAN SETTER ---
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }
}
