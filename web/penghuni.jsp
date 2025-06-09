<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> dan <c:forEach> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman (navbar, CSS, dll) --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Manajemen Data Penghuni</h2>
    </div>

    <%-- Notifikasi dinamis yang hanya muncul jika ada parameter 'status=sukses' di URL --%>
    <%-- Ini digunakan setelah pengguna berhasil melakukan aksi (tambah, edit, atau hapus) --%>
    <c:if test="${param.status == 'sukses'}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Aksi berhasil dilakukan!
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <div class="row">

        <%-- Kolom Kiri: Berisi Form untuk Menambah Penghuni Baru --%>
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    <strong>Form Tambah Penghuni</strong>
                </div>
                <div class="card-body">
                    <%-- Form ini mengirimkan data ke 'PenghuniServlet' menggunakan method POST --%>
                    <form action="penghuni" method="POST">
                        <%-- Input tersembunyi ini memberi tahu servlet bahwa operasi yang diinginkan adalah 'tambah' --%>
                        <input type="hidden" name="action" value="tambah">

                        <div class="mb-3">
                            <label for="namaLengkap" class="form-label">Nama Lengkap</label>
                            <input type="text" class="form-control" id="namaLengkap" name="namaLengkap" required>
                        </div>
                        <div class="mb-3">
                            <label for="nomorTelepon" class="form-label">Nomor Telepon</label>
                            <input type="text" class="form-control" id="nomorTelepon" name="nomorTelepon" required>
                        </div>
                        <div class="mb-3">
                            <label for="asalKota" class="form-label">Asal Kota</label>
                            <input type="text" class="form-control" id="asalKota" name="asalKota">
                        </div>
                        <div class="mb-3">
                            <label for="idKamar" class="form-label">Pilih Kamar</label>
                            <%-- Dropdown ini diisi secara dinamis dari data 'kamarKosong' yang dikirim oleh servlet --%>
                            <select class="form-select" id="idKamar" name="idKamar" required>
                                <option value="" disabled selected>-- Pilih Kamar Kosong --</option>
                                <c:forEach var="k" items="${kamarKosong}">
                                    <option value="${k.idKamar}">Kamar ${k.nomorKamar}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Simpan Penghuni</button>
                    </form>
                </div>
            </div>
        </div>

        <%-- Kolom Kanan: Berisi Form Pencarian dan Tabel Daftar Penghuni --%>
        <div class="col-md-8">
            <h4 class="mb-3">Daftar Penghuni</h4>
            <%-- Form untuk fitur pencarian. Mengirim data ke servlet dengan method GET. --%>
            <form action="penghuni" method="GET" class="mb-3">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Cari nama penghuni..." name="keyword" value="${keyword}">
                    <button class="btn btn-outline-secondary" type="submit"><i class="bi bi-search"></i> Cari</button>
                </div>
            </form>

            <div class="table-responsive">
                <table class="table table-striped table-hover border shadow-sm">
                    <thead class="table-dark">
                        <tr>
                            <th>Nama Lengkap</th>
                            <th>Nomor Telepon</th>
                            <th>Asal Kota</th>
                            <th>Nomor Kamar</th>
                            <th style="width: 130px;">Aksi</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- Menggunakan JSTL <c:forEach> untuk me-looping data 'daftarPenghuni' dari servlet --%>
                        <c:forEach var="p" items="${daftarPenghuni}">
                            <tr>
                                <td>${p.namaLengkap}</td>
                                <td>${p.nomorTelepon}</td>
                                <td>${p.asalKota}</td>
                                <td><span class="badge bg-secondary">${p.nomorKamar}</span></td>
                                <td>
                                    <%-- Tombol Edit: Mengarah ke 'EditPenghuniServlet' sambil membawa ID --%>
                                    <a href="edit-penghuni?id=${p.idPenghuni}" class="btn btn-sm btn-warning">Edit</a>

                                    <%-- Tombol Hapus: Dibuat dalam form agar bisa mengirim data dengan method POST --%>
                                    <form action="penghuni" method="POST" class="d-inline" onsubmit="return confirm('Anda yakin ingin menghapus ${p.namaLengkap}?');">
                                        <input type="hidden" name="action" value="hapus">
                                        <input type="hidden" name="idPenghuni" value="${p.idPenghuni}">
                                        <input type="hidden" name="idKamar" value="${p.idKamar}">
                                        <button type="submit" class="btn btn-sm btn-danger">Hapus</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>

                        <%-- Blok <c:if> ini hanya akan tampil jika list 'daftarPenghuni' kosong --%>
                        <c:if test="${empty daftarPenghuni}">
                            <tr>
                                <td colspan="5" class="text-center fst-italic">Data penghuni tidak ditemukan.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%-- Menyertakan file footer.jsp yang berisi bagian bawah halaman (copyright, script JS) --%>
<jsp:include page="/layout/footer.jsp" />
