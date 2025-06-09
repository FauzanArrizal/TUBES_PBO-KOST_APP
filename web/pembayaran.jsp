<%-- Menentukan tipe konten dan encoding halaman --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- Mengimpor library JSTL untuk logika (<c:..>) dan formatting (<fmt:..>) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Menyertakan bagian header (navbar, css, dll) dari file terpisah --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">
    <h2 class="mb-4">Manajemen Pembayaran</h2>
    <div class="row">
        <%-- Kolom Kiri: Berisi Form untuk Mencatat Pembayaran Baru --%>
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    <strong>Form Catat Pembayaran</strong>
                </div>
                <div class="card-body">
                    <%-- Notifikasi akan muncul di sini setelah aksi berhasil --%>
                    <c:if test="${param.status == 'sukses'}">
                        <div class="alert alert-success" role="alert">
                            Aksi berhasil dilakukan!
                        </div>
                    </c:if>

                    <%-- Form ini mengirim data ke PembayaranServlet dengan method POST --%>
                    <form action="pembayaran" method="POST">
                        <input type="hidden" name="action" value="tambah">
                        <div class="mb-3">
                            <label for="idPenghuni" class="form-label">Pilih Penghuni</label>
                            <select class="form-select" id="idPenghuni" name="idPenghuni" required>
                                <option value="" disabled selected>-- Pilih Nama Penghuni --</option>
                                <c:forEach var="penghuni" items="${daftarPenghuni}">
                                    <option value="${penghuni.idPenghuni}">${penghuni.namaLengkap}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="jumlah" class="form-label">Jumlah Pembayaran (Rp)</label>
                            <input type="number" class="form-control" id="jumlah" name="jumlah" required>
                        </div>
                        <div class="mb-3">
                            <label for="tanggalPembayaran" class="form-label">Tanggal Pembayaran</label>
                            <input type="date" class="form-control" id="tanggalPembayaran" name="tanggalPembayaran" required>
                        </div>
                        <div class="mb-3">
                            <label for="keterangan" class="form-label">Keterangan (Opsional)</label>
                            <textarea class="form-control" id="keterangan" name="keterangan" rows="2"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Simpan Pembayaran</button>
                    </form>
                </div>
            </div>
        </div>

        <%-- Kolom Kanan: Berisi Tabel Riwayat Pembayaran --%>
        <div class="col-md-8">
            <h4 class="mb-3">Riwayat Pembayaran</h4>

            <!-- Form untuk fitur pencarian. Mengirim data ke servlet dengan method GET. -->
            <form action="pembayaran" method="GET" class="mb-3">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Cari berdasarkan nama penghuni..." name="keyword" value="${keyword}">
                    <button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i> Cari</button>
                </div>
            </form>
            <!-- Akhir dari Form Pencarian -->

            <div class="table-responsive">
                <table class="table table-striped table-hover border shadow-sm">
                    <thead class="table-dark">
                        <tr>
                            <th>Tanggal</th>
                            <th>Nama Penghuni</th>
                            <th>Kamar</th>
                            <th>Jumlah</th>
                            <th>Keterangan</th>
                            <th style="width: 130px;">Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Menggunakan JSTL <c:forEach> untuk me-looping data 'daftarPembayaran' dari servlet --%>
                        <c:forEach var="p" items="${daftarPembayaran}">
                            <tr>
                                <%-- Memformat tanggal menjadi lebih mudah dibaca, contoh: 10 Jun 25 --%>
                                <td><fmt:formatDate value="${p.tanggalPembayaran}" pattern="dd MMM yy"/></td>
                                <td>${p.namaPenghuni}</td>
                                <td><span class="badge bg-secondary">${p.nomorKamar}</span></td>
                                    <%-- Memformat angka menjadi mata uang Rupiah --%>
                                <td><fmt:formatNumber value="${p.jumlah}" type="currency" currencyCode="IDR" maxFractionDigits="0"/></td>
                                <td>${p.keterangan}</td>
                                <td>
                                    <%-- Tombol Edit dan Hapus untuk setiap baris data --%>
                                    <a href="edit-pembayaran?id=${p.idPembayaran}" class="btn btn-sm btn-warning">Edit</a>
                                    <form action="pembayaran" method="POST" class="d-inline" onsubmit="return confirm('Anda yakin ingin menghapus data pembayaran ini?');">
                                        <input type="hidden" name="action" value="hapus">
                                        <input type="hidden" name="idPembayaran" value="${p.idPembayaran}">
                                        <button type="submit" class="btn btn-sm btn-danger">Hapus</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <%-- Blok ini hanya akan tampil jika list 'daftarPembayaran' dari servlet kosong --%>
                        <c:if test="${empty daftarPembayaran}">
                            <tr>
                                <td colspan="6" class="text-center fst-italic">Data pembayaran tidak ditemukan.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%-- Menyertakan bagian footer (copyright, script JS) dari file terpisah --%>
<jsp:include page="/layout/footer.jsp" />
