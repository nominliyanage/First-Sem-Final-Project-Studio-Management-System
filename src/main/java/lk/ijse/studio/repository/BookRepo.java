package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Book;
import lk.ijse.studio.model.Booking;
import lk.ijse.studio.model.Package;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRepo {
    public static String currentId() throws SQLException {
        String sql = "SELECT booking_id FROM booking ORDER BY booking_id desc LIMIT 1";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static boolean save(Book book) throws SQLException {
        String sql = "INSERT INTO booking VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);
        pstm.setString(1, book.getBookingId());
        pstm.setString(2, book.getUserId());
        pstm.setString(3, book.getClientId());
        pstm.setString(4, book.getEmployeeId());
        pstm.setString(5, book.getPackageId());
        pstm.setObject(6, book.getDate());
        pstm.setObject(7, book.getTime());
        pstm.setObject(8, book.getLocation());
        pstm.setDouble(9, book.getTotal());

        return pstm.executeUpdate() > 0;
    }

    public static boolean book(Booking booking) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Start transaction

        try {
            boolean isBookSaved = BookRepo.save(booking.getBook());
            if (isBookSaved) {
                boolean isBookingDetailsSaved = BookingDetailsRepo.save(booking.getBdList());
                if (isBookingDetailsSaved) {
                    connection.commit(); // Commit if all successful
                    return true;
                }
            }
        } catch (Exception e) { // Catch any exceptions during operations
            connection.rollback(); // Rollback if any errors occur
            throw new SQLException("Failed to save booking: " + e.getMessage());
        } finally {
            connection.setAutoCommit(true); // Reset auto-commit mode
        }

        return false; // If execution reaches here, booking failed
    }


}
