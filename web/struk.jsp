<%-- Menentukan tipe konten dan encoding halaman, ini adalah standar untuk JSP --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- Mengimpor library JSTL Core untuk menggunakan tag logika seperti <c:if> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Mengimpor library JSTL Formatting untuk memformat angka dan tanggal (misal: format Rupiah) --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!doctype html>
<html lang="id">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Struk Pembayaran - ${struk.namaPenghuni}</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

        <%-- Blok CSS internal untuk menata tampilan khusus halaman struk --%>
        <style>
            body {
                background-color: #f0f2f5;
                display: flex;
                flex-direction: column;
                align-items: center;
                min-height: 100vh;
            }
            .receipt-container {
                width: 100%;
                max-width: 480px; /* Lebar struk agar terlihat seperti kertas struk asli */
                margin: 20px;
                padding: 30px;
                background-color: #fff;
                border: 1px dashed #ccc;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
            .receipt-header h3 {
                font-weight: bold;
                color: #333;
            }
            .receipt-details td:first-child {
                font-weight: 500;
                width: 40%;
            }
            .receipt-footer {
                margin-top: 30px;
                font-style: italic;
                font-size: 0.9em;
                color: #777;
            }
            .action-buttons {
                margin-top: 20px;
            }

            /**
             * Media Query khusus untuk mode 'print'.
             * Aturan CSS di dalam blok ini hanya akan aktif saat pengguna menekan tombol Cetak
             * atau Ctrl+P, memastikan hasil cetakan bersih dan rapi.
             */
            @media print {
                /* Sembunyikan tombol aksi dan link kembali saat mencetak */
                .action-buttons, .back-link {
                    display: none !important;
                }
                /* Hilangkan background abu-abu dan atur ulang layout body untuk print */
                body {
                    background-color: #fff;
                    display: block;
                }
                /* Hilangkan border dan bayangan pada struk agar terlihat bersih di kertas */
                .receipt-container {
                    margin: 0;
                    border: none;
                    box-shadow: none;
                    max-width: 100%;
                }
            }
        </style>
    </head>
    <body>
        <%-- Tombol ini sengaja diletakkan di luar kontainer struk agar tidak ikut tercetak --%>
        <div class="container text-center back-link">
            <a href="${pageContext.request.contextPath}/pembayaran" class="btn btn-secondary mt-3">
                &laquo; Kembali ke Manajemen Pembayaran
            </a>
        </div>

        <div class="receipt-container">
            <div class="receipt-header text-center mb-4">
                <h3>BUKTI PEMBAYARAN</h3>
                <p class="mb-0">AplikasiKost</p>
            </div>

            <hr>

            <table class="table table-borderless receipt-details">
                <%-- Menampilkan data yang dikirim dari StrukServlet --%>
                <%-- ${struk.namaPenghuni} adalah cara singkat untuk memanggil method getNamaPenghuni() --%>
                <tr>
                    <td>Tanggal Bayar</td>
                    <%-- Menggunakan tag <fmt:formatDate> untuk mengubah format tanggal --%>
                    <td>: <fmt:formatDate value="${struk.tanggalPembayaran}" pattern="dd MMMM yyyy"/></td>
                </tr>
                <tr>
                    <td>Diterima dari</td>
                    <td>: ${struk.namaPenghuni}</td>
                </tr>
                <tr>
                    <td>Kamar</td>
                    <td>: ${struk.nomorKamar}</td>
                </tr>
                <tr>
                    <td>Jumlah</td>
                    <%-- Menggunakan tag <fmt:formatNumber> untuk mengubah angka menjadi format mata uang --%>
                    <td class="fs-5 fw-bold">: <fmt:formatNumber value="${struk.jumlah}" type="currency" currencyCode="IDR" maxFractionDigits="0"/></td>
                </tr>
                <tr>
                    <td>Keterangan</td>
                    <td>: ${struk.keterangan}</td>
                </tr>
                <tr>
                    <td>Diterima oleh</td>
                    <%-- Mengambil nama admin yang sedang login dari sesi (sessionScope) --%>
                    <td>: ${sessionScope.user.namaLengkap}</td>
                </tr>
            </table>

            <div class="receipt-footer text-center">
                Terima kasih atas pembayarannya.
                <br>
                (Struk ini dicetak otomatis oleh sistem)
            </div>
        </div>

        <%-- Tombol ini juga diletakkan di luar agar tidak ikut tercetak --%>
        <div class="text-center action-buttons mb-4">
            <%-- Memanggil fungsi JavaScript 'window.print()' untuk membuka dialog cetak browser --%>
            <button class="btn btn-primary" onclick="window.print();">
                Cetak Struk
            </button>
        </div>
    </body>
</html>