/*
 * File: PenghuniServlet.java
 * Package: controller
 * Deskripsi: Kelas Servlet yang berfungsi sebagai Controller untuk semua permintaan
 * yang berkaitan dengan data Penghuni. Ini menangani logika untuk menampilkan,
 * menambah, mengupdate, menghapus, dan mencari data penghuni.
 */
package controller;

import dao.KamarDAO;
import dao.PenghuniDAO;
import model.Kamar;
import model.Penghuni;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

// Anotasi @WebServlet mendaftarkan servlet ini ke URL '/penghuni'.
// Semua request ke URL ini akan ditangani oleh kelas ini.
@WebServlet("/penghuni")
public class PenghuniServlet extends HttpServlet {

    private PenghuniDAO penghuniDAO = new PenghuniDAO();
    private KamarDAO kamarDAO = new KamarDAO();

    /**
     * Menangani HTTP GET request. Method ini dipanggil saat pengguna membuka
     * halaman '/penghuni' atau saat melakukan pencarian. Tugas utamanya adalah
     * menyiapkan data untuk ditampilkan di halaman JSP.
     *
     * @param request Objek request dari client.
     * @param response Objek response untuk client.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Ambil parameter 'keyword' dari URL untuk fitur pencarian.
        String keyword = request.getParameter("keyword");

        // 2. Panggil DAO untuk mendapatkan daftar penghuni (bisa terfilter atau semua).
        List<Penghuni> daftarPenghuni = penghuniDAO.getAllPenghuni(keyword);
        request.setAttribute("daftarPenghuni", daftarPenghuni); // Kirim data ke JSP

        // 3. Ambil daftar kamar yang KOSONG untuk mengisi dropdown di form tambah.
        List<Kamar> kamarKosong = kamarDAO.getKamarByStatus("KOSONG");
        request.setAttribute("kamarKosong", kamarKosong); // Kirim data ke JSP

        // 4. Kirim kembali keyword ke JSP agar tetap tampil di kotak pencarian.
        request.setAttribute("keyword", keyword);

        // 5. Meneruskan (forward) request beserta semua data atribut ke file view.
        request.getRequestDispatcher("/penghuni.jsp").forward(request, response);
    }

    /**
     * Menangani HTTP POST request. Method ini dipanggil saat pengguna
     * mengirimkan form (misalnya, form tambah, update, atau hapus).
     *
     * @param request Objek request dari client.
     * @param response Objek response untuk client.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Menggunakan parameter 'action' sebagai "saklar" untuk menentukan operasi apa yang harus dilakukan.
        String action = request.getParameter("action");

        if ("tambah".equals(action)) {
            // Logika untuk menambah penghuni baru
            String namaLengkap = request.getParameter("namaLengkap");
            String nomorTelepon = request.getParameter("nomorTelepon");
            String asalKota = request.getParameter("asalKota");
            int idKamar = Integer.parseInt(request.getParameter("idKamar"));

            Penghuni penghuniBaru = new Penghuni();
            penghuniBaru.setNamaLengkap(namaLengkap);
            penghuniBaru.setNomorTelepon(nomorTelepon);
            penghuniBaru.setAsalKota(asalKota);
            penghuniBaru.setIdKamar(idKamar);

            penghuniDAO.tambahPenghuni(penghuniBaru);

        } else if ("hapus".equals(action)) {
            // Logika untuk menghapus (soft delete) penghuni
            int idPenghuni = Integer.parseInt(request.getParameter("idPenghuni"));
            int idKamar = Integer.parseInt(request.getParameter("idKamar"));
            penghuniDAO.hapusPenghuni(idPenghuni, idKamar);

        } else if ("update".equals(action)) {
            // Logika untuk mengupdate data penghuni
            int idPenghuni = Integer.parseInt(request.getParameter("idPenghuni"));
            String namaLengkap = request.getParameter("namaLengkap");
            String nomorTelepon = request.getParameter("nomorTelepon");
            String asalKota = request.getParameter("asalKota");

            Penghuni p = new Penghuni();
            p.setIdPenghuni(idPenghuni);
            p.setNamaLengkap(namaLengkap);
            p.setNomorTelepon(nomorTelepon);
            p.setAsalKota(asalKota);

            penghuniDAO.updatePenghuni(p);
        }

        // Setelah aksi apapun selesai, arahkan kembali (redirect) pengguna ke halaman utama penghuni.
        // Ini akan memicu method doGet lagi untuk menampilkan data terbaru.
        response.sendRedirect("penghuni?status=sukses");
    }
}
