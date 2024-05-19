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
import lk.ijse.studio.model.Client;
import lk.ijse.studio.model.tm.ClientTm;
import lk.ijse.studio.repository.ClientRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientFormController {
    @FXML
    private TableColumn<?, ?> colClientId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<ClientTm> tblClient;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnSaveClient;

    @FXML
    private TextField txtClientId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtUserId;

    private List<Client> clientList = new ArrayList<>();

    public void initialize() {
        this.clientList = getAllClient();
        setCellValueFactory();
        loadClientTable();
        //btnSaveClient.setDisable(true);
    }

    private void setCellValueFactory() {
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    private void loadClientTable() {
        ObservableList<ClientTm> tmList = FXCollections.observableArrayList();

        for (Client client : clientList) {
            ClientTm clientTm = new ClientTm(
                    client.getClientId(),
                    client.getName(),
                    client.getTel(),
                    client.getUserId()
            );

            tmList.add(clientTm);
        }
        tblClient.setItems(tmList);
        ClientTm selectedItem = tblClient.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Client> getAllClient() {
        List<Client> clientList = null;
        try {
            clientList = ClientRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String client_id = txtClientId.getText();
        String name = txtName.getText();
        String tel = txtTel.getText();
        String user_id = txtUserId.getText();


           Client client = new Client(client_id, name, user_id, tel);

        try {
            boolean isSaved = ClientRepo.save(client);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "client saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our client model

    }

    private void clearFields() {
        txtClientId.setText("");
        txtName.setText("");
        txtUserId.setText("");
        txtTel.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String client_id = txtClientId.getText();
        String name = txtName.getText();
        String user_id = txtUserId.getText();
        String tel = txtTel.getText();

        Client client = new Client(client_id, name, user_id, tel);

        try {
            boolean isUpdated = ClientRepo.update(client);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "client updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String client_id = txtClientId.getText();

        try {
            Client client = ClientRepo.searchById(client_id);

            if (client != null) {
                txtClientId.setText(client.getClientId());
                txtName.setText(client.getName());
                txtUserId.setText(client.getUserId());
                txtTel.setText(client.getTel());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String client_id = txtClientId.getText();

        try {
            boolean isDeleted = ClientRepo.delete(client_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "client deleted!").show();
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
/*
    public void txtClientIdOnKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(C)[0-9]{3}$");
        btnSaveClient.setDisable(true);
        if (!idPattern.matcher(txtClientId.getText()).matches()){
            addError(txtClientId);
            //btnSaveClient.setDisable(true);
        }else{
            removeError(txtClientId);
            btnSaveClient.setDisable(false);
        }
    }

    public void txtClientNameOnKeyReleased(KeyEvent keyEvent){
        Pattern namePattern = Pattern.compile("^([A-z ]{3,15}$)");

        if(!namePattern.matcher(txtName.getText()).matches()){
            addError(txtName);
        }else{
            removeError(txtName);
        }
    }

    public void txtTelOnKeyReleased(KeyEvent keyEvent){
        Pattern telPattern = Pattern.compile("^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$");

        if(!telPattern.matcher(txtTel.getText()).matches()){
            addError(txtTel);
        }else{
            removeError(txtTel);
        }
    }

    public void txtUserIdOnKeyReleased(KeyEvent keyEvent) {
        Pattern idPattern = Pattern.compile("^(U)[0-9]{3}$");

        if (!idPattern.matcher(txtUserId.getText()).matches()){
            addError(txtUserId);
        }else{
            removeError(txtUserId);
        }
    }*/

    public void TextFieldKeyRelased(KeyEvent keyEvent) {
        Pattern clientidPattern = Pattern.compile("^(C)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^([A-z ]{3,15})$");
        Pattern telPattern = Pattern.compile("^([+]94{1,3}|[0])([1-9]{2})([0-9]){7}$");
        Pattern useridPattern = Pattern.compile("^(U)[0-9]{3}$");

        btnSaveClient.setDisable(true);
        if (!clientidPattern.matcher(txtClientId.getText()).matches()){
            addError(txtClientId);
        }else{
            removeError(txtClientId);
            if(!namePattern.matcher(txtName.getText()).matches()){
                addError(txtName);
            }else{
                removeError(txtName);
                if(!telPattern.matcher(txtTel.getText()).matches()){
                    addError(txtTel);
                }else{
                    removeError(txtTel);
                    if (!useridPattern.matcher(txtUserId.getText()).matches()){
                        addError(txtUserId);
                    }else{
                        removeError(txtUserId);
                    }
                }
            }
        }
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSaveClient.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSaveClient.setDisable(true);
    }

}