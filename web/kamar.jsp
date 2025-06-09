<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> dan <c:forEach> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Mengimpor library JSTL Formatting untuk memformat angka (misal: format Rupiah) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman (navbar, CSS, dll) --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">
    <h2 class="mb-4">Manajemen Kamar</h2>
    <div class="row">
        <%-- Kolom Kiri: Berisi Form untuk Menambah Kamar Baru --%>
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    <strong>Form Tambah Kamar</strong>
                </div>
                <div class="card-body">
                    <%-- Form ini mengirimkan data ke 'KamarServlet' menggunakan method POST --%>
                    <form action="kamar" method="POST">
                        <input type="hidden" name="action" value="tambah">
                        <div class="mb-3">
                            <label for="nomorKamar" class="form-label">Nomor Kamar</label>
                            <input type="text" class="form-control" id="nomorKamar" name="nomorKamar" required>
                        </div>
                        <div class="mb-3">
                            <label for="tipeKamar" class="form-label">Tipe Kamar</label>
                            <input type="text" class="form-control" id="tipeKamar" name="tipeKamar" placeholder="Contoh: AC + KM Dalam">
                        </div>
                        <div class="mb-3">
                            <label for="hargaPerBulan" class="form-label">Harga per Bulan</label>
                            <input type="number" class="form-control" id="hargaPerBulan" name="hargaPerBulan" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Tambah Kamar</button>
                    </form>
                </div>
            </div>
        </div>

        <%-- Kolom Kanan: Berisi Tabel Daftar Kamar --%>
        <div class="col-md-8">
            <h4 class="mb-3">Daftar Kamar</h4>
            <%-- Notifikasi dinamis yang hanya muncul jika ada parameter 'status=sukses' di URL --%>
            <c:if test="${param.status == 'sukses'}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Aksi berhasil dilakukan!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="table-responsive">
                <table class="table table-striped table-hover border shadow-sm">
                    <thead class="table-dark">
                        <tr>
                            <th>Nomor Kamar</th>
                            <th>Tipe</th>
                            <th>Harga/Bulan</th>
                            <th>Status</th>
                            <th style="width: 130px;">Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Menggunakan JSTL <c:forEach> untuk me-looping data 'daftarKamar' dari servlet --%>
                        <c:forEach var="k" items="${daftarKamar}">
                            <tr>
                                <td>${k.nomorKamar}</td>
                                <td>${k.tipeKamar}</td>
                                <td><fmt:formatNumber value="${k.hargaPerBulan}" type="currency" currencyCode="IDR" maxFractionDigits="0"/></td>
                                <td>
                                    <%-- Badge status dengan warna dinamis: hijau untuk KOSONG, merah untuk TERISI --%>
                                    <span class="badge ${k.status == 'KOSONG' ? 'bg-success' : 'bg-danger'}">${k.status}</span>
                                </td>
                                <td>
                                    <%-- Tombol Edit: Mengarah ke 'EditKamarServlet' sambil membawa ID kamar --%>
                                    <a href="edit-kamar?id=${k.idKamar}" class="btn btn-sm btn-warning">Edit</a>

                                    <%-- Tombol Hapus: Dibuat dalam form agar bisa mengirim data dengan method POST --%>
                                    <form action="kamar" method="POST" class="d-inline" onsubmit="return confirm('Anda yakin ingin menghapus kamar ${k.nomorKamar}?');">
                                        <input type="hidden" name="action" value="hapus">
                                        <input type="hidden" name="idKamar" value="${k.idKamar}">
                                        <%-- Tombol hapus dinonaktifkan jika kamar TERISI untuk mencegah error --%>
                                        <button type="submit" class="btn btn-sm btn-danger" ${k.status == 'TERISI' ? 'disabled' : ''}>Hapus</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty daftarKamar}">
                            <tr>
                                <td colspan="5" class="text-center fst-italic">Belum ada data kamar.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/layout/footer.jsp" />
