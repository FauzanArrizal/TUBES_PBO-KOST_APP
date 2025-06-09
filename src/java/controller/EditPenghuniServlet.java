/*
 * File: EditPenghuniServlet.java
 * Package: controller
 * Deskripsi: Servlet ini memiliki satu tugas khusus: menyiapkan data untuk form edit penghuni.
 * Ini memisahkan logika untuk menampilkan form edit dari logika utama PenghuniServlet.
 */
package controller;

import dao.PenghuniDAO;
import model.Penghuni;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit-penghuni")
public class EditPenghuniServlet extends HttpServlet {

    private PenghuniDAO penghuniDAO = new PenghuniDAO();

    /**
     * Menangani GET request yang membawa ID penghuni yang akan diedit.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Ambil ID penghuni dari parameter URL.
        int id = Integer.parseInt(request.getParameter("id"));

        // 2. Panggil DAO untuk mendapatkan data lengkap penghuni berdasarkan ID tersebut.
        Penghuni penghuni = penghuniDAO.getPenghuniById(id);

        // 3. Kirim objek penghuni tersebut ke halaman JSP.
        request.setAttribute("penghuni", penghuni);

        // 4. Forward ke file view 'edit_penghuni.jsp' yang berisi form edit.
        request.getRequestDispatcher("/edit_penghuni.jsp").forward(request, response);
    }
}
