package kg.gruzovoz.models;

public class UserObj {
    private String username;
    private String phone;


    public UserObj(String username, String phone_number) {
        this.username = username;
        this.phone = phone_number;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone_number() {
        return phone;
    }

    public void setPhone_number(String phone_number) {
        this.phone = phone_number;
    }
}
