package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelRingkasanBulanan;
import raven.modal.demo.model.ModelRingkasanTahunan;
import raven.modal.demo.model.ModelTSBulanan;
import raven.modal.demo.model.ModelTSHarian;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDashboard {
    public List<ModelRingkasanBulanan> getBulanan() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT\n" +
                    "    -- Data Pemasukan\n" +
                    "    COALESCE(this_month.total_pemasukan, 0) AS pemasukan_bulan_ini,\n" +
                    "    COALESCE(last_month.total_pemasukan, 0) AS pemasukan_bulan_lalu,\n" +
                    "    ((COALESCE(this_month.total_pemasukan, 0) - COALESCE(last_month.total_pemasukan, 0)) / COALESCE(last_month.total_pemasukan, 0)) AS persentase_pemasukan,\n" +
                    "    \n" +
                    "    -- Data Pengeluaran\n" +
                    "    ABS(COALESCE(this_month.total_pengeluaran, 0)) AS pengeluaran_bulan_ini,\n" +
                    "    ABS(COALESCE(last_month.total_pengeluaran, 0)) AS pengeluaran_bulan_lalu,\n" +
                    "    ((COALESCE(this_month.total_pengeluaran, 0) - COALESCE(last_month.total_pengeluaran, 0)) / COALESCE(last_month.total_pengeluaran, 0)) AS persentase_pengeluaran,\n" +
                    "    \n" +
                    "    -- Data Saldo Bersih\n" +
                    "    COALESCE(this_month.saldo_bersih, 0) AS saldo_bersih_bulan_ini,\n" +
                    "    COALESCE(last_month.saldo_bersih, 0) AS saldo_bersih_bulan_lalu,\n" +
                    "    ((COALESCE(this_month.saldo_bersih, 0) - COALESCE(last_month.saldo_bersih, 0)) / COALESCE(last_month.saldo_bersih, 0)) AS persentase_saldo_bersih\n" +
                    "FROM\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) AS total_pemasukan,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS total_pengeluaran,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) +\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS saldo_bersih\n" +
                    "        FROM Transactions\n" +
                    "        WHERE MONTH(transaction_date) = MONTH(CURRENT_DATE)\n" +
                    "          AND YEAR(transaction_date) = YEAR(CURRENT_DATE)\n" +
                    "    ) AS this_month,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) AS total_pemasukan,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS total_pengeluaran,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) +\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS saldo_bersih\n" +
                    "        FROM Transactions\n" +
                    "        WHERE \n" +
                    "            -- Jika bulan ini adalah Januari, bandingkan dengan Desember tahun sebelumnya\n" +
                    "            (MONTH(CURRENT_DATE) = 1 AND MONTH(transaction_date) = 12 AND YEAR(transaction_date) = YEAR(CURRENT_DATE) - 1)\n" +
                    "            -- Jika bukan Januari, bandingkan dengan bulan sebelumnya pada tahun yang sama\n" +
                    "            OR (MONTH(CURRENT_DATE) != 1 AND MONTH(transaction_date) = MONTH(CURRENT_DATE) - 1 AND YEAR(transaction_date) = YEAR(CURRENT_DATE))\n" +
                    "    ) AS last_month;");
