package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelLabaTotal {
    public int getTotalPendapatan() {
        return totalPendapatan;
    }

    public void setTotalPendapatan(int totalPendapatan) {
        this.totalPendapatan = totalPendapatan;
    }

    public int getTotalBeban() {
        return totalBeban;
    }

    public void setTotalBeban(int totalBeban) {
        this.totalBeban = totalBeban;
    }

    public int getLabaRugiBersih() {
        return labaRugiBersih;
    }

    public void setLabaRugiBersih(int labaRugiBersih) {
        this.labaRugiBersih = labaRugiBersih;
    }

    public ModelLabaTotal(int totalPendapatan, int totalBeban, int labaRugiBersih) {
        this.totalPendapatan = totalPendapatan;
        this.totalBeban = totalBeban;
        this.labaRugiBersih = labaRugiBersih;
    }

    public ModelLabaTotal() {
    }

    public Object[] toTableRow(int row) {
        NumberFormat nf = new DecimalFormat(" Rp#,##0.##");
        DateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        return new Object[]{nf.format(totalPendapatan), nf.format(totalBeban), nf.format(labaRugiBersih)};
    }

    int totalPendapatan;
    int totalBeban;
    int labaRugiBersih;
}
