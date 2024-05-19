package lk.ijse.studio.model.tm;

public class SalaryTm {
    private String salary_id;
    private String employee_id;
    private String amount;
    private String date;

    public SalaryTm() {
    }

    public SalaryTm(String salary_id, String employee_id, String amount, String date) {
        this.salary_id = salary_id;
        this.employee_id = employee_id;
        this.amount = amount;
        this.date = date;
    }

    public String getSalaryId() {
        return salary_id;
    }

    public void setSalaryId(String salary_id) {
        this.salary_id = salary_id;
    }

    public String getEmployeeId() {
        return employee_id;
    }

    public void setEmployeeId(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalaryTm{" +
                "salary_id='" + salary_id + '\'' +
                ", employee_id='" + employee_id + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
