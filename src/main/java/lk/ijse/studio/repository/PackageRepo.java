package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageRepo {
    public static boolean save(Package pack) throws SQLException {
//        In here you can now save your package
        String sql = "INSERT INTO package VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, pack.getPackageId());
        pstm.setObject(2, pack.getPackageName());
        pstm.setObject(3, pack.getPrice());
        pstm.setObject(4, pack.getIncludes());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Package pack) throws SQLException {
        String sql = "UPDATE package SET package_name = ?, price = ?, includes = ? WHERE package_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, pack.getPackageId());
        pstm.setObject(2, pack.getPackageName());
        pstm.setObject(3, pack.getPrice());
        pstm.setObject(4, pack.getIncludes());

        return pstm.executeUpdate() > 0;
    }

    public static Package searchById(String package_id) throws SQLException {
        String sql = "SELECT * FROM package WHERE package_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, package_id);
        ResultSet resultSet = pstm.executeQuery();

        Package pack = null;

        if (resultSet.next()) {
            String pack_id = resultSet.getString(1);
            String package_name = resultSet.getString(2);
            String price = resultSet.getString(3);
            String includes = resultSet.getString(4);

            pack = new Package(pack_id, package_name, price, includes);
        }
        return pack;
    }

    public static boolean delete(String package_id) throws SQLException {
        String sql = "DELETE FROM package WHERE package_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, package_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Package> getAll() throws SQLException {
        String sql = "SELECT * FROM package";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Package> packageList = new ArrayList<>();
        while (resultSet.next()) {
            String package_id = resultSet.getString(1);
            String package_name = resultSet.getString(2);
            String price = resultSet.getString(3);
            String includes = resultSet.getString(4);

            Package pack = new Package(package_id, package_name, price, includes);
            packageList.add(pack);
        }
        return packageList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT package_id FROM package";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
