/*
 * File: DashboardServlet.java
 * Package: controller
 * Deskripsi: Servlet ini bertindak sebagai halaman utama (homepage) aplikasi setelah login.
 * Tugasnya adalah mengumpulkan data ringkasan dari berbagai DAO dan menampilkannya di dashboard.
 */
package controller;

import dao.KamarDAO;
import dao.PenghuniDAO;
import dao.PembayaranDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Anotasi @WebServlet("") dengan URL pattern kosong menandakan servlet ini
// akan menangani request ke root context aplikasi (misal: http://localhost:8080/AplikasiKost/).
@WebServlet("")
public class DashboardServlet extends HttpServlet {

    private KamarDAO kamarDAO = new KamarDAO();
    private PenghuniDAO penghuniDAO = new PenghuniDAO();
    private PembayaranDAO pembayaranDAO = new PembayaranDAO();

    /**
     * Menangani GET request ke halaman utama.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Panggil setiap DAO untuk mendapatkan data statistik.
        int jumlahKamarTerisi = kamarDAO.hitungKamarByStatus("TERISI");
        int jumlahKamarKosong = kamarDAO.hitungKamarByStatus("KOSONG");
        int totalPenghuni = penghuniDAO.hitungTotalPenghuni();
        double pendapatanBulanIni = pembayaranDAO.hitungPendapatanBulanIni();

        // 2. Kirim setiap data statistik sebagai atribut ke halaman JSP.
        request.setAttribute("jumlahKamarTerisi", jumlahKamarTerisi);
        request.setAttribute("jumlahKamarKosong", jumlahKamarKosong);
        request.setAttribute("totalPenghuni", totalPenghuni);
        request.setAttribute("pendapatanBulanIni", pendapatanBulanIni);

        // 3. Forward ke file view 'index.jsp' untuk ditampilkan.
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
