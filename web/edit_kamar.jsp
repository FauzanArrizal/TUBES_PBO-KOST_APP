<%-- Menentukan tipe konten dan encoding halaman --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">
    <h2>Edit Data Kamar</h2>
    <hr>

    <%-- Menggunakan Bootstrap Grid untuk menempatkan form di tengah halaman --%>
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm">
                <div class="card-body">
                    <%-- Form ini mengirimkan data yang sudah diubah ke 'KamarServlet' dengan method POST --%>
                    <form action="kamar" method="POST">

                        <%-- Input tersembunyi untuk memberi tahu servlet bahwa operasi yang diinginkan adalah 'update' --%>
                        <input type="hidden" name="action" value="update">

                        <%-- Input tersembunyi untuk mengirim ID dari kamar yang sedang diedit. Ini penting untuk klausa WHERE di query UPDATE. --%>
                        <input type="hidden" name="idKamar" value="${kamar.idKamar}">

                        <div class="mb-3">
                            <label for="nomorKamar" class="form-label">Nomor Kamar</label>
                            <%-- Atribut 'value' diisi dengan data nama dari objek 'kamar' yang dikirim oleh EditKamarServlet --%>
                            <input type="text" class="form-control" id="nomorKamar" name="nomorKamar" value="${kamar.nomorKamar}" required>
                        </div>
                        <div class="mb-3">
                            <label for="tipeKamar" class="form-label">Tipe Kamar</label>
                            <input type="text" class="form-control" id="tipeKamar" name="tipeKamar" value="${kamar.tipeKamar}">
                        </div>
                        <div class="mb-3">
                            <label for="hargaPerBulan" class="form-label">Harga per Bulan</label>
                            <input type="number" class="form-control" id="hargaPerBulan" name="hargaPerBulan" value="${kamar.hargaPerBulan}" required>
                        </div>

                        <button type="submit" class="btn btn-primary">Simpan Perubahan</button>
                        <%-- Tombol Batal mengarahkan pengguna kembali ke halaman daftar kamar --%>
                        <a href="kamar" class="btn btn-secondary">Batal</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%-- Menyertakan file footer.jsp yang berisi bagian bawah halaman --%>
<jsp:include page="/layout/footer.jsp" />
