package lk.ijse.studio.model;

public class Client { // this model class represent real world client entity
    private String client_id;
    private String name;
    private String user_id;
    private String tel;

    public Client() {
    }

    public Client(String client_id, String name, String user_id, String tel) {
        this.client_id = client_id;
        this.name = name;
        this.user_id = user_id;
        this.tel = tel;
    }

    public String getClientId() {
        return client_id;
    }

    public void setClientId(String client_id) {
        this.client_id = client_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + client_id + '\'' +
                ", name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
