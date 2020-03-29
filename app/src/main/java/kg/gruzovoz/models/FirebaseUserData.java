package kg.gruzovoz.models;

public class FirebaseUserData {

    private long id;
    private String firebase_token;
    private String username;
    private String phone;

    public FirebaseUserData(){

    }


    public FirebaseUserData(long id, String firebase_token, String username, String phone) {
        this.id = id;
        this.firebase_token = firebase_token;
        this.username = username;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirebaseToken() {
        return firebase_token;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebase_token = firebaseToken;
    }
}
