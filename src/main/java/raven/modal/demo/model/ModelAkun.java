package raven.modal.demo.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ModelAkun {
    public int getKodeAkun() {
        return kodeAkun;
    }

    public void setKodeAkun(int kodeAkun) {
        this.kodeAkun = kodeAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public String getTipeAkun() {
        return tipeAkun;
    }

    public void setTipeAkun(String tipeAkun) {
        this.tipeAkun = tipeAkun;
    }

    public ModelAkun(int kodeRekening, String namaAkun, String tipeAkun) {
        this.kodeAkun = kodeRekening;
        this.namaAkun = namaAkun;
        this.tipeAkun = tipeAkun;
    }

    public ModelAkun() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        return new Object[]{false, kodeAkun, this, tipeAkun};
    }

    int kodeAkun;
    String namaAkun;
    String tipeAkun;

    @Override
    public String toString() {
        return namaAkun;
    }
}
