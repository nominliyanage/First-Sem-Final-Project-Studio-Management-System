package lk.ijse.studio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDetails {
    private String bookingId;
    private String packageId;
    private String date;

}
