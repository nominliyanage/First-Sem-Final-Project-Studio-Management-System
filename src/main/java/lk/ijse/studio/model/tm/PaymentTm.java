package lk.ijse.studio.model.tm;

public class PaymentTm {
    private String payment_id;
    private String client_id;
    private String booking_id;
    private String amount;
    private String date;

    public PaymentTm() {
    }

    public PaymentTm(String payment_id, String client_id, String booking_id, String amount, String date) {
        this.payment_id = payment_id;
        this.client_id = client_id;
        this.booking_id = booking_id;
        this.amount = amount;
        this.date = date;
    }

    public String getPaymentId() {
        return payment_id;
    }

    public void setPaymentId(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getClientId() {
        return client_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public String getBookingId() {
        return booking_id;
    }

    public void setBookingId(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PaymentTm{" +
                "payment_id='" + payment_id + '\'' +
                ", client_id='" + client_id + '\'' +
                ", booking_id='" + booking_id + '\'' +
                ", amount='" + amount + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
