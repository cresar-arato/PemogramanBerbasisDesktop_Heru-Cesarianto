package tugasTiga;

import java.util.Scanner;

public class WarungJava {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menuRestoran = new Menu(); 

    public static void main(String[] args) {
        // Inisialisasi Data Dummy jika kosong (opsional, agar tidak kosong saat pertama run)
        if (menuRestoran.getDaftarMenu().isEmpty()) {
            menuRestoran.tambahItem(new Makanan("Nasi Campur", 15000, "Berat"));
            menuRestoran.tambahItem(new Makanan("Nasi Kuning", 17000, "Berat"));
            menuRestoran.tambahItem(new Makanan("Lalapan", 18000, "Berat"));
            menuRestoran.tambahItem(new Makanan("Soto Banjar", 20000, "Berat"));
            menuRestoran.tambahItem(new Makanan("Pisang Goreng", 2500, "Ringan"));
            menuRestoran.tambahItem(new Minuman("Es Teh", 2000, "Dingin"));
            menuRestoran.tambahItem(new Minuman("Susu", 5000, "Normal"));
            menuRestoran.tambahItem(new Minuman("Kopi", 3000, "Hangat"));
            menuRestoran.tambahItem(new Minuman("Air", 1000, "Normal"));
            menuRestoran.tambahItem(new Diskon("Promo Merdeka", 5000));
        }

        boolean berjalan = true;
        while (berjalan) {
            try {
                System.out.println("\n=== MENU UTAMA WARUNG JAVA ===");
                System.out.println("1. Tambah Menu Baru (Admin)");
                System.out.println("2. Lihat Daftar Menu");
                System.out.println("3. Buat Pesanan Pelanggan");
                System.out.println("4. Keluar");
                System.out.print("Pilih opsi: ");
                
                String input = scanner.nextLine();
                
                switch (input) {
                    case "1":
                        menuTambahItem();
                        break;
                    case "2":
                        menuRestoran.tampilkanSemuaMenu();
                        break;
                    case "3":
                        prosesPesanan();
                        break;
                    case "4":
                        System.out.println("Sampai jumpa!");
                        berjalan = false;
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }
            } catch (Exception e) {
                System.err.println("Terjadi kesalahan tidak terduga: " + e.getMessage());
            }
        }
    }

    private static void menuTambahItem() {
        System.out.println("\n--- Tambah Item Baru ---");
        System.out.println("Jenis: 1. Makanan, 2. Minuman, 3. Diskon/Voucher");
        System.out.print("Pilih (1-3): ");
        String jenis = scanner.nextLine();

        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        
        try {
            if (jenis.equals("1")) {
                System.out.print("Masukkan Harga: ");
                double harga = Double.parseDouble(scanner.nextLine());
                System.out.print("Jenis Makanan (Berat/Ringan): ");
                String info = scanner.nextLine();
                menuRestoran.tambahItem(new Makanan(nama, harga, info));

            } else if (jenis.equals("2")) {
                System.out.print("Masukkan Harga: ");
                double harga = Double.parseDouble(scanner.nextLine());
                System.out.print("Jenis Minuman (Hangat/Dingin): ");
                String info = scanner.nextLine();
                menuRestoran.tambahItem(new Minuman(nama, harga, info));

            } else if (jenis.equals("3")) {
                System.out.print("Besar Potongan Diskon: ");
                double disc = Double.parseDouble(scanner.nextLine());
                menuRestoran.tambahItem(new Diskon(nama, disc));
            } else {
                System.out.println("Jenis tidak valid.");
            }
            System.out.println("Item berhasil ditambahkan dan disimpan!");

        } catch (NumberFormatException e) {
            System.err.println("Error: Masukkan angka untuk harga/diskon.");
        }
    }

    private static void prosesPesanan() {
        Pesanan pesananSaatIni = new Pesanan();
        System.out.println("\n--- Pesanan Baru ---");
        menuRestoran.tampilkanSemuaMenu();
        System.out.println("\nFORMAT: <nama_menu>=<jumlah> (Cth: Teh=2 atau Goreng=1)");
        System.out.println("\nFORMAT: Jangan lupa input promo khusus jika ada");
        System.out.println("Ketik 'selesai' untuk cetak struk.");

        while (true) {
            System.out.print("Pesanan >> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            if (!input.contains("=")) {
                System.err.println("❌ Format harus '<nama_menu>=<jumlah>'.");
                continue;
            }

            try {
                String[] parts = input.split("=");
                if (parts.length != 2) {
                    System.err.println("❌ Format tidak valid. Gunakan satu tanda '='.");
                    continue;
                }

                String namaInput = parts[0].trim();
                int jumlah = Integer.parseInt(parts[1].trim());

                if (jumlah <= 0) {
                     System.err.println("❌ Jumlah pesanan harus lebih dari 0.");
                     continue;
                }

                // Menggunakan cariMenu()
                MenuItem item = menuRestoran.cariMenu(namaInput);
                
                if (item != null) {
                    pesananSaatIni.tambahPesanan(item, jumlah); // Tambah pesanan dan jumlah
                } else {
                    System.err.println("❌ Menu dengan kata kunci '" + namaInput + "' tidak ditemukan!");
                }
            } catch (NumberFormatException e) {
                System.err.println("❌ ERROR: Jumlah harus berupa angka yang valid.");
            } catch (Exception e) {
                 System.err.println("❌ Terjadi kesalahan saat memproses input: " + e.getMessage());
            }
        }

        // Membuka dialog simpan
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Simpan Struk");
        fileChooser.setSelectedFile(new java.io.File("struk_pelanggan.txt"));

        if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            if (!path.toLowerCase().endsWith(".txt")) {
                path += ".txt";
            }
            pesananSaatIni.cetakStruk(path);
        } else {
            System.out.println("[Info] Penyimpanan dibatalkan.");
        }
    }
}
