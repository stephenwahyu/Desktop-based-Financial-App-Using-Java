package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelNeracaTotal {
    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public ModelNeracaTotal(String kategori, int saldo) {
        this.kategori = kategori;
        this.saldo = saldo;
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{kategori,nf.format(saldo)};
    }

    String kategori;
    int saldo;
}
