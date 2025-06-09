<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">
    <h2>Edit Data Penghuni</h2>
    <hr>

    <%-- Menggunakan Bootstrap Grid untuk menempatkan form di tengah halaman --%>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <%-- Form ini mengirimkan data yang sudah diubah ke 'PenghuniServlet' dengan method POST --%>
                    <form action="penghuni" method="POST">

                        <%-- Input tersembunyi untuk memberi tahu servlet bahwa operasi yang diinginkan adalah 'update' --%>
                        <input type="hidden" name="action" value="update">

                        <%-- Input tersembunyi untuk mengirim ID dari penghuni yang sedang diedit. Ini penting untuk klausa WHERE di query UPDATE. --%>
                        <input type="hidden" name="idPenghuni" value="${penghuni.idPenghuni}">

                        <div class="mb-3">
                            <label for="namaLengkap" class="form-label">Nama Lengkap</label>
                            <%-- Atribut 'value' diisi dengan data nama dari objek 'penghuni' yang dikirim oleh EditPenghuniServlet --%>
                            <input type="text" class="form-control" id="namaLengkap" name="namaLengkap" value="${penghuni.namaLengkap}" required>
                        </div>
                        <div class="mb-3">
                            <label for="nomorTelepon" class="form-label">Nomor Telepon</label>
                            <input type="text" class="form-control" id="nomorTelepon" name="nomorTelepon" value="${penghuni.nomorTelepon}" required>
                        </div>
                        <div class="mb-3">
                            <label for="asalKota" class="form-label">Asal Kota</label>
                            <input type="text" class="form-control" id="asalKota" name="asalKota" value="${penghuni.asalKota}">
                        </div>

                        <button type="submit" class="btn btn-primary">Simpan Perubahan</button>
                        <%-- Tombol Batal mengarahkan pengguna kembali ke halaman daftar penghuni --%>
                        <a href="penghuni" class="btn btn-secondary">Batal</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Menyertakan file footer.jsp yang berisi bagian bawah halaman --%>
<jsp:include page="/layout/footer.jsp" />
