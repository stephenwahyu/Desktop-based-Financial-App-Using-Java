package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelLaba;
import raven.modal.demo.model.ModelLabaTotal;
import raven.modal.demo.model.ModelNeraca;
import raven.modal.demo.model.ModelNeracaTotal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceLaba {
    public List<ModelLabaTotal> getTotal(Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("WITH Pendapatan AS (\n" +
                    "    SELECT \n" +
                    "        SUM(amount) AS total_pendapatan\n" +
                    "    FROM Transactions t\n" +
                    "    JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "    WHERE account_type = 'Pendapatan' AND transaction_date <= ?\n" +
                    "),\n" +
                    "Beban AS (\n" +
                    "    SELECT \n" +
                    "        SUM(amount) AS total_beban\n" +
                    "    FROM Transactions t\n" +
                    "    JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "    WHERE account_type = 'Beban' AND transaction_date <= ?\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    COALESCE(p.total_pendapatan, 0) AS 'total_pendapatan', \n" +
                    "    COALESCE(b.total_beban, 0) AS 'total_beban',\n" +
                    "    (COALESCE(p.total_pendapatan, 0) + COALESCE(b.total_beban, 0)) AS 'labaRugi_bersih'\n" +
                    "FROM Pendapatan p, Beban b;");
            p.setDate(1,   search);
            p.setDate(2,   search);
            r = p.executeQuery();
            List<ModelLabaTotal> list = new ArrayList<>();
            while (r.next()) {
                int totalPendapatan = r.getInt("total_pendapatan");
                int totalBeban = r.getInt("total_beban");
                int labaRugiBersih = r.getInt("labaRugi_bersih");
                list.add(new ModelLabaTotal(totalPendapatan, totalBeban, labaRugiBersih));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelLabaTotal> getTotalInterval(Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("WITH Pendapatan AS (\n" +
                    "    SELECT \n" +
                    "        SUM(amount) AS total_pendapatan\n" +
                    "    FROM Transactions t\n" +
                    "    JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "    WHERE account_type = 'Pendapatan' AND transaction_date BETWEEN ? AND ?\n" +
                    "),\n" +
                    "Beban AS (\n" +
                    "    SELECT \n" +
                    "        SUM(amount) AS total_beban\n" +
                    "    FROM Transactions t\n" +
                    "    JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "    WHERE account_type = 'Beban' AND transaction_date BETWEEN ? AND ?\n" +
                    ")\n" +
                    "SELECT \n" +
                    "    COALESCE(p.total_pendapatan, 0) AS 'total_pendapatan', \n" +
                    "    COALESCE(b.total_beban, 0) AS 'total_beban',\n" +
                    "    (COALESCE(p.total_pendapatan, 0) + COALESCE(b.total_beban, 0)) AS 'labaRugi_bersih'\n" +
                    "FROM Pendapatan p, Beban b;");
            p.setDate(1,   search1);
            p.setDate(2,   search2);
            p.setDate(3,   search1);
            p.setDate(4,   search2);
            r = p.executeQuery();
            List<ModelLabaTotal> list = new ArrayList<>();
            while (r.next()) {
                int totalPendapatan = r.getInt("total_pendapatan");
                int totalBeban = r.getInt("total_beban");
                int labaRugiBersih = r.getInt("labaRugi_bersih");
                list.add(new ModelLabaTotal(totalPendapatan, totalBeban, labaRugiBersih));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelLaba> getAll(Date search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    account_type AS 'jenis_akun',\n" +
                    "    account_name AS 'nama_akun',\n" +
                    "    SUM(amount) AS 'total'\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE account_type IN ('Pendapatan', 'Beban') AND transaction_date <= ? \n" +
                    "GROUP BY account_type, account_name\n" +
                    "ORDER BY account_type DESC, account_name;\n");
            p.setDate(1,   search);
            r = p.executeQuery();
            List<ModelLaba> list = new ArrayList<>();
            while (r.next()) {
                String tipeAkun = r.getString("jenis_akun");
                String namaAkun = r.getString("nama_akun");
                int totalSaldo = r.getInt("total");

                list.add(new ModelLaba(tipeAkun, namaAkun, totalSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
    public List<ModelLaba> getAllInterval(Date search1, Date search2) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("SELECT \n" +
                    "    account_type AS 'jenis_akun',\n" +
                    "    account_name AS 'nama_akun',\n" +
                    "    SUM(amount) AS 'total'\n" +
                    "FROM Transactions t\n" +
                    "JOIN Accounts a ON t.account_id = a.account_id\n" +
                    "WHERE account_type IN ('Pendapatan', 'Beban') AND transaction_date BETWEEN ? AND ? \n" +
                    "GROUP BY account_type, account_name\n" +
                    "ORDER BY account_type DESC, account_name;\n");
            p.setDate(1,   search1);
            p.setDate(2,   search2);
            r = p.executeQuery();
            List<ModelLaba> list = new ArrayList<>();
            while (r.next()) {
                String tipeAkun = r.getString("jenis_akun");
                String namaAkun = r.getString("nama_akun");
                int totalSaldo = r.getInt("total");

                list.add(new ModelLaba(tipeAkun, namaAkun, totalSaldo));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
