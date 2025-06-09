/*
 * File: EditPembayaranServlet.java
 * Package: controller
 * Deskripsi: Servlet ini memiliki satu tugas khusus: menyiapkan data untuk form edit pembayaran.
 * Ini memastikan bahwa form edit sudah terisi dengan data yang benar sebelum ditampilkan.
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

@WebServlet("/edit-pembayaran")
public class EditPembayaranServlet extends HttpServlet {

    private PembayaranDAO pembayaranDAO = new PembayaranDAO();

    /**
     * Menangani GET request yang membawa ID pembayaran yang akan diedit.
     *
     * @param request Objek request dari client.
     * @param response Objek response untuk client.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Ambil ID dari parameter URL
        int id = Integer.parseInt(request.getParameter("id"));

        // 2. Panggil DAO untuk dapatkan data pembayaran
        Pembayaran pembayaran = pembayaranDAO.getPembayaranById(id);

        // 3. Kirim objek pembayaran ke halaman JSP
        request.setAttribute("pembayaran", pembayaran);

        // 4. Forward ke halaman view form edit
        request.getRequestDispatcher("/edit_pembayaran.jsp").forward(request, response);
    }
}
