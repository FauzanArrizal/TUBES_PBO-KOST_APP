/*
 * File: StrukServlet.java
 * Package: controller
 * Deskripsi: Servlet ini memiliki satu tugas khusus: menyiapkan data untuk halaman
 * struk pembayaran. Ia mengambil data pembayaran tunggal berdasarkan ID yang diterima
 * dari URL.
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

// Anotasi @WebServlet mendaftarkan servlet ini ke URL '/struk'.
// Servlet ini akan aktif ketika ada request ke, contohnya, /struk?id=7
@WebServlet("/struk")
public class StrukServlet extends HttpServlet {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();

    /**
     * Menangani GET request yang selalu membawa ID pembayaran di URL.
     *
     * @param request Objek request dari client, berisi parameter 'id'.
     * @param response Objek response untuk client.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 1. Ambil ID pembayaran dari parameter URL.
            int id = Integer.parseInt(request.getParameter("id"));

            // 2. Panggil DAO untuk mendapatkan detail pembayaran yang spesifik berdasarkan ID.
            //    Method getPembayaranById() akan melakukan JOIN untuk mendapatkan nama penghuni, dll.
            Pembayaran strukData = pembayaranDAO.getPembayaranById(id);

            // 3. Kirim objek Pembayaran yang sudah lengkap datanya ke halaman JSP.
            //    Atribut ini akan dinamai "struk" di sisi JSP.
            request.setAttribute("struk", strukData);

            // 4. Meneruskan (forward) request ke file view 'struk.jsp' untuk ditampilkan.
            request.getRequestDispatcher("/struk.jsp").forward(request, response);

        } catch (Exception e) {
            // Menangani error jika ID tidak valid atau terjadi masalah lain.
            e.printStackTrace();
            // Jika ada error, arahkan pengguna kembali ke halaman pembayaran utama.
            response.sendRedirect("pembayaran");
        }
    }
}
