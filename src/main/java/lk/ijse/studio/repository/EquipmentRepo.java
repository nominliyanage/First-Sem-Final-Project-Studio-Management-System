package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Equipment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentRepo {
    public static boolean save(Equipment equipment) throws SQLException {
//        In here you can now save your equipment
        String sql = "INSERT INTO equipment VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, equipment.getEquipmentId());
        pstm.setObject(2, equipment.getName());
        pstm.setObject(3, equipment.getPurchaseDate());
        pstm.setObject(4, equipment.getMaintainHistory());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Equipment equipment) throws SQLException {
        String sql = "UPDATE equipment SET equipment_name = ?, purchase_date = ?, maintain_history = ? WHERE equipment_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, equipment.getEquipmentId());
        pstm.setObject(2, equipment.getName());
        pstm.setObject(3, equipment.getPurchaseDate());
        pstm.setObject(4, equipment.getMaintainHistory());

        return pstm.executeUpdate() > 0;
    }

    public static Equipment searchById(String equipment_id) throws SQLException {
        String sql = "SELECT * FROM equipment WHERE equipment_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, equipment_id);
        ResultSet resultSet = pstm.executeQuery();

        Equipment equipment = null;

        if (resultSet.next()) {
            String eq_id = resultSet.getString(1);
            String equipment_name = resultSet.getString(2);
            String purchase_date = resultSet.getString(3);
            String maintain_history = resultSet.getString(4);

            equipment = new Equipment(eq_id, equipment_name, purchase_date, maintain_history);
        }
        return equipment;
    }

    public static boolean delete(String equipment_id) throws SQLException {
        String sql = "DELETE FROM equipment WHERE equipment_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, equipment_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Equipment> getAll() throws SQLException {
        String sql = "SELECT * FROM equipment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Equipment> equipmentList = new ArrayList<>();
        while (resultSet.next()) {
            String equipment_id = resultSet.getString(1);
            String equipment_name = resultSet.getString(2);
            String purchase_date = resultSet.getString(3);
            String maintain_history = resultSet.getString(4);

            Equipment equipment = new Equipment(equipment_id, equipment_name, purchase_date, maintain_history);
            equipmentList.add(equipment);
        }
        return equipmentList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT equipment_id FROM equipment";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
