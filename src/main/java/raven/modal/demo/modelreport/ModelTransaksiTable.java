package raven.modal.demo.modelreport;

public class ModelTransaksiTable {
    public ModelTransaksiTable(String namaAkun, String tanggal, String deskripsi, int jumlah) {
        this.namaAkun = namaAkun;
        this.tanggal = tanggal;
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }
    //    public Object[] toTableRow(int row) {
//        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
//        return new Object[]{this, };
//    }

    String namaAkun;
    String tanggal;
    String deskripsi;
    int jumlah;


}
