package raven.modal.demo.modelreport;

import java.util.List;

public class ModelNeracaReport {
    public ModelNeracaReport(String tanggal, String saldoAset, String saldoKewajiban, String saldoEkuitas, String saldoKewajibanEkuitas, String disetujui, List<ModelNeracaTable> table) {
        this.tanggal = tanggal;
        this.saldoAset = saldoAset;
        this.saldoKewajiban = saldoKewajiban;
        this.saldoEkuitas = saldoEkuitas;
        this.saldoKewajibanEkuitas = saldoKewajibanEkuitas;
        this.disetujui = disetujui;
        this.table = table;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getSaldoAset() {
        return saldoAset;
    }

    public void setSaldoAset(String saldoAset) {
        this.saldoAset = saldoAset;
    }

    public String getSaldoKewajiban() {
        return saldoKewajiban;
    }

    public void setSaldoKewajiban(String saldoKewajiban) {
        this.saldoKewajiban = saldoKewajiban;
    }

    public String getSaldoEkuitas() {
        return saldoEkuitas;
    }

    public void setSaldoEkuitas(String saldoEkuitas) {
        this.saldoEkuitas = saldoEkuitas;
    }

    public String getSaldoKewajibanEkuitas() {
        return saldoKewajibanEkuitas;
    }

    public void setSaldoKewajibanEkuitas(String saldoKewajibanEkuitas) {
        this.saldoKewajibanEkuitas = saldoKewajibanEkuitas;
    }

    public String getDisetujui() {
        return disetujui;
    }

    public void setDisetujui(String disetujui) {
        this.disetujui = disetujui;
    }

    public List<ModelNeracaTable> getTable() {
        return table;
    }

    public void setTable(List<ModelNeracaTable> table) {
        this.table = table;
    }

    String tanggal;
    String saldoAset;
    String saldoKewajiban;
    String saldoEkuitas;
    String saldoKewajibanEkuitas;
    String disetujui;
    List<ModelNeracaTable> table;
}
