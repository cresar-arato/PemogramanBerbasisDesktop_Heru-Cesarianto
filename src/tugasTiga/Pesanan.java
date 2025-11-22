package tugasTiga;

import java.io.*;
import java.util.ArrayList;

public class Pesanan {
    private ArrayList<MenuItem> keranjangBelanja;
    private ArrayList<Integer> jumlahPesanan; // Tambahkan untuk menyimpan jumlah

    public Pesanan() {
        this.keranjangBelanja = new ArrayList<>();
        this.jumlahPesanan = new ArrayList<>();
    }

    public void tambahPesanan(MenuItem item, int jumlah) {
        keranjangBelanja.add(item);
        jumlahPesanan.add(jumlah);
        System.out.println("Sukses menambahkan: " + item.getNama() + " x " + jumlah);
    }

    public void cetakStruk() {
        if (keranjangBelanja.isEmpty()) {
            System.out.println("Keranjang pesanan kosong.");
            return;
        }

        StringBuilder struk = new StringBuilder();
        struk.append("\n==============================\n");
        struk.append("       STRUK PEMBELIAN        \n");
        struk.append("==============================\n");
        
        double subTotal = 0;
        double potongan = 0;

        // REVISI: Loop melalui keranjang dan jumlah
        for (int i = 0; i < keranjangBelanja.size(); i++) {
            MenuItem item = keranjangBelanja.get(i);
            int jumlah = jumlahPesanan.get(i);
            
            if (item instanceof Diskon) {
                // Diskon hanya berlaku 1x, terlepas dari jumlah yang dimasukkan user
                struk.append(String.format("%-20s -Rp. %,.0f\n", item.getNama(), ((Diskon) item).getBesarDiskon()));
                potongan += ((Diskon) item).getBesarDiskon();
            } else {
                double hargaItem = item.getHarga() * jumlah;
                struk.append(String.format("%-15s x%d = Rp. %,.0f\n", item.getNama(), jumlah, hargaItem));
                subTotal += hargaItem;
            }
        }
        
        double totalAkhir = subTotal - potongan;
        if(totalAkhir < 0) totalAkhir = 0;

        struk.append("------------------------------\n");
        struk.append(String.format("Subtotal:             Rp. %,.0f\n", subTotal));
        struk.append(String.format("Hemat:               -Rp. %,.0f\n", potongan));
        struk.append(String.format("TOTAL BAYAR:          Rp. %,.0f\n", totalAkhir));
        struk.append("==============================\n");
        struk.append("Terima Kasih!\n");

        // Tampilkan ke layar
        System.out.println(struk.toString());

        // Simpan ke file
        simpanStrukKeFile(struk.toString());
    }

    private void simpanStrukKeFile(String isiStruk) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("struk_pelanggan.txt"))) {
            writer.write(isiStruk);
            System.out.println("[Info] Struk berhasil disimpan ke 'struk_pelanggan.txt'");
        } catch (IOException e) {
            System.err.println("Gagal menyimpan struk: " + e.getMessage());
        }
    }
}
