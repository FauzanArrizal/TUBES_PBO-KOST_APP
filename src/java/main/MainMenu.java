package main;

import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int pilihan;
        do {
            System.out.println("\n=== MENU UTAMA APLIKASI MANAJEMEN KOST ===");
            System.out.println("1. Kelola Kamar");
            System.out.println("2. Kelola Penghuni");
            System.out.println("3. Kelola Pembayaran");
            System.out.println("4. Keluar");
            System.out.print("Pilih: ");
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    MainKamar.menuKamar();
                    break;
                case 2:
                    MainPenghuni.menuPenghuni();
                    break;
                case 3:
                    MainPembayaran.menuPembayaran();
                    break;
                case 4:
                    System.out.println("Terima kasih! Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (pilihan != 4);
    }
}
