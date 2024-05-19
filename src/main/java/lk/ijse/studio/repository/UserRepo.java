package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {
    public static boolean save(User user) throws SQLException {
        String sql = "INSERT INTO user VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, user.getUserId());
        pstm.setString(2, user.getName());
        pstm.setString(3, user.getPassword());


        return pstm.executeUpdate() > 0;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT user_id FROM user";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
