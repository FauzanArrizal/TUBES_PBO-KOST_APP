/*
 * File: KamarServlet.java
 * Package: controller
 * Deskripsi: Servlet ini adalah Controller untuk fitur Manajemen Kamar.
 * Menangani logika untuk menampilkan daftar kamar dan menambah kamar baru.
 */
package controller;

import dao.KamarDAO;
import model.Kamar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/kamar")
public class KamarServlet extends HttpServlet {

    private KamarDAO kamarDAO = new KamarDAO();

    /**
     * Menangani GET request ke halaman '/kamar'. Menyiapkan daftar semua kamar
     * untuk ditampilkan di tabel.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Kamar> daftarKamar = kamarDAO.getAllKamar();
        request.setAttribute("daftarKamar", daftarKamar);
        request.getRequestDispatcher("/kamar.jsp").forward(request, response);
    }

    /**
     * Menangani POST request dari form tambah kamar.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mengambil parameter 'action' dari form untuk menentukan operasi.
        String action = request.getParameter("action");

        if ("tambah".equals(action)) {
            // Logika untuk menambah kamar baru
            String nomorKamar = request.getParameter("nomorKamar");
            String tipeKamar = request.getParameter("tipeKamar");
            double harga = Double.parseDouble(request.getParameter("hargaPerBulan"));

            Kamar kamarBaru = new Kamar();
            kamarBaru.setNomorKamar(nomorKamar);
            kamarBaru.setTipeKamar(tipeKamar);
            kamarBaru.setHargaPerBulan(harga);

            kamarDAO.tambahKamar(kamarBaru);

        } else if ("hapus".equals(action)) {
            // [LOGIKA BARU] Logika untuk menghapus kamar
            int idKamar = Integer.parseInt(request.getParameter("idKamar"));
            kamarDAO.hapusKamar(idKamar);
        } else if ("update".equals(action)) {
            // [LOGIKA BARU] Logika untuk mengupdate kamar
            int idKamar = Integer.parseInt(request.getParameter("idKamar"));
            String nomorKamar = request.getParameter("nomorKamar");
            String tipeKamar = request.getParameter("tipeKamar");
            double harga = Double.parseDouble(request.getParameter("hargaPerBulan"));

            Kamar kamar = new Kamar();
            kamar.setIdKamar(idKamar);
            kamar.setNomorKamar(nomorKamar);
            kamar.setTipeKamar(tipeKamar);
            kamar.setHargaPerBulan(harga);

            kamarDAO.updateKamar(kamar);
        }

        // Redirect kembali ke halaman kamar dengan pesan sukses.
        response.sendRedirect("kamar?status=sukses");
    }
}
