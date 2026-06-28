package raven.modal.demo.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.sql.Date;

public class ModelTransaksi {
    public int getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(int kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public ModelAkun getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(ModelAkun namaAkun) {
        this.namaAkun = namaAkun;
    }

    public String getTipeTransaksi() {
        return tipeTransaksi;
    }

    public void setTipeTransaksi(String tipeTransaksi) {
        this.tipeTransaksi = tipeTransaksi;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Date tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
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

    public ModelTransaksi(int kodeTransaksi, ModelAkun namaAkun, String tipeTransaksi, Date tanggalTransaksi, int jumlah, String deskripsi) {
        this.kodeTransaksi = kodeTransaksi;
        this.namaAkun = namaAkun;
        this.tipeTransaksi = tipeTransaksi;
        this.tanggalTransaksi = tanggalTransaksi;
        this.jumlah = jumlah;
        this.deskripsi = deskripsi;
    }

    public ModelTransaksi() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{false, this, namaAkun.getNamaAkun(), tipeTransaksi, tanggalTransaksi == null ? "" : df.format(tanggalTransaksi), nf.format(jumlah), deskripsi};
    }

    int kodeTransaksi;
    ModelAkun namaAkun;
    String tipeTransaksi;
    Date tanggalTransaksi;
    int jumlah;
    String deskripsi;

    @Override
    public String toString() {
        return String.valueOf(kodeTransaksi);
    }
}
