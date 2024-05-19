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
import lk.ijse.studio.model.Payment;
import lk.ijse.studio.model.tm.PaymentTm;
import lk.ijse.studio.repository.PaymentRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentFormController {
    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colClientId;

    @FXML
    private TableColumn<?, ?> colBookingId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnSavePayment;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtClientId;

    @FXML
    private TextField txtBookingId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    private List<Payment> paymentList = new ArrayList<>();

    public void initialize() {
        this.paymentList = getAllPayment();
        setCellValueFactory();
        loadPaymentTable();
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadPaymentTable() {
        ObservableList<PaymentTm> tmList = FXCollections.observableArrayList();

        for (Payment payment : paymentList) {
            PaymentTm paymentTm = new PaymentTm(
                    payment.getPaymentId(),
                    payment.getClientId(),
                    payment.getBookingId(),
                    payment.getAmount(),
                    payment.getDate()
            );

            tmList.add(paymentTm);
        }
        tblPayment.setItems(tmList);
        PaymentTm selectedItem = tblPayment.getSelectionModel().getSelectedItem();
        System.out.println("selectedItem = " + selectedItem);
    }

    private List<Payment> getAllPayment() {
        List<Payment> paymentList = null;
        try {
            paymentList = PaymentRepo.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paymentList;
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String payment_id = txtPaymentId.getText();
        String client_id = txtClientId.getText();
        String booking_id = txtBookingId.getText();
        String amount = txtAmount.getText();
        String date = txtDate.getText();

        Payment payment = new Payment(payment_id, client_id, booking_id, amount, date);

        try {
            boolean isSaved = PaymentRepo.save(payment);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment saved!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

//        now we should persist our payment model

    }

    private void clearFields() {
        txtPaymentId.setText("");
        txtClientId.setText("");
        txtBookingId.setText("");
        txtAmount.setText("");
        txtDate.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String payment_id = txtPaymentId.getText();
        String client_id = txtClientId.getText();
        String booking_id = txtBookingId.getText();
        String amount = txtAmount.getText();
        String date = txtDate.getText();

        Payment payment = new Payment(payment_id, client_id, booking_id, amount, date);

        try {
            boolean isUpdated = PaymentRepo.update(payment);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String payment_id = txtPaymentId.getText();

        try {
            Payment payment = PaymentRepo.searchById(payment_id);

            if (payment != null) {
                txtPaymentId.setText(payment.getPaymentId());
                txtClientId.setText(payment.getClientId());
                txtBookingId.setText(payment.getBookingId());
                txtAmount.setText(payment.getAmount());
                txtDate.setText(payment.getDate());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String payment_id = txtPaymentId.getText();

        try {
            boolean isDeleted = PaymentRepo.delete(payment_id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "payment deleted!").show();
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
        Pattern paymentidPattern = Pattern.compile("^(Pay)[0-9]{3}$");
        Pattern clientidPattern = Pattern.compile("^(C)[0-9]{3}$");
        Pattern bookingidPattern = Pattern.compile("^(B)[0-9]{3}$");
        Pattern amountPattern = Pattern.compile("^([0-9]){1,}[.]([0-9]){1,}$");
        Pattern datePattern = Pattern.compile("^(19|20)\\d{2}-(0[1-9]|1[1,2])-(0[1-9]|[12][0-9]|3[01])$");

        btnSavePayment.setDisable(true);
        if (!paymentidPattern.matcher(txtPaymentId.getText()).matches()){
            addError(txtPaymentId);
        }else{
            removeError(txtPaymentId);
            if(!clientidPattern.matcher(txtClientId.getText()).matches()){
                addError(txtClientId);
            }else{
                removeError(txtClientId);
                if(!bookingidPattern.matcher(txtBookingId.getText()).matches()){
                    addError(txtBookingId);
                }else{
                    removeError(txtBookingId);
                    if (!amountPattern.matcher(txtAmount.getText()).matches()){
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
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: green");
        btnSavePayment.setDisable(false);
    }

    private void addError(TextField textField) {
        if (textField.getText().length()>0) {
            textField.setStyle("-fx-border-color: red");
        }
        btnSavePayment.setDisable(true);
    }
}