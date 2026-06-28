package raven.modal.demo.service;

import raven.modal.demo.connection.DatabaseConnection;
import raven.modal.demo.model.ModelUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser {
    public static List<ModelUser> Auth() throws SQLException {
        Connection con = null;
        PreparedStatement p = null;
        ResultSet r = null;
        try {
            con = DatabaseConnection.getInstance().createConnection();
            p = con.prepareStatement("select * from users");
            r = p.executeQuery();
            List<ModelUser> list = new ArrayList<>();
            while (r.next()) {
                String name = r.getString("username");
                String pass = r.getString("password");
                String role = r.getString("role");
                list.add(new ModelUser(name, pass, role));
            }
            return list;
        } finally {
            DatabaseConnection.getInstance().close(r, p, con);
        }
    }
}
