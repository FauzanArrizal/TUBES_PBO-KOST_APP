package main;

import dao.PembayaranDAO;
import model.Pembayaran;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class MainPembayaran {
    private static Scanner scanner = new Scanner(System.in);
    private static PembayaranDAO dao = new PembayaranDAO();

    public static void menuPembayaran() {
        while (true) {
            System.out.println("\n=== MENU PEMBAYARAN ===");
            System.out.println("1. Tampilkan semua pembayaran");
            System.out.println("2. Tambah pembayaran");
            System.out.println("3. Update pembayaran");
            System.out.println("4. Hapus pembayaran");
            System.out.println("5. Kembali");
            System.out.print("Pilih menu: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    List<Pembayaran> list = dao.getAllPembayaran();
                    for (Pembayaran p : list) {
                        System.out.printf("ID: %d | ID Penghuni: %d | Jumlah: %.2f | Tanggal: %s\n",
                                p.getIdPembayaran(), p.getIdPenghuni(), p.getJumlah(), p.getTanggalPembayaran());
                    }
                    break;

                case 2:
                    System.out.print("ID Penghuni: ");
                    int idPenghuni = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Jumlah pembayaran: ");
                    double jumlah = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Tanggal pembayaran (YYYY-MM-DD): ");
                    String tanggalStr = scanner.nextLine();
                    Date tanggal = Date.valueOf(tanggalStr);

                    Pembayaran baru = new Pembayaran(idPenghuni, jumlah, tanggal);
                    dao.tambahPembayaran(baru);
                    break;

                case 3:
                    System.out.print("ID Pembayaran yang ingin diupdate: ");
                    int idBayarUpdate = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("ID Penghuni baru: ");
                    int idPenghuniBaru = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Jumlah baru: ");
                    double jumlahBaru = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Tanggal baru (YYYY-MM-DD): ");
                    String tanggalBaruStr = scanner.nextLine();
                    Date tanggalBaru = Date.valueOf(tanggalBaruStr);

                    Pembayaran update = new Pembayaran(idBayarUpdate, idPenghuniBaru, jumlahBaru, tanggalBaru);
                    dao.updatePembayaran(update);
                    break;

                case 4:
                    System.out.print("ID Pembayaran yang ingin dihapus: ");
                    int idBayarHapus = scanner.nextInt();
                    scanner.nextLine();

                    dao.hapusPembayaran(idBayarHapus);
                    break;

                case 5:
                    System.out.println("Kembali ke menu utama...");
                    return;

                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }
}