//            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelRingkasanBulanan> list = new ArrayList<>();
            while (r.next()) {
                int pemasukanBulanIni = r.getInt("pemasukan_bulan_ini");
                int pemasukanBulanLalu = r.getInt("pemasukan_bulan_lalu");
                double persentasiPemasukan = r.getDouble("persentase_pemasukan");
                int pengeluaranBulanIni = r.getInt("pengeluaran_bulan_ini");
                int pengeluaranBulanLalu = r.getInt("pengeluaran_bulan_lalu");
                double persentasiPengeluaran = r.getDouble("persentase_pengeluaran");
                int saldoBulanIni = r.getInt("saldo_bersih_bulan_ini");
                int saldoBulanlalu = r.getInt("saldo_bersih_bulan_lalu");
                double persentaseSaldo = r.getDouble("persentase_saldo_bersih");

                list.add(new ModelRingkasanBulanan(pemasukanBulanIni,pemasukanBulanLalu, persentasiPemasukan,pengeluaranBulanIni,pengeluaranBulanLalu, persentasiPengeluaran,saldoBulanIni,saldoBulanlalu, persentaseSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelRingkasanTahunan> getTahunan() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT\n" +
                    "    -- Data Pemasukan\n" +
                    "    COALESCE(this_year.total_pemasukan, 0) AS pemasukan_tahun_ini,\n" +
                    "    COALESCE(last_year.total_pemasukan, 0) AS pemasukan_tahun_lalu,\n" +
                    "    ((COALESCE(this_year.total_pemasukan, 0) - COALESCE(last_year.total_pemasukan, 0)) / COALESCE(last_year.total_pemasukan, 0)) AS persentase_pemasukan,\n" +
                    "    \n" +
                    "    -- Data Pengeluaran\n" +
                    "    ABS(COALESCE(this_year.total_pengeluaran, 0)) AS pengeluaran_tahun_ini,\n" +
                    "    ABS(COALESCE(last_year.total_pengeluaran, 0)) AS pengeluaran_tahun_lalu,\n" +
                    "    ((COALESCE(this_year.total_pengeluaran, 0) - COALESCE(last_year.total_pengeluaran, 0)) / COALESCE(last_year.total_pengeluaran, 0)) AS persentase_pengeluaran,\n" +
                    "    \n" +
                    "    -- Data Saldo Bersih\n" +
                    "    COALESCE(this_year.saldo_bersih, 0) AS saldo_bersih_tahun_ini,\n" +
                    "    COALESCE(last_year.saldo_bersih, 0) AS saldo_bersih_tahun_lalu,\n" +
                    "    ((COALESCE(this_year.saldo_bersih, 0) - COALESCE(last_year.saldo_bersih, 0)) / COALESCE(last_year.saldo_bersih, 0)) AS persentase_saldo_bersih\n" +
                    "FROM\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) AS total_pemasukan,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS total_pengeluaran,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) +\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS saldo_bersih\n" +
                    "        FROM Transactions\n" +
                    "        WHERE YEAR(transaction_date) = YEAR(CURRENT_DATE)\n" +
                    "    ) AS this_year,\n" +
                    "    (\n" +
                    "        SELECT\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) AS total_pemasukan,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS total_pengeluaran,\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pemasukan' THEN amount ELSE 0 END), 0) +\n" +
                    "            COALESCE(SUM(CASE WHEN transaction_type = 'Pengeluaran' THEN amount ELSE 0 END), 0) AS saldo_bersih\n" +
                    "        FROM Transactions\n" +
                    "        WHERE YEAR(transaction_date) = YEAR(CURRENT_DATE) - 1\n" +
                    "    ) AS last_year;");
//            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelRingkasanTahunan> list = new ArrayList<>();
            while (r.next()) {
                int pemasukanTahunIni = r.getInt("pemasukan_tahun_ini");
                int pemasukanTahunLalu = r.getInt("pemasukan_tahun_lalu");
                double persentasiPemasukan = r.getDouble("persentase_pemasukan");
                int pengeluaranTahunIni = r.getInt("pengeluaran_tahun_ini");
                int pengeluaranTahunLalu = r.getInt("pengeluaran_tahun_lalu");
                double persentasiPengeluaran = r.getDouble("persentase_pengeluaran");
                int saldoTahunIni = r.getInt("saldo_bersih_tahun_ini");
                int saldoTahunlalu = r.getInt("saldo_bersih_tahun_lalu");
                double persentaseSaldo = r.getDouble("persentase_saldo_bersih");

                list.add(new ModelRingkasanTahunan(pemasukanTahunIni,pemasukanTahunLalu, persentasiPemasukan,pengeluaranTahunIni,pengeluaranTahunLalu, persentasiPengeluaran,saldoTahunIni,saldoTahunlalu, persentaseSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelTSBulanan> getTSBulanan() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("WITH RECURSIVE month_series AS (\n" +
                    "    SELECT\n" +
                    "        -- Starting point: Current month minus 12 months, but exclude current month\n" +
                    "        DATE_FORMAT(DATE_SUB(CURRENT_DATE, INTERVAL 11 MONTH), '%Y-%m-01') AS first_day_of_month\n" +
                    "    UNION ALL\n" +
                    "    SELECT\n" +
                    "        DATE_FORMAT(DATE_ADD(first_day_of_month, INTERVAL 1 MONTH), '%Y-%m-01')\n" +
                    "    FROM\n" +
                    "        month_series\n" +
                    "    WHERE\n" +
                    "        first_day_of_month < DATE_FORMAT(CURRENT_DATE, '%Y-%m-01') -- Only up until the start of current month\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    YEAR(ms.first_day_of_month) AS year,\n" +
                    "    MONTH(ms.first_day_of_month) AS month,\n" +
                    "    COALESCE(SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END), 0) AS income,\n" +
                    "    ABS(COALESCE(SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END), 0)) AS expense\n" +
                    "FROM\n" +
                    "    month_series ms\n" +
                    "LEFT JOIN\n" +
                    "    Transactions t\n" +
                    "    ON YEAR(t.transaction_date) = YEAR(ms.first_day_of_month)\n" +
                    "    AND MONTH(t.transaction_date) = MONTH(ms.first_day_of_month)\n" +
                    "GROUP BY\n" +
                    "    ms.first_day_of_month\n" +
                    "ORDER BY\n" +
                    "    ms.first_day_of_month;");
//            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelTSBulanan> list = new ArrayList<>();
            while (r.next()) {
                int bulan = r.getInt("month");
                int tahun = r.getInt("year");
                int pemasukan = r.getInt("income");
                int pengeluaran = r.getInt("expense");

                list.add(new ModelTSBulanan(bulan, tahun, pemasukan, pengeluaran));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelTSHarian> getTSHarian() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("WITH RECURSIVE calendar AS (\n" +
                    "    SELECT DATE_SUB(CURRENT_DATE, INTERVAL 30 DAY) AS transaction_date\n" +
                    "    UNION ALL\n" +
                    "    SELECT DATE_ADD(transaction_date, INTERVAL 1 DAY)\n" +
                    "    FROM calendar\n" +
                    "    WHERE transaction_date < CURRENT_DATE\n" +
                    ")\n" +
                    "SELECT\n" +
                    "    YEAR(c.transaction_date) AS year,\n" +
                    "    MONTH(c.transaction_date) AS month,\n" +
                    "    DAY(c.transaction_date) AS day,\n" +
                    "    COALESCE(SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END), 0) AS income,\n" +
                    "    ABS(COALESCE(SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END), 0)) AS expense\n" +
                    "FROM\n" +
                    "    calendar c\n" +
                    "LEFT JOIN\n" +
                    "    Transactions t\n" +
                    "ON\n" +
                    "    DATE(t.transaction_date) = c.transaction_date\n" +
                    "GROUP BY\n" +
                    "    c.transaction_date\n" +
                    "ORDER BY\n" +
                    "    c.transaction_date;");
//            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelTSHarian> list = new ArrayList<>();
            while (r.next()) {
                int hari = r.getInt("day");
                int bulan = r.getInt("month");
                int tahun = r.getInt("year");
                int pemasukan = r.getInt("income");
                int pengeluaran = r.getInt("expense");

                list.add(new ModelTSHarian(hari, bulan, tahun, pemasukan, pengeluaran));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
