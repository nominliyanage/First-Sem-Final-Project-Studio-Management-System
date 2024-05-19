package lk.ijse.studio.model.tm;

public class GalleryTm {
    private String gallery_id;
    private String name;
    private String date;
    private String description;
    private String client_id;

    public GalleryTm() {
    }

    public GalleryTm(String gallery_id, String name, String date, String description, String client_id) {
        this.gallery_id = gallery_id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.client_id = client_id;
    }

    public String getGalleryId() {
        return gallery_id;
    }

    public void setGalleryId(String gallery_id) {
        this.gallery_id = gallery_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClientId() {
        return client_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "GalleryTm{" +
                "gallery_id='" + gallery_id + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", client_id='" + client_id + '\'' +
                '}';
    }
}
