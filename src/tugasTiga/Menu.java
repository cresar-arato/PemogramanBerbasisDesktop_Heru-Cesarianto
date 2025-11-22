package tugasTiga;

import java.io.*;
import java.util.ArrayList;

public class Menu {
    private ArrayList<MenuItem> daftarMenu;
    private final String FILENAME = "menu_data.txt";

    public Menu() {
        this.daftarMenu = new ArrayList<>();
        memuatMenuDariFile(); // Load otomatis
    }

    public void tambahItem(MenuItem item) {
        daftarMenu.add(item);
        simpanMenuKeFile(); // Auto-save
    }

    public ArrayList<MenuItem> getDaftarMenu() {
        return daftarMenu;
    }

    public MenuItem cariMenu(String inputNama) {
        String cari = inputNama.toLowerCase().trim();
        for (MenuItem item : daftarMenu) {
            String namaMenu = item.getNama().toLowerCase();
            // Akan cocok jika nama menu mengandung string yang dicari (contains)
            if (namaMenu.contains(cari)) { 
                return item;
            }
        }
        return null;
    }

    public void tampilkanSemuaMenu() {
        if (daftarMenu.isEmpty()) {
            System.out.println("Belum ada data menu.");
            return;
        }
        System.out.println("\n--- DAFTAR MENU ---");
        for (int i = 0; i < daftarMenu.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarMenu.get(i).tampilMenu(); // Polymorphic call
        }
    }

    // --- File I/O: Simpan ---
    private void simpanMenuKeFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (MenuItem item : daftarMenu) {
                writer.write(item.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Gagal menyimpan data menu: " + e.getMessage());
        }
    }

    // --- File I/O: Muat ---
    private void memuatMenuDariFile() {
        File file = new File(FILENAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                String[] parts = baris.split(";");
                if (parts.length < 3) continue;

                String tipe = parts[0];
                String nama = parts[1];
                double harga = Double.parseDouble(parts[2]);
                String infoTambahan = (parts.length > 3) ? parts[3] : "-";

                MenuItem item = null;
                if (tipe.equalsIgnoreCase("Makanan")) {
                    item = new Makanan(nama, harga, infoTambahan);
                } else if (tipe.equalsIgnoreCase("Minuman")) {
                    item = new Minuman(nama, harga, infoTambahan);
                } else if (tipe.equalsIgnoreCase("Diskon")) {
                    item = new Diskon(nama, harga); 
                }

                if (item != null) daftarMenu.add(item);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Gagal memuat data menu: " + e.getMessage());
        }
    }
}
