package lk.ijse.studio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Booking {
    private Book book;
    private List<BookingDetails> bdList;
}
