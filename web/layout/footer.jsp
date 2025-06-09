<%-- File: layout/footer.jsp --%>
<%-- Bagian ini akan menutup tag <main> dari header.jsp dan menampilkan footer. --%>
</main>

<%-- Tag <footer> semantik untuk bagian bawah halaman, diberi style dengan class Bootstrap. --%>
<footer class="container mt-5 py-4 text-center border-top">

    <%-- Paragraf untuk informasi Hak Cipta (Copyright) --%>
    <p class="text-muted mb-1">&copy; 2025 AplikasiKost - Pembelajaran Berbasis Objek</p>

    <%-- Paragraf untuk menampilkan kredit atau nama anggota kelompok. --%>
    <p class="small text-muted mb-0">
        <strong>Raisha Amalia</strong> | 
        <strong>Destina Bekti</strong> | 
        <strong>Muhammad Randy</strong> | 
        <strong>Fauzan Arrizal</strong>
    </p>
</footer>

<%-- Memuat file JavaScript Bootstrap dari CDN. --%>
<%-- Ini diperlukan untuk fungsionalitas komponen Bootstrap seperti dropdown, modal, dll. --%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<%-- Tag penutup untuk body dan html, melengkapi struktur dokumen dari header.jsp --%>
</body>
</html>
