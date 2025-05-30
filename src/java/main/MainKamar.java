package main;

import dao.KamarDAO;
import model.Kamar;

import java.util.List;
import java.util.Scanner;

public class MainKamar {
    private static final Scanner scanner = new Scanner(System.in);
    private static final KamarDAO kamarDAO = new KamarDAO();

    public static void main(String[] args) {
        menuKamar();
    }

    public static void menuKamar() {
        int pilihan;
        do {
            System.out.println("\n=== MENU KAMAR ===");
            System.out.println("1. Tampilkan semua kamar");
            System.out.println("2. Tambah kamar");
            System.out.println("3. Update kamar");
            System.out.println("4. Hapus kamar");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tampilkanKamar();
                    break;
                case 2:
                    tambahKamar();
                    break;
                case 3:
                    updateKamar();
                    break;
                case 4:
                    hapusKamar();
                    break;
                case 5:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 5);
    }

    private static void tampilkanKamar() {
        List<Kamar> list = kamarDAO.getAllKamar();
        System.out.println("\nDaftar Kamar:");
        for (Kamar k : list) {
            System.out.printf("ID: %d | No: %s | Tipe: %s | Harga: %.2f | Status: %s\n",
                    k.getIdKamar(), k.getNoKamar(), k.getTipe(), k.getHarga(), k.getStatus());
        }
    }

    private static void tambahKamar() {
        System.out.print("No Kamar: ");
        String no = scanner.nextLine();
        System.out.print("Tipe: ");
        String tipe = scanner.nextLine();
        System.out.print("Harga: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Status: ");
        String status = scanner.nextLine();

        Kamar k = new Kamar(0, no, tipe, harga, status);
        kamarDAO.tambahKamar(k);
    }

    private static void updateKamar() {
        System.out.print("ID Kamar yang ingin diupdate: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("No Kamar baru: ");
        String no = scanner.nextLine();
        System.out.print("Tipe baru: ");
        String tipe = scanner.nextLine();
        System.out.print("Harga baru: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Status baru: ");
        String status = scanner.nextLine();

        Kamar k = new Kamar(id, no, tipe, harga, status);
        kamarDAO.updateKamar(k);
    }

    private static void hapusKamar() {
        System.out.print("ID Kamar yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        kamarDAO.hapusKamar(id);
    }
}
