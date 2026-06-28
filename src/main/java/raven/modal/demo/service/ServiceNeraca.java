package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelAkun;
import raven.modal.demo.model.ModelNeraca;
import raven.modal.demo.model.ModelNeracaTotal;
import raven.modal.demo.model.ModelTransaksi;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceNeraca {
    public List<ModelNeracaTotal> getTotal(Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    kategori,\n" +
                    "    SUM(total_saldo) AS total_saldo\n" +
                    "FROM (\n" +
                    "    SELECT \n" +
                    "        CASE \n" +
                    "            WHEN account_type = 'Aset' THEN 'Aset'\n" +
                    "            WHEN account_type = 'Kewajiban' THEN 'Kewajiban'\n" +
                    "            WHEN account_type = 'Ekuitas' THEN 'Ekuitas'\n" +
                    "        END AS kategori,\n" +
                    "        SUM(amount) AS total_saldo\n" +
                    "    FROM \n" +
                    "        Accounts a\n" +
                    "    JOIN \n" +
                    "        Transactions t ON a.account_id = t.account_id\n" +
                    "    WHERE \n" +
                    "        transaction_date <= ?\n" +
                    "    GROUP BY \n" +
                    "        kategori\n" +
                    "\n" +
                    "    UNION ALL\n" +
                    "\n" +
                    "    SELECT \n" +
                    "        'Kewajiban + Ekuitas' AS kategori,\n" +
                    "        SUM(amount) AS total_saldo\n" +
                    "    FROM \n" +
                    "        Accounts a\n" +
                    "    JOIN \n" +
                    "        Transactions t ON a.account_id = t.account_id\n" +
                    "    WHERE \n" +
                    "        transaction_date <= ?\n" +
                    "        AND account_type IN ('Kewajiban', 'Ekuitas')\n" +
                    ") AS combined\n" +
                    "GROUP BY \n" +
                    "    kategori\n" +
                    "ORDER BY \n" +
                    "    FIELD(kategori, 'Aset', 'Kewajiban', 'Ekuitas', 'Kewajiban + Ekuitas');");
            p.setDate(1,   search);
            p.setDate(2,   search);
            r = p.executeQuery();
            List<ModelNeracaTotal> list = new ArrayList<>();
            while (r.next()) {
                String kategori = r.getString("kategori");
                int totalSaldo = r.getInt("total_saldo");

                list.add(new ModelNeracaTotal(kategori, totalSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelNeracaTotal> getTotalInterval(Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    kategori,\n" +
                    "    SUM(total_saldo) AS total_saldo\n" +
                    "FROM (\n" +
                    "    SELECT \n" +
                    "        CASE \n" +
                    "            WHEN account_type = 'Aset' THEN 'Aset'\n" +
                    "            WHEN account_type = 'Kewajiban' THEN 'Kewajiban'\n" +
                    "            WHEN account_type = 'Ekuitas' THEN 'Ekuitas'\n" +
                    "        END AS kategori,\n" +
                    "        SUM(amount) AS total_saldo\n" +
                    "    FROM \n" +
                    "        Accounts a\n" +
                    "    JOIN \n" +
                    "        Transactions t ON a.account_id = t.account_id\n" +
                    "    WHERE \n" +
                    "        transaction_date BETWEEN ? AND ?\n" +
                    "    GROUP BY \n" +
                    "        kategori\n" +
                    "\n" +
                    "    UNION ALL\n" +
                    "\n" +
                    "    SELECT \n" +
                    "        'Kewajiban + Ekuitas' AS kategori,\n" +
                    "        SUM(amount) AS total_saldo\n" +
                    "    FROM \n" +
                    "        Accounts a\n" +
                    "    JOIN \n" +
                    "        Transactions t ON a.account_id = t.account_id\n" +
                    "    WHERE \n" +
                    "        transaction_date BETWEEN ? AND ?\n" +
                    "        AND account_type IN ('Kewajiban', 'Ekuitas')\n" +
                    ") AS combined\n" +
                    "GROUP BY \n" +
                    "    kategori\n" +
                    "ORDER BY \n" +
                    "    FIELD(kategori, 'Aset', 'Kewajiban', 'Ekuitas', 'Kewajiban + Ekuitas');");
            p.setDate(1,   search1);
            p.setDate(2,   search2);
            p.setDate(3,   search1);
            p.setDate(4,   search2);
            r = p.executeQuery();
            List<ModelNeracaTotal> list = new ArrayList<>();
            while (r.next()) {
                String kategori = r.getString("kategori");
                int totalSaldo = r.getInt("total_saldo");

                list.add(new ModelNeracaTotal(kategori, totalSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelNeraca> getAll(Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    CASE \n" +
                    "        WHEN account_type = 'Aset' THEN 'Aset'\n" +
                    "        WHEN account_type = 'Kewajiban' THEN 'Kewajiban'\n" +
                    "        WHEN account_type = 'Ekuitas' THEN 'Ekuitas'\n" +
                    "    END AS kategori,\n" +
                    "    account_name AS nama_akun,\n" +
                    "    SUM(amount) AS saldo\n" +
                    "FROM \n" +
                    "    Accounts a\n" +
                    "JOIN \n" +
                    "    Transactions t ON a.account_id = t.account_id\n" +
                    "WHERE \n" +
                    "    transaction_date <= ? -- Ganti tanggal sesuai kebutuhan\n" +
                    "GROUP BY \n" +
                    "    kategori, account_name\n" +
                    "ORDER BY \n" +
                    "    FIELD(kategori, 'Aset', 'Kewajiban', 'Ekuitas'), nama_akun;");
            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelNeraca> list = new ArrayList<>();
            while (r.next()) {
                String kategori = r.getString("kategori");
                String namaAkun = r.getString("nama_akun");
                int totalSaldo = r.getInt("saldo");

                list.add(new ModelNeraca(kategori, namaAkun, totalSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelNeraca> getAllInterval(Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    CASE \n" +
                    "        WHEN account_type = 'Aset' THEN 'Aset'\n" +
                    "        WHEN account_type = 'Kewajiban' THEN 'Kewajiban'\n" +
                    "        WHEN account_type = 'Ekuitas' THEN 'Ekuitas'\n" +
                    "    END AS kategori,\n" +
                    "    account_name AS nama_akun,\n" +
                    "    SUM(amount) AS saldo\n" +
                    "FROM \n" +
                    "    Accounts a\n" +
                    "JOIN \n" +
                    "    Transactions t ON a.account_id = t.account_id\n" +
                    "WHERE \n" +
                    "    transaction_date BETWEEN ? AND ? -- Ganti tanggal sesuai kebutuhan\n" +
                    "GROUP BY \n" +
                    "    kategori, account_name\n" +
                    "ORDER BY \n" +
                    "    FIELD(kategori, 'Aset', 'Kewajiban', 'Ekuitas'), nama_akun;");
            p.setDate(1,   search1);
            p.setDate(2,   search2);
            r = p.executeQuery();
            List<ModelNeraca> list = new ArrayList<>();
            while (r.next()) {
                String kategori = r.getString("kategori");
                String namaAkun = r.getString("nama_akun");
                int totalSaldo = r.getInt("saldo");

                list.add(new ModelNeraca(kategori, namaAkun, totalSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
