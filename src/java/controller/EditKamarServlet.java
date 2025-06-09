/*
 * File: EditKamarServlet.java
 * Package: controller
 * Deskripsi: Servlet ini memiliki satu tugas khusus: menyiapkan data untuk form edit kamar.
 * Memisahkan logika ini membuat alur kerja lebih bersih dan terorganisir.
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

@WebServlet("/edit-kamar")
public class EditKamarServlet extends HttpServlet {

    private KamarDAO kamarDAO = new KamarDAO();

    /**
     * Menangani GET request yang membawa ID kamar yang akan diedit.
     *
     * @param request Objek request dari client.
     * @param response Objek response untuk client.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Ambil ID kamar dari parameter URL.
        int id = Integer.parseInt(request.getParameter("id"));

        // 2. Panggil DAO untuk mendapatkan data kamar berdasarkan ID.
        Kamar kamar = kamarDAO.getKamarById(id);

        // 3. Kirim objek kamar tersebut ke halaman JSP.
        request.setAttribute("kamar", kamar);

        // 4. Forward ke file view 'edit_kamar.jsp' yang berisi form edit.
        request.getRequestDispatcher("/edit_kamar.jsp").forward(request, response);
    }
}
