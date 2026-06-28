package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelAkun;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceAkun {
    public List<ModelAkun> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from accounts");
            r = p.executeQuery();
            List<ModelAkun> list = new ArrayList<>();
            while (r.next()) {
                int id = r.getInt("account_id");
                String name = r.getString("account_name");
                String type = r.getString("account_type");
                list.add(new ModelAkun(id, name, type));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }

    public void create(ModelAkun data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("insert into accounts (account_id, account_name, account_type) values (?,?,?)");
            p.setInt(1, data.getKodeAkun());
            p.setString(2, data.getNamaAkun());
            p.setString(3, data.getTipeAkun());
            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public void edit(ModelAkun data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            String sql = "update accounts set account_id=?, account_name=?, account_type=? where account_id=? limit 1";
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement(sql);
            System.out.println(data);
            p.setInt(1, data.getKodeAkun());
            p.setString(2, data.getNamaAkun());
            p.setString(3, data.getTipeAkun());
            p.setInt(4, data.getKodeAkun());
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
            p = con.prepareStatement("delete from accounts where account_id=? limit 1");
            p.setInt(1, id);
            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public List<ModelAkun> search(String search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from accounts where account_id like ? order by account_name");
            p.setString(1,   search + "%");
//            p.setString(2, "%" + search + "%");
//            p.setString(3, "%" + search + "%");
//            p.setString(4, "%" + search + "%");
            r = p.executeQuery();
            List<ModelAkun> list = new ArrayList<>();
            while (r.next()) {
                int kodeAkun = r.getInt("account_id");
                String namaAkun = r.getString("account_name");
                String tipeAkun = r.getString("account_type");
                list.add(new ModelAkun(kodeAkun,namaAkun,tipeAkun));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
