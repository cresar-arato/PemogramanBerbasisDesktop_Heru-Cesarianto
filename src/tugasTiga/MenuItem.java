package tugasTiga;

/**
 * MenuItem.java
 * Berisi hirarki kelas item menu (Parent dan Child classes).
 */

public abstract class MenuItem {
    // Encapsulation: Field private
    private String nama;
    private double harga;
    private String kategori;

    public MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    // Getter
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public String getKategori() { return kategori; }
    
    // Abstract method
    public abstract void tampilMenu();
    public abstract String toFileString();
}

// --- Subkelas Makanan ---
class Makanan extends MenuItem {
    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("🍛 [Makanan] %-20s | Jenis: %-10s | Rp. %,.0f\n", 
            getNama(), jenisMakanan, getHarga());
    }

    @Override
    public String toFileString() {
        return "Makanan;" + getNama() + ";" + getHarga() + ";" + jenisMakanan;
    }
}

// --- Subkelas Minuman ---
class Minuman extends MenuItem {
    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("🥤 [Minuman] %-20s | Jenis: %-10s | Rp. %,.0f\n", 
            getNama(), jenisMinuman, getHarga());
    }

    @Override
    public String toFileString() {
        return "Minuman;" + getNama() + ";" + getHarga() + ";" + jenisMinuman;
    }
}

// --- Subkelas Diskon ---
class Diskon extends MenuItem {
    private double besarDiskon; 

    public Diskon(String nama, double besarDiskon) {
        super(nama, 0, "Diskon");
        this.besarDiskon = besarDiskon;
    }

    public double getBesarDiskon() {
        return besarDiskon;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("🎟️ [Promo]   %-20s | Potongan: Rp. %,.0f\n", 
            getNama(), besarDiskon);
    }

    @Override
    public String toFileString() {
        return "Diskon;" + getNama() + ";" + besarDiskon + ";Promo";
    }
}
