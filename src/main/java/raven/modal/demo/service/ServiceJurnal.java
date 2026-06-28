package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelJurnal;
import raven.modal.demo.model.ModelLaba;
import raven.modal.demo.model.ModelLabaTotal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJurnal {
    public List<ModelJurnal> getAll(Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    t.transaction_date AS 'tanggal_transaksi',\n" +
                    "    a.account_name AS 'nama_akun',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pemasukan' THEN t.amount\n" +
                    "        ELSE 0\n" +
                    "    END AS 'debit',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pengeluaran' THEN ABS(t.amount)\n" +
                    "        ELSE 0\n" +
                    "    END AS 'kredit',\n" +
                    "    t.description AS 'deskripsi'\n" +
                    "FROM \n" +
                    "    Transactions t\n" +
                    "JOIN \n" +
                    "    Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE t.transaction_date <= ?\n" +
                    "ORDER BY \n" +
                    "    a.account_name, t.transaction_date;");
            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelJurnal> list = new ArrayList<>();
            while (r.next()) {
                String namaAkun = r.getString("nama_akun");
                Date tanggalTransaksi = r.getDate("tanggal_transaksi");
                int debit = r.getInt("debit");
                int kredit = r.getInt("kredit");
                String deskripsi = r.getString("deskripsi");
                list.add(new ModelJurnal(namaAkun, tanggalTransaksi, debit, kredit, deskripsi));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelJurnal> getAllInterval(Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    t.transaction_date AS 'tanggal_transaksi',\n" +
                    "    a.account_name AS 'nama_akun',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pemasukan' THEN t.amount\n" +
                    "        ELSE 0\n" +
                    "    END AS 'debit',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pengeluaran' THEN ABS(t.amount)\n" +
                    "        ELSE 0\n" +
                    "    END AS 'kredit',\n" +
                    "    t.description AS 'deskripsi'\n" +
                    "FROM \n" +
                    "    Transactions t\n" +
                    "JOIN \n" +
                    "    Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE t.transaction_date BETWEEN ? AND ?\n" +
                    "ORDER BY \n" +
                    "    a.account_name, t.transaction_date;");
            p.setDate(1,   search1);
            p.setDate(2,   search2);
            r = p.executeQuery();
            List<ModelJurnal> list = new ArrayList<>();
            while (r.next()) {
                String namaAkun = r.getString("nama_akun");
                Date tanggalTransaksi = r.getDate("tanggal_transaksi");
                int debit = r.getInt("debit");
                int kredit = r.getInt("kredit");
                String deskripsi = r.getString("deskripsi");
                list.add(new ModelJurnal(namaAkun, tanggalTransaksi, debit, kredit, deskripsi));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelJurnal> getAll(String word, Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    t.transaction_date AS 'tanggal_transaksi',\n" +
                    "    a.account_name AS 'nama_akun',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pemasukan' THEN t.amount\n" +
                    "        ELSE 0\n" +
                    "    END AS 'debit',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pengeluaran' THEN ABS(t.amount)\n" +
                    "        ELSE 0\n" +
                    "    END AS 'kredit',\n" +
                    "    t.description AS 'deskripsi'\n" +
                    "FROM \n" +
                    "    Transactions t\n" +
                    "JOIN \n" +
                    "    Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_name like ? AND t.transaction_date <= ?\n" +
                    "ORDER BY \n" +
                    "    a.account_name, t.transaction_date;");
            p.setString(1,   word + "%");
            p.setDate(2,   search);
            r = p.executeQuery();
            List<ModelJurnal> list = new ArrayList<>();
            while (r.next()) {
                String namaAkun = r.getString("nama_akun");
                Date tanggalTransaksi = r.getDate("tanggal_transaksi");
                int debit = r.getInt("debit");
                int kredit = r.getInt("kredit");
                String deskripsi = r.getString("deskripsi");
                list.add(new ModelJurnal(namaAkun, tanggalTransaksi, debit, kredit, deskripsi));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelJurnal> getAllInterval(String word, Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    t.transaction_date AS 'tanggal_transaksi',\n" +
                    "    a.account_name AS 'nama_akun',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pemasukan' THEN t.amount\n" +
                    "        ELSE 0\n" +
                    "    END AS 'debit',\n" +
                    "    CASE \n" +
                    "        WHEN t.transaction_type = 'Pengeluaran' THEN ABS(t.amount)\n" +
                    "        ELSE 0\n" +
                    "    END AS 'kredit',\n" +
                    "    t.description AS 'deskripsi'\n" +
                    "FROM \n" +
                    "    Transactions t\n" +
                    "JOIN \n" +
                    "    Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_name like ? AND t.transaction_date BETWEEN ? AND ?\n" +
                    "ORDER BY \n" +
                    "    a.account_name, t.transaction_date;");
            p.setString(1,   word + "%");
            p.setDate(2,   search1);
            p.setDate(3,   search2);
            r = p.executeQuery();
            List<ModelJurnal> list = new ArrayList<>();
            while (r.next()) {
                String namaAkun = r.getString("nama_akun");
                Date tanggalTransaksi = r.getDate("tanggal_transaksi");
                int debit = r.getInt("debit");
                int kredit = r.getInt("kredit");
                String deskripsi = r.getString("deskripsi");
                list.add(new ModelJurnal(namaAkun, tanggalTransaksi, debit, kredit, deskripsi));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
