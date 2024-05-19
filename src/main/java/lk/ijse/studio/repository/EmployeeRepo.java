package lk.ijse.studio.repository;

import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepo {
    public static boolean save(Employee employee) throws SQLException {
//        In here you can now save your employee
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getTel());
        pstm.setObject(4, employee.getRole());
        pstm.setObject(5, employee.getUserId());

        return pstm.executeUpdate() > 0;

        /*int affectedRows = pstm.executeUpdate();
        if (affectedRows > 0) {
            return true;
        } else {
            return false;
        }*/
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET name = ?, tel = ?, role = ?, user_id = ? WHERE employee_id = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee.getEmployeeId());
        pstm.setObject(2, employee.getName());
        pstm.setObject(3, employee.getTel());
        pstm.setObject(4, employee.getRole());
        pstm.setObject(5, employee.getUserId());

        return pstm.executeUpdate() > 0;
    }

    public static Employee searchById(String employee_id) throws SQLException {
        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee_id);
        ResultSet resultSet = pstm.executeQuery();

        Employee employee = null;

        if (resultSet.next()) {
            String em_id = resultSet.getString(1);
            String employee_name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String role = resultSet.getString(4);
            String user_id = resultSet.getString(5);

            employee = new Employee(em_id, employee_name, tel, role, user_id);
        }
        return employee;
    }

    public static boolean delete(String employee_id) throws SQLException {
        String sql = "DELETE FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, employee_id);

        return pstm.executeUpdate() > 0;
    }

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String employee_id = resultSet.getString(1);
            String employee_name = resultSet.getString(2);
            String tel = resultSet.getString(3);
            String role = resultSet.getString(4);
            String user_id = resultSet.getString(5);

            Employee employee = new Employee(employee_id, employee_name, tel, role, user_id);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT employee_id FROM employee";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
