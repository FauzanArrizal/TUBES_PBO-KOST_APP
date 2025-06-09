<%-- Menentukan tipe konten dan encoding halaman --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Formatting untuk memformat tanggal --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">
    <h2>Edit Data Pembayaran</h2>
    <hr>

    <%-- Menggunakan Bootstrap Grid untuk menempatkan form di tengah halaman --%>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <%-- Form ini mengirimkan data yang sudah diubah ke 'PembayaranServlet' dengan method POST --%>
                    <form action="pembayaran" method="POST">

                        <%-- Input tersembunyi untuk memberi tahu servlet bahwa operasi yang diinginkan adalah 'update' --%>
                        <input type="hidden" name="action" value="update">

                        <%-- Input tersembunyi untuk mengirim ID dari pembayaran yang sedang diedit. Ini penting untuk klausa WHERE di query UPDATE. --%>
                        <input type="hidden" name="idPembayaran" value="${pembayaran.idPembayaran}">

                        <div class="mb-3">
                            <label class="form-label">Penghuni</label>
                            <%-- Nama penghuni hanya ditampilkan (readonly) dan tidak bisa diubah untuk menjaga integritas data --%>
                            <input type="text" class="form-control" value="${pembayaran.namaPenghuni} (Kamar ${pembayaran.nomorKamar})" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="jumlah" class="form-label">Jumlah Pembayaran (Rp)</label>
                            <%-- Atribut 'value' diisi dengan data jumlah dari objek 'pembayaran' yang dikirim oleh EditPembayaranServlet --%>
                            <input type="number" class="form-control" id="jumlah" name="jumlah" value="${pembayaran.jumlah}" required>
                        </div>
                        <div class="mb-3">
                            <label for="tanggalPembayaran" class="form-label">Tanggal Pembayaran</label>
                            <%-- Trik untuk menampilkan tanggal di input type="date": --%>
                            <%-- 1. Gunakan <fmt:formatDate> untuk mengubah objek Date menjadi String dengan format yyyy-MM-dd. --%>
                            <fmt:formatDate value="${pembayaran.tanggalPembayaran}" pattern="yyyy-MM-dd" var="formattedDate"/>
                            <%-- 2. Gunakan String yang sudah diformat tersebut sebagai nilai (value) dari input. --%>
                            <input type="date" class="form-control" id="tanggalPembayaran" name="tanggalPembayaran" value="${formattedDate}" required>
                        </div>
                        <div class="mb-3">
                            <label for="keterangan" class="form-label">Keterangan (Opsional)</label>
                            <%-- Untuk textarea, nilai diletakkan di antara tag pembuka dan penutup --%>
                            <textarea class="form-control" id="keterangan" name="keterangan" rows="2">${pembayaran.keterangan}</textarea>
                        </div>

                        <button type="submit" class="btn btn-primary">Simpan Perubahan</button>
                        <%-- Tombol Batal mengarahkan pengguna kembali ke halaman daftar pembayaran --%>
                        <a href="pembayaran" class="btn btn-secondary">Batal</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Menyertakan file footer.jsp yang berisi bagian bawah halaman --%>
<jsp:include page="/layout/footer.jsp" />
