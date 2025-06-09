/*
 * File: LaporanServlet.java
 * Package: controller
 * Deskripsi: Servlet ini berfungsi sebagai Controller untuk fitur Laporan Pendapatan.
 * Menangani logika untuk memfilter data pembayaran berdasarkan rentang tanggal.
 */
package controller;

import dao.PembayaranDAO;
import model.Pembayaran;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@WebServlet("/laporan")
public class LaporanServlet extends HttpServlet {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();

    /**
     * Menangani GET request ke halaman laporan.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Ambil tanggal dari parameter URL.
            String tanggalMulaiStr = request.getParameter("tanggalMulai");
            String tanggalSelesaiStr = request.getParameter("tanggalSelesai");

            // Jika parameter tanggal tidak ada, atur tanggal default ke awal dan akhir bulan ini.
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (tanggalMulaiStr == null || tanggalMulaiStr.isEmpty()) {
                tanggalMulaiStr = today.withDayOfMonth(1).format(formatter);
            }
            if (tanggalSelesaiStr == null || tanggalSelesaiStr.isEmpty()) {
                tanggalSelesaiStr = today.withDayOfMonth(today.lengthOfMonth()).format(formatter);
            }

            // Konversi tanggal dari String ke objek Date.
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date tanggalMulai = sdf.parse(tanggalMulaiStr);
            Date tanggalSelesai = sdf.parse(tanggalSelesaiStr);

            // Panggil DAO untuk mendapatkan data pembayaran dalam rentang tanggal tersebut.
            List<Pembayaran> daftarLaporan = pembayaranDAO.getPembayaranByTanggal(tanggalMulai, tanggalSelesai);

            // Hitung total pendapatan dari data yang sudah difilter.
            double totalPendapatan = 0;
            for (Pembayaran p : daftarLaporan) {
                totalPendapatan += p.getJumlah();
            }

            // Kirim semua data yang dibutuhkan ke halaman JSP.
            request.setAttribute("daftarLaporan", daftarLaporan);
            request.setAttribute("totalPendapatan", totalPendapatan);
            request.setAttribute("tanggalMulai", tanggalMulaiStr);
            request.setAttribute("tanggalSelesai", tanggalSelesaiStr);

            request.getRequestDispatcher("/laporan.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
