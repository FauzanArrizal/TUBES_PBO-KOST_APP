<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> dan <c:forEach> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Mengimpor library JSTL Formatting untuk memformat angka dan tanggal --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Menyertakan file header.jsp yang berisi bagian atas halaman --%>
<jsp:include page="/layout/header.jsp" />

<div class="container mt-4">
    <h2 class="mb-4">Laporan Pendapatan</h2>

    <%-- Form ini digunakan untuk memfilter laporan berdasarkan rentang tanggal --%>
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <%-- Form mengirim data ke 'LaporanServlet' dengan method GET, karena hanya meminta data, tidak mengubahnya --%>
            <form action="laporan" method="GET" class="row g-3 align-items-end">
                <div class="col-md-5">
                    <label for="tanggalMulai" class="form-label">Dari Tanggal</label>
                    <%-- Atribut 'value' diisi dengan data dari servlet agar pilihan tanggal pengguna tetap ada setelah halaman dimuat ulang --%>
                    <input type="date" class="form-control" id="tanggalMulai" name="tanggalMulai" value="${tanggalMulai}">
                </div>
                <div class="col-md-5">
                    <label for="tanggalSelesai" class="form-label">Sampai Tanggal</label>
                    <input type="date" class="form-control" id="tanggalSelesai" name="tanggalSelesai" value="${tanggalSelesai}">
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Tampilkan</button>
                </div>
            </form>
        </div>
    </div>

    <%-- Tabel untuk menampilkan hasil laporan yang sudah difilter --%>
    <div class="table-responsive">
        <table class="table table-bordered table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Tanggal</th>
                    <th>Nama Penghuni</th>
                    <th>Kamar</th>
                    <th>Keterangan</th>
                    <th class="text-end">Jumlah</th>
                </tr>
            </thead>
            <tbody>
                <%-- Menggunakan JSTL <c:forEach> untuk me-looping data 'daftarLaporan' dari servlet --%>
                <c:forEach var="item" items="${daftarLaporan}">
                    <tr>
                        <td><fmt:formatDate value="${item.tanggalPembayaran}" pattern="dd MMM yy"/></td>
                        <td>${item.namaPenghuni}</td>
                        <td>${item.nomorKamar}</td>
                        <td>${item.keterangan}</td>
                        <td class="text-end"><fmt:formatNumber value="${item.jumlah}" type="currency" currencyCode="IDR" maxFractionDigits="0"/></td>
                    </tr>
                </c:forEach>
                <%-- Blok <c:if> ini hanya akan tampil jika list 'daftarLaporan' kosong --%>
                <c:if test="${empty daftarLaporan}">
                    <tr>
                        <td colspan="5" class="text-center fst-italic">Tidak ada data pembayaran pada rentang tanggal ini.</td>
                    </tr>
                </c:if>
            </tbody>
            <tfoot>
                <%-- Baris footer tabel untuk menampilkan total pendapatan --%>
                <tr class="fw-bold fs-5 table-light">
                    <td colspan="4" class="text-end">Total Pendapatan:</td>
                    <td class="text-end text-success">
                        <fmt:formatNumber value="${totalPendapatan}" type="currency" currencyCode="IDR" maxFractionDigits="0"/>
                    </td>
                </tr>
            </tfoot>
        </table>
    </div>
</div>

<jsp:include page="/layout/footer.jsp" />
