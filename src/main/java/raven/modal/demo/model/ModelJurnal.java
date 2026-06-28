package raven.modal.demo.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelJurnal {
    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public Date getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(Date tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public int getKredit() {
        return kredit;
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public ModelJurnal(String namaAkun, Date tanggalTransaksi, int debit, int kredit, String deskripsi) {
        this.namaAkun = namaAkun;
        this.tanggalTransaksi = tanggalTransaksi;
        this.debit = debit;
        this.kredit = kredit;
        this.deskripsi = deskripsi;
    }

    public ModelJurnal() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{namaAkun, tanggalTransaksi == null ? "" : df.format(tanggalTransaksi), nf.format(debit), nf.format(kredit), deskripsi};
    }

    String namaAkun;
    Date tanggalTransaksi;
    int debit;
    int kredit;
    String deskripsi;
}
