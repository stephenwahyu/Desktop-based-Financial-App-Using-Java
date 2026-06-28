package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelAnggaran;
import raven.modal.demo.model.ModelAkun;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAnggaran {
    public List<ModelAnggaran> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from budgets join accounts using (account_id)");
            r = p.executeQuery();
            List<ModelAnggaran> list = new ArrayList<>();
            while (r.next()) {
                int id = r.getInt("budget_id");
                int acc_id = r.getInt("account_id");
                String acc_name = r.getString("account_name");
                String acc_type = r.getString("account_type");
                int sum = r.getInt("budget_amount");
                String period = r.getString("budget_period");
                list.add(new ModelAnggaran(id,new ModelAkun(acc_id,acc_name,acc_type),sum,period));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }

    public void create(ModelAnggaran data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("insert into budgets (budget_id, account_id, budget_amount, budget_period) values (?,?,?,?)");
            p.setInt(1, data.getKodeAnggaran());
            p.setInt(2, data.getNamaAkun().getKodeAkun());
            p.setInt(3, data.getJumlahAnggaran());
            p.setString(4, data.getPeriodeAnggaran());

            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public void edit(ModelAnggaran data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            String sql = "update budgets set budget_id=?, account_id=?, budget_amount=?, budget_period=? where budget_id=? limit 1";
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement(sql);
            System.out.println(data);
            p.setInt(1, data.getKodeAnggaran());
            p.setInt(2, data.getNamaAkun().getKodeAkun());
            p.setInt(3, data.getJumlahAnggaran());
            p.setString(4, data.getPeriodeAnggaran());
            p.setInt(5, data.getKodeAnggaran());

            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public void delete(int id) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("delete from budgets where budget_id=? limit 1");
            p.setInt(1, id);
            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public List<ModelAnggaran> search(String search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from budgets join accounts using (account_id) where account_name like ? order by budget_id");
            p.setString(1,   search + "%");
//            p.setString(2, "%" + search + "%");
//            p.setString(3, "%" + search + "%");
//            p.setString(4, "%" + search + "%");
            r = p.executeQuery();
            List<ModelAnggaran> list = new ArrayList<>();
            while (r.next()) {
                int kodeAnggaran = r.getInt("budget_id");
                int acc_id = r.getInt("account_id");
                String acc_name = r.getString("account_name");
                String acc_type = r.getString("account_type");
                int jumlahAnggaran = r.getInt("budget_amount");
                String periodeAnggaran = r.getString("budget_period");

                list.add(new ModelAnggaran(kodeAnggaran,new ModelAkun(acc_id,acc_name,acc_type), jumlahAnggaran, periodeAnggaran));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }

    public ServiceAkun getServicePositions() {
        if (servicePositions == null) {
            servicePositions = new ServiceAkun();
        }
        return servicePositions;
    }
    private ServiceAkun servicePositions;
}
