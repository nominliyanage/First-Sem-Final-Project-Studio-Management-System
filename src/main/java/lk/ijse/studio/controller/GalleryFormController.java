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
import lk.ijse.studio.model.Gallery;
import lk.ijse.studio.model.tm.GalleryTm;
import lk.ijse.studio.repository.GalleryRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GalleryFormController {
    @FXML
    private TableColumn<?, ?> colGalleryId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colClientId;

    @FXML
    private TableView<GalleryTm> tblGallery;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnSaveGallery;

    @FXML
    private TextField txtGalleryId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtClientId;

    private List<Gallery> galleryList = new ArrayList<>();

    public void initialize() {
        this.galleryList = getAllGallery();
        setCellValueFactory();
        loadGalleryTable();
    }

    private void setCellValueFactory() {
        colGalleryId.setCellValueFactory(new PropertyValueFactory<>("galleryId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
    }

    private void loadGalleryTable() {
        ObservableList<GalleryTm> tmList = FXCollections.observableArrayList();

        for (Gallery gallery : galleryList) {
            GalleryTm galleryTm = new GalleryTm(
                    gallery.getGalleryId(),
                    gallery.getName(),
                    gallery.getDate(),
                    gallery.getDescription(),
                    gallery.getClientId()
            );

            tmList.add(galleryTm);
        }
        tblGallery.setItems(tmList);
        GalleryTm selectedItem = tblGallery.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Gallery> getAllGallery() {
        List<Gallery> galleryList = null;
        try {
            galleryList = GalleryRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return galleryList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String gallery_id = txtGalleryId.getText();
        String name = txtName.getText();
        String date = txtDate.getText();
        String description = txtDescription.getText();
        String client_id = txtClientId.getText();

        Gallery gallery = new Gallery(gallery_id, name, date, description, client_id);

        try {
            boolean isSaved = GalleryRepo.save(gallery);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "gallery saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our gallery model

    }

    private void clearFields() {
        txtGalleryId.setText("");
        txtName.setText("");
        txtDate.setText("");
        txtDescription.setText("");
        txtClientId.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String gallery_id = txtGalleryId.getText();
        String name = txtName.getText();
        String date = txtDate.getText();
        String description = txtDescription.getText();
        String client_id = txtClientId.getText();

        Gallery gallery = new Gallery(gallery_id, name, date, description, client_id);

        try {
            boolean isUpdated = GalleryRepo.update(gallery);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "gallery updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String gallery_id = txtGalleryId.getText();

        try {
            Gallery gallery = GalleryRepo.searchById(gallery_id);

            if (gallery != null) {
                txtGalleryId.setText(gallery.getGalleryId());
                txtName.setText(gallery.getName());
                txtDate.setText(gallery.getDate());
                txtDescription.setText(gallery.getDescription());
                txtClientId.setText(gallery.getClientId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String gallery_id = txtGalleryId.getText();

        try {
            boolean isDeleted = GalleryRepo.delete(gallery_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "gallery deleted!").show();
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
        Pattern galleryidPattern = Pattern.compile("^(G)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^([A-z ]{3,15}$)");
        Pattern datePattern = Pattern.compile("^(19|20)\\d{2}-(0[1-9]|1[1,2])-(0[1-9]|[12][0-9]|3[01])$");
        Pattern descriptionPattern = Pattern.compile("^([A-z ]{3,30}$)");
        Pattern clientidPattern = Pattern.compile("^(C)[0-9]{3}$");

        btnSaveGallery.setDisable(true);
        if (!galleryidPattern.matcher(txtGalleryId.getText()).matches()){
            addError(txtGalleryId);
        }else{
            removeError(txtGalleryId);
            if(!namePattern.matcher(txtName.getText()).matches()){
                addError(txtName);
            }else{
                removeError(txtName);
                if(!datePattern.matcher(txtDate.getText()).matches()){
                    addError(txtDate);
                }else{
                    removeError(txtDate);
                    if (!descriptionPattern.matcher(txtDescription.getText()).matches()){
                        addError(txtDescription);
                    }else{
                        removeError(txtDescription);
                        if (!clientidPattern.matcher(txtClientId.getText()).matches()){
                            addError(txtClientId);
                        }else{
                            removeError(txtClientId);
                        }
                    }
                }
            }
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSaveGallery.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSaveGallery.setDisable(true);
    }
}