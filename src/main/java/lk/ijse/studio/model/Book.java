package lk.ijse.studio.model;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    private String bookingId;
    private String userId;
    private String clientId;
    private String employeeId;
    private String packageId;
    private String date;
    private String time;
    private String location;
    private double total;
}