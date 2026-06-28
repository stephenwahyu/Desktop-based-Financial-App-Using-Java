package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelArusKas;
import raven.modal.demo.model.ModelLaba;
import raven.modal.demo.model.ModelLabaTotal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceArusKas {
    public List<ModelArusKas> getAll(Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    'Aktivitas Operasi' AS kategori,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) AS pemasukan,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS pengeluaran,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) + \n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS total\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_type IN ('Pendapatan', 'Beban')\n" +
                    "AND t.transaction_date <= ? \n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT \n" +
                    "    'Aktivitas Investasi' AS kategori,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) AS pemasukan,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS pengeluaran,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) + \n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS total\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_type IN ('Aset')\n" +
                    "AND t.transaction_date <= ? \n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT \n" +
                    "    'Aktivitas Pendanaan' AS kategori,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) AS pemasukan,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS pengeluaran,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) + \n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS total\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_type IN ('Kewajiban', 'Ekuitas')\n" +
                    "AND t.transaction_date <= ? ;");
            p.setDate(1,   search);
            p.setDate(2,   search);
            p.setDate(3,   search);
            r = p.executeQuery();
            List<ModelArusKas> list = new ArrayList<>();
            while (r.next()) {
                String kategori = r.getString("kategori");
                int pemasukan = r.getInt("pemasukan");
                int pengeluaran = r.getInt("pengeluaran");
                int total = r.getInt("total");

                list.add(new ModelArusKas(kategori, pemasukan, pengeluaran, total));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelArusKas> getAllInterval(Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    'Aktivitas Operasi' AS kategori,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) AS pemasukan,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS pengeluaran,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) + \n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS total\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_type IN ('Pendapatan', 'Beban')\n" +
                    "AND t.transaction_date BETWEEN ? AND ?\n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT \n" +
                    "    'Aktivitas Investasi' AS kategori,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) AS pemasukan,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS pengeluaran,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) + \n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS total\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_type IN ('Aset')\n" +
                    "AND t.transaction_date BETWEEN ? AND ?\n" +
                    "\n" +
                    "UNION\n" +
                    "\n" +
                    "SELECT \n" +
                    "    'Aktivitas Pendanaan' AS kategori,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) AS pemasukan,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS pengeluaran,\n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pemasukan' THEN t.amount ELSE 0 END) + \n" +
                    "    SUM(CASE WHEN t.transaction_type = 'Pengeluaran' THEN t.amount ELSE 0 END) AS total\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE a.account_type IN ('Kewajiban', 'Ekuitas')\n" +
                    "AND t.transaction_date BETWEEN ? AND ?;");
            p.setDate(1,   search1);
            p.setDate(2,   search2);
            p.setDate(3,   search1);
            p.setDate(4,   search2);
            p.setDate(5,   search1);
            p.setDate(6,   search2);
            r = p.executeQuery();
            List<ModelArusKas> list = new ArrayList<>();
            while (r.next()) {
                String kategori = r.getString("kategori");
                int pemasukan = r.getInt("pemasukan");
                int pengeluaran = r.getInt("pengeluaran");
                int total = r.getInt("total");

                list.add(new ModelArusKas(kategori, pemasukan, pengeluaran, total));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
