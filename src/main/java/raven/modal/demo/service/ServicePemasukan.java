package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelTransaksi;
import raven.modal.demo.model.ModelAkun;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ServicePemasukan {
    public List<ModelTransaksi> getAll() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from transactions join accounts using (account_id) where transaction_type='Pemasukan'");
            r = p.executeQuery();
            List<ModelTransaksi> list = new ArrayList<>();
            while (r.next()) {
                int id = r.getInt("transaction_id");
                int acc_id = r.getInt("account_id");
                String acc_name = r.getString("account_name");
                String acc_type = r.getString("account_type");
                String type = r.getString("transaction_type");
                Date date = r.getDate("transaction_date");
                int sum = r.getInt("amount");
                String desc = r.getString("description");
                list.add(new ModelTransaksi(id,new ModelAkun(acc_id,acc_name,acc_type),type,date,sum,desc));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }

    public void create(ModelTransaksi data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("insert into transactions (transaction_id, account_id, transaction_type, transaction_date, amount, description) values (?,?,?,?,?,?)");
            p.setInt(1, data.getKodeTransaksi());
            p.setInt(2, data.getNamaAkun().getKodeAkun());
            p.setString(3, data.getTipeTransaksi());
            p.setDate(4, data.getTanggalTransaksi());
            p.setInt(5, data.getJumlah());
            p.setString(6, data.getDeskripsi());

            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public void edit(ModelTransaksi data) throws SQLException, IOException {
        Connection con = null;
        PreparedStatement p = null;
        try {
            String sql = "update transactions set transaction_id=?, account_id=?, transaction_type=?, transaction_date=?, amount=?, description=? where transaction_id=? limit 1";
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement(sql);
            System.out.println(data);
            p.setInt(1, data.getKodeTransaksi());
            p.setInt(2, data.getNamaAkun().getKodeAkun());
            p.setString(3, data.getTipeTransaksi());
            p.setDate(4, data.getTanggalTransaksi());
            p.setInt(5, data.getJumlah());
            p.setString(6, data.getDeskripsi());
            p.setInt(7, data.getKodeTransaksi());

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
            p = con.prepareStatement("delete from transactions where transaction_id=? limit 1");
            p.setInt(1, id);
            p.execute();
        } finally {
            DatabaseConnection.getInstance().close(p, con);
        }
    }

    public List<ModelTransaksi> search(String search) throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from transactions join accounts using (account_id) where transaction_type = 'Pemasukan' && account_name like ? order by transaction_id");
            p.setString(1,   search + "%");
//            p.setString(2, "%" + search + "%");
//            p.setString(3, "%" + search + "%");
//            p.setString(4, "%" + search + "%");
            r = p.executeQuery();
            List<ModelTransaksi> list = new ArrayList<>();
            while (r.next()) {
                int kodeTransaksi = r.getInt("transaction_id");
                int acc_id = r.getInt("account_id");
                String acc_name = r.getString("account_name");
                String acc_type = r.getString("account_type");
                String tipeTransaksi = r.getString("transaction_type");
                Date tanggalTransaksi = r.getDate("transaction_date");
                int jumlah = r.getInt("amount");
                String deskripsi = r.getString("description");

                list.add(new ModelTransaksi(kodeTransaksi,new ModelAkun(acc_id,acc_name,acc_type), tipeTransaksi,tanggalTransaksi, jumlah, deskripsi));
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
