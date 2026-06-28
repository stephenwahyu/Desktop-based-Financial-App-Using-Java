package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelNeraca {
    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public ModelNeraca(String kategori, String namaAkun, int saldo) {
        this.kategori = kategori;
        this.namaAkun = namaAkun;
        this.saldo = saldo;
    }

    public ModelNeraca() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{kategori == "" ? null : kategori, namaAkun, nf.format(saldo)};
    }

    String kategori;
    String namaAkun;
    int saldo;
}
