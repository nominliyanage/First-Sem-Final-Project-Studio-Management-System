package lk.ijse.studio.model;

public class Equipment { // this model class represent real world client entity
    private String equipment_id;
    private String equipment_name;
    private String purchase_date;
    private String maintain_history;

    public Equipment() {
    }

    public Equipment(String equipment_id, String equipment_name, String purchase_date, String maintain_history) {
        this.equipment_id = equipment_id;
        this.equipment_name = equipment_name;
        this.purchase_date = purchase_date;
        this.maintain_history = maintain_history;
    }

    public String getEquipmentId() {
        return equipment_id;
    }

    public void setEquipmentId(String equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getName() {
        return equipment_name;
    }

    public void setName(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getPurchaseDate() {
        return purchase_date;
    }

    public void setPurchaseDate(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    public String getMaintainHistory() {
        return maintain_history;
    }

    public void setMaintainHistory(String maintain_history) {
        this.maintain_history = maintain_history;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "equipment_id='" + equipment_id + '\'' +
                ", equipment_name='" + equipment_name + '\'' +
                ", purchase_date='" + purchase_date + '\'' +
                ", maintain_history='" + maintain_history + '\'' +
                '}';
    }
}
