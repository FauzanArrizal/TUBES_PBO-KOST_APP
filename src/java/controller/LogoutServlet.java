/*
 * File: LogoutServlet.java
 * Package: controller
 * Deskripsi: Servlet sederhana yang hanya memiliki satu tugas: mengakhiri sesi
 * pengguna (logout).
 */
package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    /**
     * Menangani GET request ke URL '/logout'.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ambil sesi yang sedang berjalan. Parameter 'false' berarti jangan buat sesi baru jika tidak ada.
        HttpSession session = request.getSession(false);

        // Jika sesi ada (artinya pengguna memang sedang login), maka hapus sesi tersebut.
        if (session != null) {
            session.invalidate(); // Menghapus semua data dari sesi.
        }

        // Setelah sesi dihapus, arahkan pengguna kembali ke halaman login.
        response.sendRedirect("login.jsp");
    }
}
