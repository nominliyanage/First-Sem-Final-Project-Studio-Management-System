package lk.ijse.studio.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.studio.db.DbConnection;
import lk.ijse.studio.model.*;
import lk.ijse.studio.model.Package;
import lk.ijse.studio.model.tm.CartTm;
import lk.ijse.studio.repository.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Time;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class BookingFormController {

   @FXML
   private JFXButton btnAddToCart;

   @FXML
   private JFXComboBox<String> cmbClientId;

   @FXML
   private JFXComboBox<String> cmbUserId;

   @FXML
   private JFXComboBox<String> cmbEmployeeId;

   @FXML
   private JFXComboBox<String> cmbPackageId;

   @FXML
   private TableColumn<?, ?> colAction;

   @FXML
   private TableColumn<?, ?> colBookingId;

   @FXML
   private TableColumn<?, ?> colUserId;

   @FXML
   private TableColumn<?, ?> colClientId;

   @FXML
   private TableColumn<?, ?> colEmployeeId;

   @FXML
   private TableColumn<?, ?> colPackageId;

   @FXML
   private TableColumn<?, ?> colDate;

   @FXML
   private TableColumn<?, ?> colTime;

   @FXML
   private TableColumn<?, ?> colLocation;

   @FXML
   private TableColumn<?, ?> colPrice;

   @FXML
   private Label lblBookingId;

   @FXML
   private TextField txtDate;

   @FXML
   private TextField txtTime;

   @FXML
   private TextField txtLocation;

   @FXML
   private Label lblClientName;

   @FXML
   private Label lblPrice;

   @FXML
   private Label lblTotal;


   @FXML
   private AnchorPane pane;

   @FXML
   private TableView<CartTm> tblBookingCart;

   private ObservableList<CartTm> cartList = FXCollections.observableArrayList();
   private double Total = 0;

   public void initialize() {
      setCellValueFactory();
      loadNextBookingId();
      //setDate();
      getClientIds();
      getUserIds();
      getPackageIds();
      getEmployeeIds();
   }

   private void setCellValueFactory() {
      colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
      colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
      colClientId.setCellValueFactory(new PropertyValueFactory<>("clientId"));
      colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
      colPackageId.setCellValueFactory(new PropertyValueFactory<>("packageId"));
      colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
      colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
      colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
      colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
      colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
   }

   private void getPackageIds() {
      ObservableList<String> bdList = FXCollections.observableArrayList();
      try {
         List<String> idList = PackageRepo.getIds();
         for (String package_id : idList) {
            bdList.add(package_id);
         }

         cmbPackageId.setItems(bdList);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   private void getClientIds() {
      ObservableList<String> bdList = FXCollections.observableArrayList();
      try {
         List<String> idList = ClientRepo.getIds();
         for (String client_id : idList) {
            bdList.add(client_id);
         }

         cmbClientId.setItems(bdList);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

   }

   private void getUserIds() {
      ObservableList<String> bdList = FXCollections.observableArrayList();
      try {
         List<String> idList = UserRepo.getIds();
         for (String user_id : idList) {
            bdList.add(user_id);
         }

         cmbUserId.setItems(bdList);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

   }

   private void getEmployeeIds() {
      ObservableList<String> bdList = FXCollections.observableArrayList();
      try {
         List<String> idList = EmployeeRepo.getIds();
         for (String employee_id : idList) {
            bdList.add(employee_id);
         }

         cmbEmployeeId.setItems(bdList);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

   }

   private void loadNextBookingId() {
      try {
         String currentId = BookRepo.currentId();
         String nextId = nextId(currentId);

         lblBookingId.setText(nextId);
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   private String nextId(String currentId) {
      if (currentId != null) {
         String[] split = currentId.split("B");
//            System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
         int id = Integer.parseInt(split[1]);    //3
         return "B00" + ++id;

      }
      return "B" + "001";
   }
/*
   private void setDate() {
      LocalDate now = LocalDate.now();
      lblOrderDate.setText(String.valueOf(now));
   }
*/
   @FXML
   void btnAddToCartOnAction(ActionEvent event) {
      String booking_id = lblBookingId.getText();
      String user_id = cmbUserId.getValue();
      String client_id = cmbClientId.getValue();
      String employee_id = cmbEmployeeId.getValue();
      String package_id = cmbPackageId.getValue();
      String date = txtDate.getText();
      String time = txtTime.getText();
      String location = txtLocation.getText();
      double price = Double.parseDouble(lblPrice.getText());
      JFXButton btnRemove = new JFXButton("remove");
      btnRemove.setCursor(Cursor.HAND);

      btnRemove.setOnAction((e) -> {
         ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
         ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

         Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

         if(type.orElse(no) == yes) {
            int selectedIndex = tblBookingCart.getSelectionModel().getSelectedIndex();
            cartList.remove(selectedIndex);
            tblBookingCart.refresh();
            calculateTotal();
         }
      });

      for (int i = 0; i < tblBookingCart.getItems().size(); i++) {
         if (package_id.equals(colPackageId.getCellData(i))) {

            //cartList.get(i).setQty(qty);
            //cartList.get(i).setTotal(total);

            tblBookingCart.refresh();
            calculateTotal();
            return;
         }
      }

      CartTm cartTm = new CartTm(booking_id, user_id, client_id, employee_id, package_id, date, time, location, price, btnRemove);

      cartList.add(cartTm);

      tblBookingCart.setItems(cartList);
      calculateTotal();
   }

   private void calculateTotal() {
      Total = 0;
      for (int i = 0; i < tblBookingCart.getItems().size(); i++) {
         Total += (double) colPrice.getCellData(i);
      }
      lblTotal.setText(String.valueOf(Total));
   }

   @FXML
   void btnExitOnAction(ActionEvent event) throws IOException {
      AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
      Stage stage = (Stage) pane.getScene().getWindow();

      stage.setScene(new Scene(anchorPane));
      stage.setTitle("Dashboard Form");
      stage.centerOnScreen();
   }

   @FXML
   void btnNewCustomerOnAction(ActionEvent event) {

   }

   @FXML
   void btnBookingComfirmOnAction(ActionEvent event) {
      String bookingId = lblBookingId.getText();
      String clientId = cmbClientId.getValue();
      String userId = cmbUserId.getValue();
      String employeeId = cmbEmployeeId.getValue();
      String packageId = cmbPackageId.getValue();
      String date = txtDate.getText();
      String time = txtTime.getText();
      String location = txtLocation.getText();
      double price = Double.parseDouble(lblPrice.getText());


      var book = new Book(bookingId, userId, clientId, employeeId, packageId, date, time, location, price);

      List<BookingDetails> bdList = new ArrayList<>();
     for (int i = 0; i < tblBookingCart.getItems().size(); i++) {
         CartTm tm = cartList.get(i);

         BookingDetails bd = new BookingDetails(
                 bookingId,
                 tm.getPackageId(),
                 tm.getDate()
         );
         bdList.add(bd);
      }

      Booking bd = new Booking(book, bdList);
      try {
         boolean isPlaced = BookRepo.book(bd);
         if(isPlaced) {
            new Alert(Alert.AlertType.CONFIRMATION, "booking comfirm!").show();
         } else {
            new Alert(Alert.AlertType.WARNING, "booking not comfirm!").show();
         }
      } catch (SQLException e) {
         new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
      }
   }

   @FXML
   void cmbClientOnAction(ActionEvent event) {
      String client_Id = cmbClientId.getValue();

      try {
         Client client = ClientRepo.searchById(client_Id);

         lblClientName.setText(client.getName());

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   @FXML
   void cmbPackageOnAction(ActionEvent event) {
      String package_id = cmbPackageId.getValue();
      try {
         Package pack = PackageRepo.searchById(package_id);
         if (pack != null) {
            lblPrice.setText(pack.getPrice());

         }
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

   }

   @FXML
   void btnPrintBillOnAction(ActionEvent event) throws JRException, SQLException {
      JasperDesign jasperDesign =
              JRXmlLoader.load("src/main/resources/Report/StudioJasper.jrxml");
      JasperReport jasperReport =
              JasperCompileManager.compileReport(jasperDesign);

      Map<String, Object> data = new HashMap<>();
      data.put("CustomerID",cmbClientId.getValue());
      data.put("CustomerID",lblBookingId.getText());
      data.put("CustomerID",cmbPackageId.getValue());
      data.put("CustomerID",lblPrice.getText());
      //data.put("NetTotal","3000");

      JasperPrint jasperPrint =
              JasperFillManager.fillReport(
                      jasperReport,
                      data,
                      DbConnection.getInstance().getConnection());

      JasperViewer.viewReport(jasperPrint,false);

   }

}
