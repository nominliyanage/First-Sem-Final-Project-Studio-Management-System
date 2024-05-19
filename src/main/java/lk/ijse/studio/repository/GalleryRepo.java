package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Gallery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GalleryRepo {
    public static boolean save(Gallery gallery) throws SQLException {
//        In here you can now save your gallery
        String sql = "INSERT INTO gallery VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, gallery.getGalleryId());
        pstm.setObject(2, gallery.getName());
        pstm.setObject(3, gallery.getDate());
        pstm.setObject(4, gallery.getDescription());
        pstm.setObject(5, gallery.getClientId());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Gallery gallery) throws SQLException {
        String sql = "UPDATE gallery SET name = ?, date = ?, description = ?, client_id = ? WHERE gallery_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, gallery.getGalleryId());
        pstm.setObject(2, gallery.getName());
        pstm.setObject(3, gallery.getDate());
        pstm.setObject(4, gallery.getDescription());
        pstm.setObject(5, gallery.getClientId());

        return pstm.executeUpdate() > 0;
    }

    public static Gallery searchById(String gallery_id) throws SQLException {
        String sql = "SELECT * FROM gallery WHERE gallery_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, gallery_id);
        ResultSet resultSet = pstm.executeQuery();

        Gallery gallery = null;

        if (resultSet.next()) {
            String gal_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String description = resultSet.getString(4);
            String client_id = resultSet.getString(5);

            gallery = new Gallery(gal_id, name, date, description, client_id);
        }
        return gallery;
    }

    public static boolean delete(String gallery_id) throws SQLException {
        String sql = "DELETE FROM gallery WHERE gallery_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, gallery_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Gallery> getAll() throws SQLException {
        String sql = "SELECT * FROM gallery";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Gallery> galleryList = new ArrayList<>();
        while (resultSet.next()) {
            String gallery_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String description = resultSet.getString(4);
            String client_id = resultSet.getString(5);

            Gallery gallery = new Gallery(gallery_id, name, date, description, client_id);
            galleryList.add(gallery);
        }
        return galleryList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT gallery_id FROM gallery";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
