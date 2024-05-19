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
import lk.ijse.studio.model.Package;
import lk.ijse.studio.model.tm.PackageTm;
import lk.ijse.studio.repository.PackageRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PackageFormController {
    @FXML
    private TableColumn<?, ?> colPackageId;

    @FXML
    private TableColumn<?, ?> colPackageName;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colIncludes;

    @FXML
    private TableView<PackageTm> tblPackage;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnSavePackage;

    @FXML
    private TextField txtPackageId;

    @FXML
    private TextField txtPackageName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtIncludes;

    private List<Package> packageList = new ArrayList<>();

    public void initialize() {
        this.packageList = getAllPackage();
        setCellValueFactory();
        loadPackageTable();
    }

    private void setCellValueFactory() {
        colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
        colPackageName.setCellValueFactory(new PropertyValueFactory<>("packageName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colIncludes.setCellValueFactory(new PropertyValueFactory<>("includes"));
    }

    private void loadPackageTable() {
        ObservableList<PackageTm> tmList = FXCollections.observableArrayList();

        for (Package pack : packageList) {
            PackageTm packageTm = new PackageTm(
                    pack.getPackageId(),
                    pack.getPackageName(),
                    pack.getPrice(),
                    pack.getIncludes()
            );

            tmList.add(packageTm);
        }
        tblPackage.setItems(tmList);
        PackageTm selectedItem = tblPackage.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Package> getAllPackage() {
        List<Package> packageList = null;
        try {
            packageList = PackageRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return packageList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String package_id = txtPackageId.getText();
        String package_name = txtPackageName.getText();
        String price = txtPrice.getText();
        String includes = txtIncludes.getText();

        Package pack = new Package(package_id, package_name, price, includes);

        try {
            boolean isSaved = PackageRepo.save(pack);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "package saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our package model

    }

    private void clearFields() {
        txtPackageId.setText("");
        txtPackageName.setText("");
        txtPrice.setText("");
        txtIncludes.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String package_id = txtPackageId.getText();
        String package_name = txtPackageName.getText();
        String price = txtPrice.getText();
        String includes = txtIncludes.getText();

        Package pack = new Package(package_id, package_name, price, includes);

        try {
            boolean isUpdated = PackageRepo.update(pack);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "package updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String package_id = txtPackageId.getText();

        try {
            Package pack = PackageRepo.searchById(package_id);

            if (pack != null) {
                txtPackageId.setText(pack.getPackageId());
                txtPackageName.setText(pack.getPackageName());
                txtPrice.setText(pack.getPrice());
                txtIncludes.setText(pack.getIncludes());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String package_id = txtPackageId.getText();

        try {
            boolean isDeleted = PackageRepo.delete(package_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "package deleted!").show();
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
        Pattern packageidPattern = Pattern.compile("^(P)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^([A-z ]{3,30})$");
        Pattern pricePattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        Pattern includesPattern = Pattern.compile("^^([A-z ]{3,30})$");

        btnSavePackage.setDisable(true);
        if (!packageidPattern.matcher(txtPackageId.getText()).matches()){
            addError(txtPackageId);
        }else{
            removeError(txtPackageId);
            if(!namePattern.matcher(txtPackageName.getText()).matches()){
                addError(txtPackageName);
            }else{
                removeError(txtPackageName);
                if(!pricePattern.matcher(txtPrice.getText()).matches()){
                    addError(txtPrice);
                }else{
                    removeError(txtPrice);
                    if (!includesPattern.matcher(txtIncludes.getText()).matches()){
                        addError(txtIncludes);
                    }else{
                        removeError(txtIncludes);
                    }
                }
            }
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSavePackage.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSavePackage.setDisable(true);
    }
}