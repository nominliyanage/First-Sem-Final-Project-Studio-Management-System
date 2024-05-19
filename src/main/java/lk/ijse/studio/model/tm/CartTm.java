package lk.ijse.studio.model.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartTm {
    private String bookingId;
    private String userId;
    private String clientId;
    private String employeeId;
    private String packageId;
    private String date;
    private String time;
    private String location;
    private double price;
    private JFXButton btnRemove;
}