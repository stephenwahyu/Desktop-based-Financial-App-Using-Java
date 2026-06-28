package raven.modal.demo.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class ModelRingkasanTahunan {
    public ModelRingkasanTahunan() {
    }

    public ModelRingkasanTahunan(int pemasukanTahunIni, int pemasukanTahunLalu, double persentasePemasukan, int pengeluaranTahunIni, int pengeluaranTahunLalu, double persentasePengeluaran, int saldoBersihTahunIni, int saldoBersihTahunLalu, double persentaseSaldoBersih) {
        this.pemasukanTahunIni = pemasukanTahunIni;
        this.pemasukanTahunLalu = pemasukanTahunLalu;
        this.persentasePemasukan = persentasePemasukan;
        this.pengeluaranTahunIni = pengeluaranTahunIni;
        this.pengeluaranTahunLalu = pengeluaranTahunLalu;
        this.persentasePengeluaran = persentasePengeluaran;
        this.saldoBersihTahunIni = saldoBersihTahunIni;
        this.saldoBersihTahunLalu = saldoBersihTahunLalu;
        this.persentaseSaldoBersih = persentaseSaldoBersih;
    }

    public int getPemasukanTahunIni() {
        return pemasukanTahunIni;
    }

    public void setPemasukanTahunIni(int pemasukanTahunIni) {
        this.pemasukanTahunIni = pemasukanTahunIni;
    }

    public int getPemasukanTahunLalu() {
        return pemasukanTahunLalu;
    }

    public void setPemasukanTahunLalu(int pemasukanTahunLalu) {
        this.pemasukanTahunLalu = pemasukanTahunLalu;
    }

    public double getPersentasePemasukan() {
        return persentasePemasukan;
    }

    public void setPersentasePemasukan(double persentasePemasukan) {
        this.persentasePemasukan = persentasePemasukan;
    }

    public int getPengeluaranTahunIni() {
        return pengeluaranTahunIni;
    }

    public void setPengeluaranTahunIni(int pengeluaranTahunIni) {
        this.pengeluaranTahunIni = pengeluaranTahunIni;
    }

    public int getPengeluaranTahunLalu() {
        return pengeluaranTahunLalu;
    }

    public void setPengeluaranTahunLalu(int pengeluaranTahunLalu) {
        this.pengeluaranTahunLalu = pengeluaranTahunLalu;
    }

    public double getPersentasePengeluaran() {
        return persentasePengeluaran;
    }

    public void setPersentasePengeluaran(double persentasePengeluaran) {
        this.persentasePengeluaran = persentasePengeluaran;
    }

    public int getSaldoBersihTahunIni() {
        return saldoBersihTahunIni;
    }

    public void setSaldoBersihTahunIni(int saldoBersihTahunIni) {
        this.saldoBersihTahunIni = saldoBersihTahunIni;
    }

    public int getSaldoBersihTahunLalu() {
        return saldoBersihTahunLalu;
    }

    public void setSaldoBersihTahunLalu(int saldoBersihTahunLalu) {
        this.saldoBersihTahunLalu = saldoBersihTahunLalu;
    }

    public double getPersentaseSaldoBersih() {
        return persentaseSaldoBersih;
    }

    public void setPersentaseSaldoBersih(double persentaseSaldoBersih) {
        this.persentaseSaldoBersih = persentaseSaldoBersih;
    }

    int pemasukanTahunIni;
    int pemasukanTahunLalu;
    double persentasePemasukan;
    int pengeluaranTahunIni;
    int pengeluaranTahunLalu;
    double persentasePengeluaran;
    int saldoBersihTahunIni;
    int saldoBersihTahunLalu;
    double persentaseSaldoBersih;
}
