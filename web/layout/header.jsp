<%-- Menentukan tipe konten dan encoding halaman --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="id">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>AplikasiKost</title>
        <%-- Memuat file CSS dari Bootstrap dan Bootstrap Icons melalui CDN --%>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <%-- Blok CSS Kustom untuk memberikan tampilan unik pada aplikasi --%>
        <style>
            body {
                /* Warna latar belakang dasar sebagai fallback jika gambar tidak termuat */
                background-color: #e9ecef;

                /* Gambar background, menggunakan EL untuk path yang dinamis dan aman */
                background-image: url('${pageContext.request.contextPath}/img/Pinterest-19.jpg');
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
                background-attachment: fixed;

                /* Diperlukan agar pseudo-element ::before bisa diposisikan relatif terhadap body */
                position: relative;
                z-index: 0;

                /* Pengaturan layout dasar menggunakan Flexbox */
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            /* Lapisan overlay semi-transparan di atas gambar background agar konten lebih mudah dibaca */
            body::before {
                content: '';
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(255, 255, 255, 0.6); /* Overlay putih dengan opacity 60% */
                z-index: -1; /* Diletakkan di bawah konten utama */
            }

            .navbar {
                background-color: rgba(33, 37, 41, 0.9); /* Navbar semi-transparan */
                box-shadow: 0 2px 4px rgba(0,0,0,.1);
            }

            main.container {
                flex-grow: 1; /* Memastikan konten utama mengisi ruang vertikal yang tersedia */
                background-color: rgba(255, 255, 255, 0.9);
                border-radius: 0.75rem;
                padding: 2rem;
                box-shadow: 0 0.5rem 1rem rgba(0,0,0,.1);
            }

            .card {
                background-color: rgba(255, 255, 255, 0.95);
            }

            footer {
                color: #343a40 !important;
                border-top: none !important;
                padding: 1.5rem 0;
                text-shadow: 0 1px 1px rgba(255,255,255,0.7);
            }
        </style>
    </head>
    <body>
        <%-- Navbar utama aplikasi, menggunakan komponen Bootstrap --%>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="${pageContext.request.contextPath}/"> <i class="bi bi-house-heart-fill"></i> AplikasiKost</a>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="mainNavbar">
                    <%-- Menu Navigasi Utama (di kiri) --%>
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/kamar"><i class="bi bi-door-open"></i> Kamar</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/penghuni"><i class="bi bi-people"></i> Penghuni</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/pembayaran"><i class="bi bi-cash-coin"></i> Pembayaran</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/laporan"><i class="bi bi-file-earmark-text"></i> Laporan</a>
                        </li>
                    </ul>

                    <%-- Menu Pengguna dan Logout (di kanan, menggunakan 'ms-auto') --%>
                    <ul class="navbar-nav ms-auto">
                        <%-- Tag <c:if> ini memeriksa apakah ada atribut 'user' di dalam sesi (sessionScope). --%>
                        <%-- Blok ini hanya akan ditampilkan jika pengguna sudah berhasil login. --%>
                        <c:if test="${not empty sessionScope.user}">
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                    <i class="bi bi-person-circle"></i> ${sessionScope.user.namaLengkap}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <%-- Link ini mengarah ke LogoutServlet untuk mengakhiri sesi --%>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>
        <%-- Tag <main> ini akan ditutup di file footer.jsp, membentuk sebuah template --%>
        <main class="container mt-4">
