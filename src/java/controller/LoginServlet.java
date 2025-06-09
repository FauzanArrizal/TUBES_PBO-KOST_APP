/*
 * File: LoginServlet.java
 * Package: controller
 * Deskripsi: Servlet ini adalah pusat dari proses autentikasi.
 * Menangani POST request dari form login, memverifikasi kredensial pengguna,
 * dan mengelola sesi (session).
 */
package controller;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt; // Import library untuk verifikasi password hash
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    /**
     * Menangani POST request dari form login.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 1. Cari user di database berdasarkan username.
        User user = userDAO.getUserByUsername(username);

        // 2. Verifikasi. Cek apakah user ditemukan DAN password yang dimasukkan cocok
        //    dengan hash password yang ada di database menggunakan BCrypt.checkpw().
        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            // 3. Jika login berhasil:
            // Buat sesi baru untuk pengguna.
            HttpSession session = request.getSession();
            // Simpan objek User ke dalam sesi. Ini akan menjadi tanda bahwa pengguna sudah login.
            session.setAttribute("user", user);
            // Arahkan ke halaman utama (dashboard).
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            // 4. Jika login gagal:
            // Kirim pesan error kembali ke halaman login.
            request.setAttribute("error", "Username atau password salah!");
            // Gunakan forward agar pesan error bisa ditampilkan.
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
