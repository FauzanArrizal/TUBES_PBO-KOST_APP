# Aplikasi Manajemen Kost Berbasis Web

![Status Projek](https://img.shields.io/badge/Status-Selesai-brightgreen)
![Lisensi](https://img.shields.io/badge/Lisensi-MIT-blue.svg)

Aplikasi berbasis web untuk manajemen operasional rumah kost, dirancang untuk menggantikan metode manual yang inefisien dan rentan kesalahan. Dibangun dengan arsitektur MVC (Model-View-Controller) menggunakan teknologi Java Servlet, JSP, dan MySQL.

## Daftar Isi

- [Fitur Utama](#fitur-utama)
- [Latar Belakang & Permasalahan](#latar-belakang--permasalahan)
- [Tujuan & Manfaat](#tujuan--manfaat)
- [Arsitektur Sistem](#arsitektur-sistem)
- [Teknologi yang Digunakan](#teknologi-yang-digunakan)
- [Instalasi & Konfigurasi](#instalasi--konfigurasi)
- [Penggunaan Aplikasi](#penggunaan-aplikasi)
- [Struktur Proyek](#struktur-proyek)
- [Verifikasi & Validasi (Pengujian)](#verifikasi--validasi-pengujian)
- [Saran Pengembangan](#saran-pengembangan)
- [Tim Pengembang](#tim-pengembang)
- [Lisensi](#lisensi)

---

## Fitur Utama

Aplikasi ini menyediakan fungsionalitas komprehensif untuk mengelola data kost, meliputi:

* **Manajemen Kamar:**
    * CRUD (Create, Read, Update, Delete) data kamar.
    * Update status kamar secara otomatis (Terisi/Kosong) berdasarkan penempatan penghuni.
* **Manajemen Penghuni:**
    * CRUD data penghuni.
    * Implementasi **Soft Delete** untuk penghuni (status diubah menjadi TIDAK AKTIF, riwayat pembayaran tetap tersimpan).
* **Manajemen Pembayaran:**
    * CRUD catatan pembayaran penghuni.
    * Terhubung langsung dengan data penghuni.
* **Keamanan Aplikasi:**
    * Sistem **Login** dengan validasi kredensial.
    * Penggunaan **Hashing Password** dengan `jBCrypt` untuk keamanan.
    * **Authentication Filter** untuk melindungi halaman yang memerlukan login (akses terbatas).
    * Manajemen **Sesi** pengguna.
* **Laporan Dinamis:**
    * Tampilan laporan pembayaran yang dapat difilter berdasarkan rentang tanggal (bulan berjalan atau kustom).
* **Antarmuka Pengguna (UI) Responsif:**
    * Dibangun dengan Bootstrap 5 untuk tampilan yang modern dan responsif.

## Latar Belakang & Permasalahan

Proyek ini diinisiasi untuk mengatasi permasalahan umum dalam pengelolaan kost skala kecil hingga menengah yang masih mengandalkan metode manual. Metode ini menyebabkan:
* **Integritas Data Rendah:** Rentan kesalahan pencatatan, kehilangan data.
* **Inefisiensi Operasional:** Proses administrasi lambat dan memakan waktu.
* **Kurangnya Visibilitas:** Sulit mendapatkan gambaran cepat tentang kondisi bisnis.
* **Risiko Keamanan Data:** Data sensitif penghuni dan keuangan tidak terkelola aman.

## Tujuan & Manfaat

**Tujuan:**
* Membangun aplikasi web fungsional dengan arsitektur MVC.
* Mengimplementasikan database relasional terstruktur menggunakan MySQL.
* Menyediakan antarmuka pengguna yang intuitif dan responsif.
* Mengamankan aplikasi menggunakan sistem autentikasi berbasis sesi dan hashing password.

**Manfaat:**
* **Bagi Pemilik Kost:** Peningkatan efisiensi, pengurangan kesalahan, pemantauan keuangan yang mudah.
* **Bagi Disiplin Ilmu:** Studi kasus nyata penerapan PBO, MVC, dan rekayasa perangkat lunak.
* **Bagi Pengembang:** Peningkatan kompetensi dalam teknologi backend (Java Servlet), frontend (JSP), dan desain database.

## Arsitektur Sistem

Aplikasi ini mengadopsi pola arsitektur **Model-View-Controller (MVC)** untuk memisahkan antara logika data, tampilan antarmuka, dan alur kontrol aplikasi.
* **Model:** Kelas POJO (e.g., `Kamar.java`, `Penghuni.java`) dan kelas DAO (Data Access Object) yang bertanggung jawab atas data dan interaksi database.
* **View:** File-file JSP (Jakarta Server Pages) yang menampilkan data dan menangkap input pengguna.
* **Controller:** Kelas-kelas Servlet yang bertindak sebagai jembatan, menerima request, memanggil logika di Model/DAO, dan meneruskan data ke View.

## Teknologi yang Digunakan

* **IDE:** NetBeans
* **Bahasa Pemrograman:** Java (JDK 23)
* **Platform:** Java EE 8 (Servlet API)
* **Database:** MySQL
* **Web Server:** Apache Tomcat
* **Library Tambahan:** JDBC Driver for MySQL, JSTL 1.2, jBCrypt 0.4
* **Frontend:** HTML5, JSP, Bootstrap 5, Bootstrap Icons
* **Version Control:** Git & GitHub

## Instalasi & Konfigurasi

Untuk menjalankan proyek ini secara lokal, ikuti langkah-langkah berikut:

1.  **Clone Repositori:**
    ```bash
    git clone [https://github.com/FauzanArrizal/TUBES_PBO-KOST_APP.git](https://github.com/FauzanArrizal/TUBES_PBO-KOST_APP.git)
    cd TUBES_PBO-KOST_APP
    ```

2.  **Konfigurasi Database MySQL:**
    * Pastikan Anda memiliki server MySQL yang berjalan.
    * Buat database baru dengan nama `db_kost`.
    * Import skema database dari file SQL yang tersedia (misalnya, `db_kost.sql` - *Anda perlu membuatnya jika belum ada, dari ERD Anda*).
        ```sql
        -- Membuat database jika belum ada
      CREATE DATABASE IF NOT EXISTS db_kost;
      
      -- Menggunakan database db_kost
      USE db_kost;
      
      CREATE TABLE kamar (
          id_kamar INT AUTO_INCREMENT PRIMARY KEY, -- Primary Key, auto-increment untuk identifikasi unik kamar
          nomor_kamar VARCHAR(10) NOT NULL UNIQUE, -- Nomor kamar, harus unik
          tipe_kamar VARCHAR(50), -- Tipe kamar (misal: "AC + KM Dalam", "Non-AC")
          harga_per_bulan DECIMAL(10, 2) NOT NULL, -- Harga sewa per bulan
          status ENUM('KOSONG', 'TERISI') NOT NULL DEFAULT 'KOSONG' -- Status ketersediaan kamar
      );
      CREATE TABLE penghuni (
          id_penghuni INT AUTO_INCREMENT PRIMARY KEY, -- Primary Key, auto-increment untuk identifikasi unik penghuni
          nama_lengkap VARCHAR(100) NOT NULL, -- Nama lengkap penghuni
          nomor_telepon VARCHAR(15) NOT NULL, -- Nomor telepon penghuni
          asal_kota VARCHAR(50), -- Asal kota penghuni
          id_kamar INT UNIQUE, -- Foreign Key ke tabel kamar, satu penghuni hanya bisa menempati satu kamar
          FOREIGN KEY (id_kamar) REFERENCES kamar(id_kamar) -- Definisi Foreign Key relationship
      );
      CREATE TABLE pembayaran (
          id_pembayaran INT AUTO_INCREMENT PRIMARY KEY, -- Primary Key, auto-increment untuk identifikasi unik pembayaran
          id_penghuni INT NOT NULL, -- Foreign Key ke tabel penghuni
          tanggal_pembayaran DATE NOT NULL, -- Tanggal pembayaran dilakukan
          jumlah DECIMAL(10, 2) NOT NULL, -- Jumlah uang yang dibayarkan
          keterangan TEXT, -- Keterangan tambahan mengenai pembayaran
          FOREIGN KEY (id_penghuni) REFERENCES penghuni(id_penghuni) -- Definisi Foreign Key relationship
      );
      
      CREATE TABLE users (
          id_user INT AUTO_INCREMENT PRIMARY KEY, -- Primary Key, auto-increment, sesuai screenshot
          username VARCHAR(50) NOT NULL UNIQUE, -- Nama pengguna untuk login, harus unik
          password_hash VARCHAR(255) NOT NULL, -- Password yang sudah di-hash (panjang 255 cukup untuk BCrypt), sesuai screenshot
          nama_lengkap VARCHAR(100) NOT NULL -- Nama lengkap pengguna, sesuai screenshot
      );
        ```
    * Sesuaikan kredensial database di file koneksi Anda (misalnya, `DBConnection.java` jika ada) agar sesuai dengan konfigurasi MySQL lokal Anda.

3.  **Buka Proyek di NetBeans:**
    * Buka NetBeans IDE.
    * Pilih `File` > `Open Project...` dan arahkan ke folder `TUBES_PBO-KOST_APP` yang sudah di-clone.

4.  **Konfigurasi Apache Tomcat:**
    * Pastikan Apache Tomcat terintegrasi dengan NetBeans Anda.
    * Klik kanan pada proyek di NetBeans, pilih `Properties` > `Run` dan pastikan server Tomcat sudah terpilih.

5.  **Tambahkan Library:**
    * Pastikan library yang dibutuhkan (`JDBC Driver for MySQL`, `JSTL 1.2`, `jBCrypt 0.4`) sudah ditambahkan ke `Libraries` proyek Anda di NetBeans. Jika belum, Anda bisa mengunduh JAR-nya dan menambahkannya secara manual.

6.  **Build & Run:**
    * Klik kanan pada proyek, lalu pilih `Clean and Build`.
    * Setelah berhasil, klik kanan lagi, lalu pilih `Run`.
    * Aplikasi akan terbuka di browser Anda (biasanya `http://localhost:8080/TUBES_PBO-KOST_APP/login.jsp` atau sesuai konfigurasi Anda).

## Penggunaan Aplikasi

1.  **Login:**
    * Akses halaman login (misal: `http://localhost:8080/TUBES_PBO-KOST_APP/login.jsp`).
    * Gunakan kredensial default:
        * **Username:** `admin`
        * **Password:** `admin123` (jika Anda menggunakan hash default yang saya berikan di SQL di atas, pastikan hashnya benar)
2.  **Dashboard:**
    * Setelah login, Anda akan diarahkan ke halaman dashboard yang memberikan ringkasan informasi.
3.  **Navigasi:**
    * Gunakan menu navigasi untuk mengakses modul-modul: Manajemen Kamar, Manajemen Penghuni, Manajemen Pembayaran, dan Laporan.
4.  **Operasi CRUD:**
    * Di setiap modul (Kamar, Penghuni, Pembayaran), Anda dapat menambah, melihat, mengedit, dan menghapus data.
5.  **Laporan:**
    * Di modul Laporan, Anda bisa melihat riwayat pembayaran dan memfilter berdasarkan tanggal.

## Struktur Proyek

```
AplikasiKost/
├── 📁 build/                     // Folder hasil kompilasi
├── 📁 dist/                      // Folder berisi file .war untuk deployment
├── 📁 nbproject/                 // File konfigurasi khusus NetBeans
├── 📁 src/
│   └── 📁 java/
│       ├── 📦 controller
│       │   ├── 📄 AuthenticationFilter.java
│       │   ├── 📄 DashboardServlet.java
│       │   ├── 📄 EditKamarServlet.java
│       │   ├── 📄 EditPembayaranServlet.java
│       │   ├── 📄 EditPenghuniServlet.java
│       │   ├── 📄 KamarServlet.java
│       │   ├── 📄 LaporanServlet.java
│       │   ├── 📄 LoginServlet.java
│       │   ├── 📄 LogoutServlet.java
│       │   ├── 📄 PembayaranServlet.java
│       │   ├── 📄 PenghuniServlet.java
│       │   └── 📄 StrukServlet.java
│       ├── 📦 dao
│       │   ├── 📄 KamarDAO.java
│       │   ├── 📄 PembayaranDAO.java
│       │   ├── 📄 PenghuniDAO.java
│       │   └── 📄 UserDAO.java
│       ├── 📦 model
│       │   ├── 📄 Kamar.java
│       │   ├── 📄 Pembayaran.java
│       │   ├── 📄 Penghuni.java
│       │   └── 📄 User.java
│       └── 📦 utils
│           ├── 📄 CreateAdminUser.java
│           └── 📄 DatabaseConnection.java
│
├── 📁 test/                      // Folder untuk file-file pengujian
│   └── ...
│
├── 📁 web/ (atau Web Pages)
│   ├── 📁 img/
│   │   └── 🖼️ Pinterest-19.jpg
│   ├── 📁 layout/
│   │   ├── 📄 footer.jsp
│   │   └── 📄 header.jsp
│   ├── 📁 WEB-INF/
│   │   ├── 📁 lib/
│   │   │   ├── 📜 jbcrypt-0.4.jar
│   │   │   ├── 📜 jstl-1.2.jar
│   │   │   └── 📜 mysql-connector-j-....jar
│   │   └── 📄 web.xml
│   │
│   ├── 📄 edit_kamar.jsp
│   ├── 📄 edit_pembayaran.jsp
│   ├── 📄 edit_penghuni.jsp
│   ├── 📄 index.jsp (Dashboard)
│   ├── 📄 kamar.jsp
│   ├── 📄 laporan.jsp
│   ├── 📄 login.jsp
│   ├── 📄 pembayaran.jsp
│   ├── 📄 penghuni.jsp
│   └── 📄 struk.jsp
│
└── 📄 build.xml   
```

## Verifikasi & Validasi (Pengujian)

Proyek ini telah melalui tahap pengujian menggunakan metode **Black Box Testing**. Pengujian ini berfokus pada validasi fungsionalitas aplikasi dari perspektif pengguna, tanpa memeriksa struktur internal kode. Beberapa skenario yang diuji meliputi:
* Fungsionalitas login (sukses dan gagal).
* Operasi CRUD pada semua modul (Kamar, Penghuni, Pembayaran).
* Validasi input dan penanganan kesalahan.
* Filter dan tampilan laporan.
* Implementasi soft delete.

## Saran Pengembangan

Untuk pengembangan di masa depan, beberapa fitur dapat ditambahkan untuk meningkatkan nilai aplikasi:
* **Notifikasi Tunggakan:** Sistem otomatis untuk menandai penghuni yang terlambat membayar.
* **Portal Penghuni:** Halaman login terpisah bagi penghuni untuk melihat riwayat pembayaran mereka.
* **Manajemen Pengeluaran:** Modul untuk mencatat pengeluaran operasional kost.
* **Laporan Grafis:** Visualisasi data menggunakan library JavaScript seperti Chart.js.

## Tim Pengembang

* **Raisha Amalia** (2311110002)
* **Destina Bekti** (23111100)
* **Muhammad Randy** (23111100)
* **Fauzan Arrizal** (2311110021)

## Lisensi

Proyek ini dilisensikan di bawah [Lisensi MIT](https://opensource.org/licenses/MIT).
