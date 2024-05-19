package lk.ijse.studio.model;

public class Employee { // this model class represent real world client entity
    private String employee_id;
    private String employee_name;
    private String tel;
    private String role;
    private String user_id;

    public Employee() {
    }

    public Employee(String employee_id, String employee_name, String tel, String role, String user_id) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.tel = tel;
        this.role = role;
        this.user_id = user_id;
    }

    public String getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return employee_name;
    }

    public void setName(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id='" + employee_id + '\'' +
                ", employee_name'" + employee_name + '\'' +
                ", tel='" + tel + '\'' +
                ", role='" + role + '\'' +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
