package main;

import dao.PenghuniDAO;
import model.Penghuni;

import java.util.List;
import java.util.Scanner;

public class MainPenghuni {
    private static final Scanner scanner = new Scanner(System.in);
    private static final PenghuniDAO penghuniDAO = new PenghuniDAO();

    public static void menuPenghuni() {
        int pilihan;
        do {
            System.out.println("\n=== MENU PENGHUNI ===");
            System.out.println("1. Tampilkan semua penghuni");
            System.out.println("2. Tambah penghuni");
            System.out.println("3. Update penghuni");
            System.out.println("4. Hapus penghuni");
            System.out.println("5. Kembali");
            System.out.print("Pilih: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tampilkanPenghuni();
                    break;
                case 2:
                    tambahPenghuni();
                    break;
                case 3:
                    updatePenghuni();
                    break;
                case 4:
                    hapusPenghuni();
                    break;
                case 5:
                    System.out.println("Kembali ke menu utama...");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 5);
    }

    private static void tampilkanPenghuni() {
        List<Penghuni> list = penghuniDAO.getAllPenghuni();
        System.out.println("\nDaftar Penghuni:");
        for (Penghuni p : list) {
            System.out.printf("ID: %d | Nama: %s | No HP: %s | ID Kamar: %d\n",
                    p.getIdPenghuni(), p.getNama(), p.getNoHp(), p.getIdKamar());
        }
    }

    private static void tambahPenghuni() {
        System.out.print("Nama: ");
        String nama = scanner.nextLine();
        System.out.print("No HP: ");
        String noHp = scanner.nextLine();
        System.out.print("ID Kamar: ");
        int idKamar = scanner.nextInt();
        scanner.nextLine();

        Penghuni p = new Penghuni(0, nama, noHp, idKamar);
        penghuniDAO.tambahPenghuni(p);
    }

    private static void updatePenghuni() {
        System.out.print("ID Penghuni yang ingin diupdate: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nama baru: ");
        String nama = scanner.nextLine();
        System.out.print("No HP baru: ");
        String noHp = scanner.nextLine();
        System.out.print("ID Kamar baru: ");
        int idKamar = scanner.nextInt();
        scanner.nextLine();

        Penghuni p = new Penghuni(id, nama, noHp, idKamar);
        penghuniDAO.updatePenghuni(p);
    }

    private static void hapusPenghuni() {
        System.out.print("ID Penghuni yang ingin dihapus: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        penghuniDAO.hapusPenghuni(id);
    }
}
