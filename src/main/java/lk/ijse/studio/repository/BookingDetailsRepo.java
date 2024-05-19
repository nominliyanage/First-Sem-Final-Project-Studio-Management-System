package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.BookingDetails;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BookingDetailsRepo {
    public static boolean save(List<BookingDetails> bdList) throws SQLException {
        for (BookingDetails bd : bdList) {
            if(!save(bd)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(BookingDetails bd) throws SQLException {
        String sql = "INSERT INTO booking_details VALUES(?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setObject(1, bd.getBookingId());
        pstm.setObject(2, bd.getPackageId());
        pstm.setObject(3, bd.getDate());

        return pstm.executeUpdate() > 0;
    }
}