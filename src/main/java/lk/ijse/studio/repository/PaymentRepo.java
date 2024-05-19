package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepo {
    public static boolean save(Payment payment) throws SQLException {
//        In here you can now save your payment
        String sql = "INSERT INTO payment VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2, payment.getClientId());
        pstm.setObject(3, payment.getBookingId());
        pstm.setObject(4, payment.getAmount());
        pstm.setObject(5, payment.getDate());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET client_id = ?, booking_id = ?, amount = ?, date = ? WHERE payment_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2, payment.getClientId());
        pstm.setObject(3, payment.getBookingId());
        pstm.setObject(4, payment.getAmount());
        pstm.setObject(5, payment.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static Payment searchById(String payment_id) throws SQLException {
        String sql = "SELECT * FROM payment WHERE payment_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, payment_id);
        ResultSet resultSet = pstm.executeQuery();

        Payment payment = null;

        if (resultSet.next()) {
            String pay_id = resultSet.getString(1);
            String client_id = resultSet.getString(2);
            String booking_id = resultSet.getString(3);
            String amount = resultSet.getString(4);
            String date = resultSet.getString(5);

            payment = new Payment(pay_id, client_id, booking_id, amount, date);
        }
        return payment;
    }

    public static boolean delete(String payment_id) throws SQLException {
        String sql = "DELETE FROM payment WHERE payment_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, payment_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String payment_id = resultSet.getString(1);
            String client_id = resultSet.getString(2);
            String booking_id = resultSet.getString(3);
            String amount = resultSet.getString(4);
            String date = resultSet.getString(5);

            Payment payment = new Payment(payment_id, client_id, booking_id, amount, date);
            paymentList.add(payment);
        }
        return paymentList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT payment_id FROM payment";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
