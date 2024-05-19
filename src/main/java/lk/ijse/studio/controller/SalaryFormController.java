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
import lk.ijse.studio.model.Salary;
import lk.ijse.studio.model.tm.SalaryTm;
import lk.ijse.studio.repository.SalaryRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SalaryFormController {
    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnSaveSalary;

    @FXML
    private TextField txtSalaryId;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    private List<Salary> salaryList = new ArrayList<>();

    public void initialize() {
        this.salaryList = getAllSalary();
        setCellValueFactory();
        loadSalaryTable();
    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadSalaryTable() {
        ObservableList<SalaryTm> tmList = FXCollections.observableArrayList();

        for (Salary salary : salaryList) {
            SalaryTm salaryTm = new SalaryTm(
                    salary.getSalaryId(),
                    salary.getEmployeeId(),
                    salary.getAmount(),
                    salary.getDate()
            );

            tmList.add(salaryTm);
        }
        tblSalary.setItems(tmList);
        SalaryTm selectedItem = tblSalary.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Salary> getAllSalary() {
        List<Salary> salaryList = null;
        try {
            salaryList = SalaryRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salaryList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String salary_id = txtSalaryId.getText();
        String employee_id = txtEmployeeId.getText();
        String amount = txtAmount.getText();
        String date = txtDate.getText();

        Salary salary = new Salary(salary_id, employee_id, amount, date);

        try {
            boolean isSaved = SalaryRepo.save(salary);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our salary model

    }

    private void clearFields() {
        txtSalaryId.setText("");
        txtEmployeeId.setText("");
        txtAmount.setText("");
        txtDate.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String salary_id = txtSalaryId.getText();
        String employee_id = txtEmployeeId.getText();
        String amount = txtAmount.getText();
        String date = txtDate.getText();

        Salary salary = new Salary(salary_id, employee_id, amount, date);

        try {
            boolean isUpdated = SalaryRepo.update(salary);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String salary_id = txtSalaryId.getText();

        try {
            Salary salary = SalaryRepo.searchById(salary_id);

            if (salary != null) {
                txtSalaryId.setText(salary.getSalaryId());
                txtEmployeeId.setText(salary.getEmployeeId());
                txtAmount.setText(salary.getAmount());
                txtDate.setText(salary.getDate());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String salary_id = txtSalaryId.getText();

        try {
            boolean isDeleted = SalaryRepo.delete(salary_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary deleted!").show();
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
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }

    public void TextFieldKeyRelased(KeyEvent keyEvent) {
        Pattern salaryidPattern = Pattern.compile("^(S)[0-9]{3}$");
        Pattern employeeidPattern = Pattern.compile("^(E)[0-9]{3}$");
        Pattern amountPattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        Pattern datePattern = Pattern.compile("^(19|20)\\d{2}-(0[1-9]|1[1,2])-(0[1-9]|[12][0-9]|3[01])$");

        btnSaveSalary.setDisable(true);
        if (!salaryidPattern.matcher(txtSalaryId.getText()).matches()){
            addError(txtSalaryId);
        }else{
            removeError(txtSalaryId);
            if(!employeeidPattern.matcher(txtEmployeeId.getText()).matches()){
                addError(txtEmployeeId);
            }else{
                removeError(txtEmployeeId);
                if(!amountPattern.matcher(txtAmount.getText()).matches()){
                    addError(txtAmount);
                }else{
                    removeError(txtAmount);
                    if (!datePattern.matcher(txtDate.getText()).matches()){
                        addError(txtDate);
                    }else{
                        removeError(txtDate);
                    }
                }
            }
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSaveSalary.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSaveSalary.setDisable(true);
    }
}