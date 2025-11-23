package tugasTiga;

import java.io.*;
import java.util.ArrayList;

public class Pesanan {
    private ArrayList<MenuItem> keranjangWarung;
    private ArrayList<Integer> jumlahPesanan;
    
    public Pesanan() {
        this.keranjangWarung = new ArrayList<>();
        this.jumlahPesanan = new ArrayList<>();
    }
    // Mengecek apakah Keranjang Warung Kosong,
    // sebelem melakukan pembayaran
    public boolean isKeranjangEmpty() {
        return keranjangWarung.isEmpty();
    }
    
    public void tambahPesanan(MenuItem item, int jumlah) {
        keranjangWarung.add(item);
        jumlahPesanan.add(jumlah);
        System.out.println("Sukses menambahkan: " + item.getNama() + " x " + jumlah);
    }

    public void cetakStruk(String filePath) {
        if (keranjangWarung.isEmpty()) {
            System.out.println("Keranjang pesanan kosong.");
            return;
        }

        // --- Membuat Struk ---
        StringBuilder struk = new StringBuilder();
        struk.append("\n==============================\n");
        struk.append("       STRUK PEMBELIAN        \n");
        struk.append("==============================\n");

        double subtotal = 0;
        double potongan = 0;

        for (int i = 0; i < keranjangWarung.size(); i++) {
            MenuItem item = keranjangWarung.get(i);
            int jumlah = jumlahPesanan.get(i);
            
            if (item instanceof Diskon) {
            	// Diskon hanya berlaku 1 kali, terlepas dari jumlah yang dimasukkan
                struk.append(String.format("%-20s -Rp. %,.0f\n",
                        item.getNama(), ((Diskon) item).getBesarDiskon()));
                potongan += ((Diskon) item).getBesarDiskon();
            } else {
                double hargaItem = item.getHarga() * jumlah;
                struk.append(String.format("%-15s x%d = Rp. %,.0f\n",
                        item.getNama(), jumlah, hargaItem));
                subtotal += hargaItem;
            }
        }

        double totalAkhir = subtotal - potongan;
        if (totalAkhir < 0) totalAkhir = 0;

        struk.append("------------------------------\n");
        struk.append(String.format("Subtotal:             Rp. %,.0f\n", subtotal));
        struk.append(String.format("Hemat:               -Rp. %,.0f\n", potongan));
        struk.append(String.format("TOTAL BAYAR:          Rp. %,.0f\n", totalAkhir));
        struk.append("==============================\n");
        struk.append("Terima kasih!\n");

        String isiStruk = struk.toString();

        // Tampilkan struk di layar
        System.out.println(isiStruk);
        
        // Simpan ke dalam file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(isiStruk);
            System.out.println("✔ Struk berhasil disimpan ke: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Gagal menyimpan struk: " + e.getMessage());
        }
    }
}
