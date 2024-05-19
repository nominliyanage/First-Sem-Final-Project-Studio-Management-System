package lk.ijse.studio.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.studio.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane rootNode;

    public Pane paneHolder;
    public Label lblClientCount;

    public Label lblBookingCount;
    private int clientCount;

    private int bookingCount;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtTime;

    public void initialize() {
        try {
            clientCount = getClientCount();
            bookingCount = getBookingCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setClientCount(clientCount);
        setBookingCount(bookingCount);
        loadTime();

    }

    public String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
        //System.out.println(dateFormat.format(new Date()));
        return dateFormat.format(new Date()) ;
    }

    private void loadTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        txtDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {

            txtTime.setText(timeNow());
            // lblTime.setText(LocalDateTime.now().format(formatter));

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void setClientCount(int clientCount) {
        lblClientCount.setText(String.valueOf(clientCount));
    }

    private int getClientCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS client_count FROM client";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int clientCount = 0;
        if(resultSet.next()) {
            clientCount = resultSet.getInt("client_count");
        }
        return clientCount;
    }

    private void setBookingCount(int bookingCount) {
        lblBookingCount.setText(String.valueOf(bookingCount));
    }

    private int getBookingCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS booking_count FROM booking";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int bookingCount = 0;
        if(resultSet.next()) {
            bookingCount = resultSet.getInt("booking_count");
        }
        return bookingCount;
    }

    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
 }

    @FXML
    void btnClientOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/client_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    void btnBookingOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/booking_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }


    @FXML
    void btnPackageOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/package_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employee_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    void btnEquipmentOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/equipment_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/payment_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    void btnGalleryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/gallery_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/salary_form.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        paneHolder.getChildren().clear();
        paneHolder.getChildren().add(registerPane);
    }

    @FXML
    public void btnExitOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
    }
}
