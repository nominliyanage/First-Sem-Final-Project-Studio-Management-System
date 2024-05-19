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
import lk.ijse.studio.model.Equipment;
import lk.ijse.studio.model.tm.EquipmentTm;
import lk.ijse.studio.repository.EquipmentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EquipmentFormController {
    @FXML
    private TableColumn<?, ?> colEquipmentId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPurchaseDate;

    @FXML
    private TableColumn<?, ?> colMaintainHistory;

    @FXML
    private TableView<EquipmentTm> tblEquipment;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnSaveEquipment;

    @FXML
    private TextField txtEquipmentId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPurchaseDate;

    @FXML
    private TextField txtMaintainHistory;

    private List<Equipment> equipmentList = new ArrayList<>();

    public void initialize() {
        this.equipmentList = getAllClient();
        setCellValueFactory();
        loadEquipmentTable();
    }

    private void setCellValueFactory() {
        colEquipmentId.setCellValueFactory(new PropertyValueFactory<>("EquipmentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
        colMaintainHistory.setCellValueFactory(new PropertyValueFactory<>("maintainHistory"));
    }

    private void loadEquipmentTable() {
        ObservableList<EquipmentTm> tmList = FXCollections.observableArrayList();

        for (Equipment equipment : equipmentList) {
            EquipmentTm equipmentTm = new EquipmentTm(
                    equipment.getEquipmentId(),
                    equipment.getName(),
                    equipment.getPurchaseDate(),
                    equipment.getMaintainHistory()
            );

            tmList.add(equipmentTm);
        }
        tblEquipment.setItems(tmList);
        EquipmentTm selectedItem = tblEquipment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Equipment> getAllClient() {
        List<Equipment> equipmentList = null;
        try {
            equipmentList = EquipmentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return equipmentList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String equipment_id = txtEquipmentId.getText();
        String name = txtName.getText();
        String purchase_date = txtPurchaseDate.getText();
        String maintain_history = txtMaintainHistory.getText();

        Equipment equipment = new Equipment(equipment_id, name, purchase_date, maintain_history);

        try {
            boolean isSaved = EquipmentRepo.save(equipment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "equipment saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our equipment model

    }

    private void clearFields() {
        txtEquipmentId.setText("");
        txtName.setText("");
        txtPurchaseDate.setText("");
        txtMaintainHistory.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String equipment_id = txtEquipmentId.getText();
        String name = txtName.getText();
        String purchase_date = txtPurchaseDate.getText();
        String maintain_history = txtMaintainHistory.getText();

        Equipment equipment = new Equipment(equipment_id, name, purchase_date, maintain_history);

        try {
            boolean isUpdated = EquipmentRepo.update(equipment);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "equipment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String equipment_id = txtEquipmentId.getText();

        try {
            Equipment equipment = EquipmentRepo.searchById(equipment_id);

            if (equipment != null) {
                txtEquipmentId.setText(equipment.getEquipmentId());
                txtName.setText(equipment.getName());
                txtPurchaseDate.setText(equipment.getPurchaseDate());
                txtMaintainHistory.setText(equipment.getMaintainHistory());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String equipment_id = txtEquipmentId.getText();

        try {
            boolean isDeleted = EquipmentRepo.delete(equipment_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "equipment deleted!").show();
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
        Pattern equipmentidPattern = Pattern.compile("^(Eq)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^([A-z ]{3,15})$");
        Pattern purchasedatePattern = Pattern.compile("^(19|20)\\d{2}-(0[1-9]|1[1,2])-(0[1-9]|[12][0-9]|3[01])$");
        Pattern maintainhistoryPattern = Pattern.compile("^(19|20)\\d{2}-(0[1-9]|1[1,2])-(0[1-9]|[12][0-9]|3[01])|[ A-z]{3,15}$");

        btnSaveEquipment.setDisable(true);
        if (!equipmentidPattern.matcher(txtEquipmentId.getText()).matches()){
            addError(txtEquipmentId);
        }else{
            removeError(txtEquipmentId);
            if(!namePattern.matcher(txtName.getText()).matches()){
                addError(txtName);
            }else{
                removeError(txtName);
                if(!purchasedatePattern.matcher(txtPurchaseDate.getText()).matches()){
                    addError(txtPurchaseDate);
                }else{
                    removeError(txtPurchaseDate);
                    if (!maintainhistoryPattern.matcher(txtMaintainHistory.getText()).matches()){
                        addError(txtMaintainHistory);
                    }else{
                        removeError(txtMaintainHistory);
                    }
                }
            }
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSaveEquipment.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSaveEquipment.setDisable(true);
    }
}