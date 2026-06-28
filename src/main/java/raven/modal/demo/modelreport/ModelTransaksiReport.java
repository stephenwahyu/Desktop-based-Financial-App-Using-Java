package raven.modal.demo.modelreport;

import java.util.List;

public class ModelTransaksiReport {
    public ModelTransaksiReport(String disetujui, String diterima, int total, List<ModelTransaksiTable> table) {
        this.disetujui = disetujui;
        this.diterima = diterima;
        this.total = total;
        this.table = table;
    }

    public String getDisetujui() {
        return disetujui;
    }

    public void setDisetujui(String disetujui) {
        this.disetujui = disetujui;
    }

    public String getDiterima() {
        return diterima;
    }

    public void setDiterima(String diterima) {
        this.diterima = diterima;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ModelTransaksiTable> getTable() {
        return table;
    }

    public void setTable(List<ModelTransaksiTable> table) {
        this.table = table;
    }

    String disetujui;
    String diterima;
    int total;
    List<ModelTransaksiTable> table;
}
