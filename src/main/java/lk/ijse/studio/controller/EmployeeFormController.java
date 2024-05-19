package lk.ijse.studio.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.studio.model.Employee;
import lk.ijse.studio.model.tm.EmployeeTm;
import lk.ijse.studio.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {
    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colRole;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private JFXButton btnSaveEmployee;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUserId;

    private List<Employee> employeeList = new ArrayList<>();

    public void initialize() {
        this.employeeList = getAllClient();
        setCellValueFactory();
        loadEmployeeTable();
        //btnSaveEmployee.setDisable(true);
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    private void loadEmployeeTable() {
        ObservableList<EmployeeTm> tmList = FXCollections.observableArrayList();

        for (Employee employee : employeeList) {
            EmployeeTm employeeTm = new EmployeeTm(
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getTel(),
                    employee.getRole(),
                    employee.getUserId()
            );

            tmList.add(employeeTm);
        }
        tblEmployee.setItems(tmList);
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Employee> getAllClient() {
        List<Employee> employeeList = null;
        try {
            employeeList = EmployeeRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String employee_id = txtEmployeeId.getText();
        String name = txtName.getText();
        String tel = txtTel.getText();
        String role = txtRole.getText();
        String user_id = txtUserId.getText();

        Employee employee = new Employee(employee_id, name, tel, role, user_id);

        try {
            boolean isSaved = EmployeeRepo.save(employee);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our employee model

    }

    private void clearFields() {
        txtEmployeeId.setText("");
        txtName.setText("");
        txtTel.setText("");
        txtRole.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String employee_id = txtEmployeeId.getText();
        String name = txtName.getText();
        String tel = txtTel.getText();
        String role = txtRole.getText();
        String user_id = txtUserId.getText();

        Employee employee = new Employee(employee_id, name, tel, role, user_id);

        try {
            boolean isUpdated = EmployeeRepo.update(employee);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String employee_id = txtEmployeeId.getText();

        try {
            Employee employee = EmployeeRepo.searchById(employee_id);

            if (employee != null) {
                txtEmployeeId.setText(employee.getEmployeeId());
                txtName.setText(employee.getName());
                txtTel.setText(employee.getTel());
                txtRole.setText(employee.getRole());
                txtUserId.setText(employee.getUserId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String employee_id = txtEmployeeId.getText();

        try {
            boolean isDeleted = EmployeeRepo.delete(employee_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }

    public void TextFieldKeyRelased(KeyEvent keyEvent) {
        Pattern employeeidPattern = Pattern.compile("^(E)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^([A-z ]{3,15}$)");
        Pattern telPattern = Pattern.compile("^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$");
        Pattern rolePattern = Pattern.compile("^([A-z ]{3,25}$)");
        Pattern useridPattern = Pattern.compile("^(U)[0-9]{3}$");

        btnSaveEmployee.setDisable(true);
        if (!employeeidPattern.matcher(txtEmployeeId.getText()).matches()){
            addError(txtEmployeeId);
        }else{
            removeError(txtEmployeeId);
            if(!namePattern.matcher(txtName.getText()).matches()){
                addError(txtName);
            }else{
                removeError(txtName);
                if(!telPattern.matcher(txtTel.getText()).matches()){
                    addError(txtTel);
                }else{
                    removeError(txtTel);
                    if (!rolePattern.matcher(txtRole.getText()).matches()){
                        addError(txtRole);
                    }else{
                        removeError(txtRole);
                        if (!useridPattern.matcher(txtUserId.getText()).matches()){
                            addError(txtUserId);
                        }else{
                            removeError(txtUserId);
                        }
                    }
                }
            }
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSaveEmployee.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSaveEmployee.setDisable(true);
    }
}