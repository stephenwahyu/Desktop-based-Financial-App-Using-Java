package raven.modal.demo.model;

public class ModelTSBulanan {
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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

    public ModelTSBulanan(int month, int year, int pemasukan, int pengeluaran) {
        this.year = year;
        this.month = month;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
    }

    public ModelTSBulanan() {
    }

    int year;
    int month;
    int pemasukan;
    int pengeluaran;
}
