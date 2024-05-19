package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Salary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryRepo {
    public static boolean save(Salary salary) throws SQLException {
//        In here you can now save your salary
        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, salary.getSalaryId());
        pstm.setObject(2, salary.getEmployeeId());
        pstm.setObject(3, salary.getAmount());
        pstm.setObject(4, salary.getDate());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Salary salary) throws SQLException {
        String sql = "UPDATE salary SET employee_id = ?, amount = ?, date = ? WHERE salary_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, salary.getSalaryId());
        pstm.setObject(2, salary.getEmployeeId());
        pstm.setObject(3, salary.getAmount());
        pstm.setObject(4, salary.getDate());

        return pstm.executeUpdate() > 0;
    }

    public static Salary searchById(String salary_id) throws SQLException {
        String sql = "SELECT * FROM salary WHERE salary_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, salary_id);
        ResultSet resultSet = pstm.executeQuery();

        Salary salary = null;

        if (resultSet.next()) {
            String salar_id = resultSet.getString(1);
            String employee_id = resultSet.getString(2);
            String amount = resultSet.getString(3);
            String date = resultSet.getString(4);

            salary = new Salary(salar_id, employee_id, amount, date);
        }
        return salary;
    }

    public static boolean delete(String salary_id) throws SQLException {
        String sql = "DELETE FROM salary WHERE salary_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, salary_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Salary> getAll() throws SQLException {
        String sql = "SELECT * FROM salary";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Salary> salaryList = new ArrayList<>();
        while (resultSet.next()) {
            String salary_id = resultSet.getString(1);
            String employee_id = resultSet.getString(2);
            String amount = resultSet.getString(3);
            String date = resultSet.getString(4);

            Salary salary = new Salary(salary_id, employee_id, amount, date);
            salaryList.add(salary);
        }
        return salaryList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT salary_id FROM salary";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
