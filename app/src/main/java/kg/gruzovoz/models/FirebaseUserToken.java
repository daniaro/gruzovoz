package kg.gruzovoz.models;

public class FirebaseUserToken {

    private String firebase_token;

    public FirebaseUserToken(){

    }
    public FirebaseUserToken(String firebaseToken) {
        this.firebase_token = firebaseToken;
    }

    public String getFirebaseToken() {
        return firebase_token;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebase_token = firebaseToken;
    }
}
