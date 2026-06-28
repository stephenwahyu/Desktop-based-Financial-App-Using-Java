package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelArusKas {
    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getPemasukan() {
        return pemasukan;
    }

    public void setPemasukan(int pemasukan) {
        this.pemasukan = pemasukan;
    }

    public int getPengeluaran() {
        return pengeluaran;
    }

    public void setPengeluaran(int pengeluaran) {
        this.pengeluaran = pengeluaran;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ModelArusKas(String kategori, int pemasukan, int pengeluaran, int total) {
        this.kategori = kategori;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
        this.total = total;
    }

    public ModelArusKas() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{kategori, nf.format(pemasukan), nf.format(pengeluaran), nf.format(total)};
    }

    String kategori;
    int pemasukan;
    int pengeluaran;
    int total;
}
