package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelAnggaran {
    public int getKodeAnggaran() {
        return kodeAnggaran;
    }

    public void setKodeAnggaran(int kodeAnggaran) {
        this.kodeAnggaran = kodeAnggaran;
    }

    public ModelAkun getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(ModelAkun namaAkun) {
        this.namaAkun = namaAkun;
    }

    public int getJumlahAnggaran() {
        return jumlahAnggaran;
    }

    public void setJumlahAnggaran(int jumlahAnggaran) {
        this.jumlahAnggaran = jumlahAnggaran;
    }

    public String getPeriodeAnggaran() {
        return periodeAnggaran;
    }

    public void setPeriodeAnggaran(String periodeAnggaran) {
        this.periodeAnggaran = periodeAnggaran;
    }

    public ModelAnggaran() {
    }

    public ModelAnggaran(int kodeAnggaran, ModelAkun namaAkun, int jumlahAnggaran, String periodeAnggaran) {
        this.kodeAnggaran = kodeAnggaran;
        this.namaAkun = namaAkun;
        this.jumlahAnggaran = jumlahAnggaran;
        this.periodeAnggaran = periodeAnggaran;
    }

    int kodeAnggaran;
    ModelAkun namaAkun;
    int jumlahAnggaran;
    String periodeAnggaran;

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{false, this, namaAkun.getNamaAkun(), nf.format(jumlahAnggaran), periodeAnggaran};
    }

    @Override
    public String toString() {
        return String.valueOf(kodeAnggaran);
    }
}
