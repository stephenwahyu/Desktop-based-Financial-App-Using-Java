package raven.modal.demo.modelreport;

public class ModelLabaRugiTable {
    public ModelLabaRugiTable(String tipeAkun, String namaAkun, String jumlah) {
        this.tipeAkun = tipeAkun;
        this.namaAkun = namaAkun;
        this.jumlah = jumlah;
    }

    public String getTipeAkun() {
        return tipeAkun;
    }

    public void setTipeAkun(String tipeAkun) {
        this.tipeAkun = tipeAkun;
    }

    public String getNamaAkun() {
        return namaAkun;
    }

    public void setNamaAkun(String namaAkun) {
        this.namaAkun = namaAkun;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    String tipeAkun;
    String namaAkun;
    String jumlah;
}
