package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo {
    public static boolean save(Client client) throws SQLException {
//        In here you can now save your client
        String sql = "INSERT INTO client VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, client.getClientId());
        pstm.setObject(2, client.getName());
        pstm.setObject(3, client.getTel());
        pstm.setObject(4, client.getUserId());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Client client) throws SQLException {
        String sql = "UPDATE client SET name = ?, user_id = ?, tel = ? WHERE client_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, client.getName());
        pstm.setObject(2, client.getUserId());
        pstm.setObject(3, client.getTel());
        pstm.setObject(4, client.getClientId());

        return pstm.executeUpdate() > 0;
    }

    public static Client searchById(String client_id) throws SQLException {
        String sql = "SELECT * FROM client WHERE client_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, client_id);
        ResultSet resultSet = pstm.executeQuery();

        Client client = null;

        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String user_id = resultSet.getString(3);
            String tel = resultSet.getString(4);

            client = new Client(cus_id, name, user_id, tel);
        }
        return client;
    }

    public static boolean delete(String client_id) throws SQLException {
        String sql = "DELETE FROM client WHERE client_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, client_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Client> getAll() throws SQLException {
        String sql = "SELECT * FROM client";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Client> clientList = new ArrayList<>();
        while (resultSet.next()) {
            String client_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String user_id = resultSet.getString(4);

            Client client = new Client(client_id, name, tel, user_id);
            clientList.add(client);
        }
        return clientList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT client_id FROM client";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
