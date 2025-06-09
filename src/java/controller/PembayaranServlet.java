/*
 * File: PembayaranServlet.java
 * Package: controller
 * Deskripsi: Kelas Servlet yang berfungsi sebagai Controller untuk fitur Pembayaran.
 * Menangani logika untuk menampilkan riwayat dan mencatat pembayaran baru.
 */
package controller;

import dao.PembayaranDAO;
import dao.PenghuniDAO;
import model.Pembayaran;
import model.Penghuni;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/pembayaran")
public class PembayaranServlet extends HttpServlet {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();
    private PenghuniDAO penghuniDAO = new PenghuniDAO();

    /**
     * Menangani GET request ke halaman pembayaran. Menyiapkan dua jenis data:
     * riwayat pembayaran untuk tabel dan daftar penghuni untuk dropdown form.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Ambil keyword pencarian dari URL.
        String keyword = request.getParameter("keyword");

        // 2. Panggil DAO dengan keyword tersebut untuk mendapatkan riwayat pembayaran yang terfilter.
        List<Pembayaran> daftarPembayaran = pembayaranDAO.getAllPembayaran(keyword);
        request.setAttribute("daftarPembayaran", daftarPembayaran);

        // 3. Daftar penghuni tetap diperlukan untuk mengisi dropdown di form tambah.
        List<Penghuni> daftarPenghuni = penghuniDAO.getAllPenghuni(null);
        request.setAttribute("daftarPenghuni", daftarPenghuni);

        // 4. Kirim kembali keyword ke JSP agar tetap tampil di kotak pencarian.
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/pembayaran.jsp").forward(request, response);
    }

    /**
     * Menangani POST request dari form tambah pembayaran.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("tambah".equals(action)) {
            try {
                int idPenghuni = Integer.parseInt(request.getParameter("idPenghuni"));
                Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("tanggalPembayaran"));
                double jumlah = Double.parseDouble(request.getParameter("jumlah"));
                String keterangan = request.getParameter("keterangan");

                Pembayaran p = new Pembayaran();
                p.setIdPenghuni(idPenghuni);
                p.setTanggalPembayaran(tanggal);
                p.setJumlah(jumlah);
                p.setKeterangan(keterangan);

                // Panggil DAO dan tangkap ID pembayaran yang baru dibuat.
                int idPembayaranBaru = pembayaranDAO.tambahPembayaran(p);

                // Jika berhasil disimpan (ID > 0), redirect ke halaman struk.
                if (idPembayaranBaru > 0) {
                    response.sendRedirect("struk?id=" + idPembayaranBaru);
                    return; // Hentikan eksekusi servlet di sini.
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            // Jika gagal, kembali ke halaman pembayaran dengan pesan error.
            response.sendRedirect("pembayaran?status=gagal");

        } else if ("hapus".equals(action)) {
            // [LOGIKA BARU] Logika untuk menghapus pembayaran
            try {
                int idPembayaran = Integer.parseInt(request.getParameter("idPembayaran"));
                pembayaranDAO.hapusPembayaran(idPembayaran);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if ("update".equals(action)) {
            // [LOGIKA BARU] Logika untuk mengupdate pembayaran
            try {
                int idPembayaran = Integer.parseInt(request.getParameter("idPembayaran"));
                Date tanggal = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("tanggalPembayaran"));
                double jumlah = Double.parseDouble(request.getParameter("jumlah"));
                String keterangan = request.getParameter("keterangan");

                Pembayaran p = new Pembayaran();
                p.setIdPembayaran(idPembayaran);
                p.setTanggalPembayaran(tanggal);
                p.setJumlah(jumlah);
                p.setKeterangan(keterangan);

                pembayaranDAO.updatePembayaran(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("pembayaran?status=sukses");
    }
}
