<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Mengimpor library JSTL Formatting untuk memformat angka (misal: format Rupiah) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman (navbar, CSS, dll) --%>
<jsp:include page="/layout/header.jsp" />

<%-- Bagian utama dashboard dengan judul yang diposisikan di tengah --%>
<div class="text-center pt-3 pb-2 mb-3 border-bottom">
    <h1 class="h2">Dashboard</h1>
</div>

<%-- Bagian Kartu-Kartu Ringkasan Data. Menggunakan Bootstrap Grid System (row, col) untuk tata letak responsif. --%>
<div class="row">

    <%-- Kartu Pendapatan Bulan Ini --%>
    <div class="col-lg-3 col-md-6 mb-4">
        <div class="card text-white bg-warning shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-0">Pendapatan Bulan Ini</h5>
                        <p class="card-text fs-4 fw-bold">
                            <%-- Menampilkan data pendapatan dari servlet dan memformatnya sebagai mata uang Rupiah. --%>
                            <fmt:formatNumber value="${pendapatanBulanIni}" type="currency" currencyCode="IDR" maxFractionDigits="0"/>
                        </p>
                    </div>
                    <i class="bi bi-wallet2 fs-1"></i>
                </div>
            </div>
        </div>
    </div>

    <%-- Kartu Kamar Terisi --%>
    <div class="col-lg-3 col-md-6 mb-4">
        <div class="card text-white bg-danger shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-0">Kamar Terisi</h5>
                        <%-- Menampilkan jumlah kamar terisi yang dikirim oleh DashboardServlet. --%>
                        <p class="card-text fs-1 fw-bold">${jumlahKamarTerisi}</p>
                    </div>
                    <i class="bi bi-lock-fill fs-1"></i>
                </div>
            </div>
        </div>
    </div>

    <%-- Kartu Kamar Kosong --%>
    <div class="col-lg-3 col-md-6 mb-4">
        <div class="card text-white bg-success shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-0">Kamar Kosong</h5>
                        <%-- Menampilkan jumlah kamar kosong yang dikirim oleh DashboardServlet. --%>
                        <p class="card-text fs-1 fw-bold">${jumlahKamarKosong}</p>
                    </div>
                    <i class="bi bi-unlock-fill fs-1"></i>
                </div>
            </div>
        </div>
    </div>

    <%-- Kartu Total Penghuni --%>
    <div class="col-lg-3 col-md-6 mb-4">
        <div class="card text-white bg-info shadow">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h5 class="card-title mb-0">Total Penghuni Aktif</h5>
                        <%-- Menampilkan total penghuni aktif yang dikirim oleh DashboardServlet. --%>
                        <p class="card-text fs-1 fw-bold">${totalPenghuni}</p>
                    </div>
                    <i class="bi bi-person-check-fill fs-1"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<hr class="my-4"> <%-- Garis pemisah horizontal untuk memisahkan bagian. --%>

<%-- Area Pintasan/Shortcut ke Halaman Lain. --%>
<div class="row text-center">
    <div class="col-md-4">
        <h4>Manajemen Kamar</h4>
        <p>Tambah, edit, atau lihat detail semua kamar kost.</p>
        <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/kamar" role="button">Lihat Detail &raquo;</a></p>
    </div>
    <div class="col-md-4">
        <h4>Manajemen Penghuni</h4>
        <p>Kelola data penghuni dan penempatan kamar.</p>
        <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/penghuni" role="button">Lihat Detail &raquo;</a></p>
    </div>
    <div class="col-md-4">
        <h4>Manajemen Pembayaran</h4>
        <p>Catat transaksi pembayaran dari para penghuni.</p>
        <p><a class="btn btn-secondary" href="${pageContext.request.contextPath}/pembayaran" role="button">Lihat Detail &raquo;</a></p>
    </div>
</div>

<%-- Menyertakan file footer.jsp yang berisi bagian bawah halaman (copyright, script JS) --%>
<jsp:include page="/layout/footer.jsp" />
