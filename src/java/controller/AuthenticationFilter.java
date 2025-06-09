/*
 * File: AuthenticationFilter.java
 * Package: controller
 * Deskripsi: Kelas Filter yang bertindak sebagai "penjaga gerbang" aplikasi.
 * Filter ini akan mencegat semua request yang masuk untuk memeriksa apakah pengguna
 * sudah login (terautentikasi) sebelum diizinkan mengakses halaman yang diproteksi.
 */
package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

// Anotasi @WebFilter("/*") berarti filter ini akan mencegat SEMUA request yang masuk ke aplikasi.
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    /**
     * Method utama dari filter yang akan dieksekusi untuk setiap request.
     *
     * @param req Objek request yang masuk.
     * @param res Objek response yang akan dikirim.
     * @param chain Objek untuk meneruskan request ke tujuan selanjutnya
     * (servlet atau JSP lain).
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false); // Ambil session jika ada, jangan buat baru.

        // Dapatkan path yang diminta user, contoh: /login.jsp, /penghuni, /
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());

        // Cek apakah user sudah login dengan memeriksa keberadaan atribut "user" di session.
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        // Daftar halaman/resource yang boleh diakses publik (tanpa login).
        // Kita izinkan akses ke halaman login dan servlet login.
        // Anda juga bisa menambahkan path ke folder CSS atau JS jika ada, contoh: || requestPath.startsWith("/css")
        boolean isPublicResource = requestPath.equals("/login.jsp")
                || requestPath.equals("/login")
                || requestPath.startsWith("/img/"); //pengecekan untuk folder /img/

        if (isLoggedIn || isPublicResource) {
            // KASUS 1: JIKA SUDAH LOGIN, ATAU JIKA HALAMAN YANG DIMINTA ADALAH HALAMAN PUBLIK
            // Maka, izinkan request untuk melanjutkan ke tujuannya.
            chain.doFilter(req, res);
        } else {
            // KASUS 2: JIKA BELUM LOGIN DAN MENCOBA MENGAKSES HALAMAN TERPROTEKSI
            // Maka, 'lempar' atau redirect user ke halaman login.
            System.out.println("FILTER: Akses ditolak ke " + requestPath + ". Redirect ke halaman login.");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    // Method init dan destroy bisa dibiarkan kosong untuk saat ini.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
