package raven.modal.demo.model;

public class ModelTSHarian {
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public ModelTSHarian(int day, int month, int year, int pemasukan, int pengeluaran) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.pemasukan = pemasukan;
        this.pengeluaran = pengeluaran;
    }

    public ModelTSHarian() {
    }

    int day;
    int month;
    int year;
    int pemasukan;
    int pengeluaran;
}
