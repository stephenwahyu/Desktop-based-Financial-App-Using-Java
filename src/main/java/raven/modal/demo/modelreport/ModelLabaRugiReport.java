package raven.modal.demo.modelreport;

import java.util.List;

public class ModelLabaRugiReport {
    public ModelLabaRugiReport(String tanggal, String saldoPendapatan, String saldoBeban, String labaRugi, String disetujui, List<ModelLabaRugiTable> table) {
        this.tanggal = tanggal;
        this.saldoPendapatan = saldoPendapatan;
        this.saldoBeban = saldoBeban;
        this.labaRugi = labaRugi;
        this.disetujui = disetujui;
        this.table = table;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSaldoPendapatan() {
        return saldoPendapatan;
    }

    public void setSaldoPendapatan(String saldoPendapatan) {
        this.saldoPendapatan = saldoPendapatan;
    }

    public String getSaldoBeban() {
        return saldoBeban;
    }

    public void setSaldoBeban(String saldoBeban) {
        this.saldoBeban = saldoBeban;
    }

    public String getLabaRugi() {
        return labaRugi;
    }

    public void setLabaRugi(String labaRugi) {
        this.labaRugi = labaRugi;
    }

    public String getDisetujui() {
        return disetujui;
    }

    public void setDisetujui(String disetujui) {
        this.disetujui = disetujui;
    }

    public List<ModelLabaRugiTable> getTable() {
        return table;
    }

    public void setTable(List<ModelLabaRugiTable> table) {
        this.table = table;
    }

    String tanggal;
    String saldoPendapatan;
    String saldoBeban;
    String labaRugi;
    String disetujui;
    List<ModelLabaRugiTable> table;
}
