package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelRingkasanBulanan {
    public ModelRingkasanBulanan() {
    }

    public ModelRingkasanBulanan(int pemasukanBulanIni, int pemasukanBulanLalu, double persentasePemasukan, int pengeluaranBulanIni, int pengeluaranBulanLalu, double persentasePengeluaran, int saldoBersihBulanIni, int saldoBersihBulanLalu, double persentaseSaldoBersih) {
        this.pemasukanBulanIni = pemasukanBulanIni;
        this.pemasukanBulanLalu = pemasukanBulanLalu;
        this.persentasePemasukan = persentasePemasukan;
        this.pengeluaranBulanIni = pengeluaranBulanIni;
        this.pengeluaranBulanLalu = pengeluaranBulanLalu;
        this.persentasePengeluaran = persentasePengeluaran;
        this.saldoBersihBulanIni = saldoBersihBulanIni;
        this.saldoBersihBulanLalu = saldoBersihBulanLalu;
        this.persentaseSaldoBersih = persentaseSaldoBersih;
    }

    public int getPemasukanBulanIni() {
        return pemasukanBulanIni;
    }

    public void setPemasukanBulanIni(int pemasukanBulanIni) {
        this.pemasukanBulanIni = pemasukanBulanIni;
    }

    public int getPemasukanBulanLalu() {
        return pemasukanBulanLalu;
    }

    public void setPemasukanBulanLalu(int pemasukanBulanLalu) {
        this.pemasukanBulanLalu = pemasukanBulanLalu;
    }

    public double getPersentasePemasukan() {
        return persentasePemasukan;
    }

    public void setPersentasePemasukan(double persentasePemasukan) {
        this.persentasePemasukan = persentasePemasukan;
    }

    public int getPengeluaranBulanIni() {
        return pengeluaranBulanIni;
    }

    public void setPengeluaranBulanIni(int pengeluaranBulanIni) {
        this.pengeluaranBulanIni = pengeluaranBulanIni;
    }

    public int getPengeluaranBulanLalu() {
        return pengeluaranBulanLalu;
    }

    public void setPengeluaranBulanLalu(int pengeluaranBulanLalu) {
        this.pengeluaranBulanLalu = pengeluaranBulanLalu;
    }

    public double getPersentasePengeluaran() {
        return persentasePengeluaran;
    }

    public void setPersentasePengeluaran(double persentasePengeluaran) {
        this.persentasePengeluaran = persentasePengeluaran;
    }

    public int getSaldoBersihBulanIni() {
        return saldoBersihBulanIni;
    }

    public void setSaldoBersihBulanIni(int saldoBersihBulanIni) {
        this.saldoBersihBulanIni = saldoBersihBulanIni;
    }

    public int getSaldoBersihBulanLalu() {
        return saldoBersihBulanLalu;
    }

    public void setSaldoBersihBulanLalu(int saldoBersihBulanLalu) {
        this.saldoBersihBulanLalu = saldoBersihBulanLalu;
    }

    public double getPersentaseSaldoBersih() {
        return persentaseSaldoBersih;
    }

    public void setPersentaseSaldoBersih(double persentaseSaldoBersih) {
        this.persentaseSaldoBersih = persentaseSaldoBersih;
    }

    int pemasukanBulanIni;
    int pemasukanBulanLalu;
    double persentasePemasukan;
    int pengeluaranBulanIni;
    int pengeluaranBulanLalu;
    double persentasePengeluaran;
    int saldoBersihBulanIni;
    int saldoBersihBulanLalu;
    double persentaseSaldoBersih;
}

