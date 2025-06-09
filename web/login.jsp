<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="id">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Login - AplikasiKost</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <%-- Blok CSS internal untuk menata tampilan khusus halaman login --%>
        <style>
            body {
                /* Gambar background, path ini harus diizinkan oleh AuthenticationFilter */
                background-image: url('${pageContext.request.contextPath}/img/Pinterest-19.jpg');
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
                background-attachment: fixed;

                /* Diperlukan untuk lapisan overlay */
                position: relative;
                z-index: 0;
            }

            /* Lapisan overlay gelap semi-transparan di atas gambar background */
            body::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5); /* Overlay hitam dengan opacity 50% */
                z-index: -1; /* Memastikan overlay berada di bawah konten */
            }

            .login-container {
                /* Menggunakan flexbox untuk menempatkan form login tepat di tengah layar */
                display: flex;
                align-items: center;
                justify-content: center;
                min-height: 100vh;
            }

            .login-card {
                width: 100%;
                max-width: 400px;
                /* Efek 'glassmorphism' (kaca buram) untuk tampilan modern */
                background-color: rgba(255, 255, 255, 0.9);
                border-radius: 1rem;
                backdrop-filter: blur(5px);
                border: 1px solid rgba(255, 255, 255, 0.2);
            }
        </style>
    </head>
    <body>
        <div class="login-container">
            <div class="card login-card shadow-lg">
                <div class="card-body p-5">
                    <h3 class="card-title text-center mb-4">Login AplikasiKost</h3>

                    <%-- Notifikasi error dinamis --%>
                    <%-- Blok <c:if> ini hanya akan tampil jika LoginServlet mengirimkan atribut 'error' --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger" role="alert">
                            ${error}
                        </div>
                    </c:if>

                    <%-- Form login mengirimkan data ke LoginServlet menggunakan method POST --%>
                    <form action="login" method="POST">
                        <div class="mb-3">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
