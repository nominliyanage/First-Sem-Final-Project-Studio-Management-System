package lk.ijse.studio.model.tm;

public class UserTm {
    private String user_id;
    private String name;
    private String password;


    public UserTm() {
    }

    public UserTm(String user_id, String name, String password) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTm{" +
                "user_id='" + user_id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
