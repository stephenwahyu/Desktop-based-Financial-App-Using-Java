package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelLaba {
    public String getJenisAkun() {
        return tipeAkun;
    }

    public void setJenisAkun(String jenisAkun) {
        this.tipeAkun = jenisAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public ModelLaba(String jenisAkun, String namaAkun, int jumlah) {
        this.tipeAkun = jenisAkun;
        this.namaAkun = namaAkun;
        this.jumlah = jumlah;
    }

    public ModelLaba() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{tipeAkun, namaAkun, nf.format(jumlah)};
    }

    String tipeAkun;
    String namaAkun;
    int jumlah;
}
