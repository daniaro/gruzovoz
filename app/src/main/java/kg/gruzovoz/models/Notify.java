package kg.gruzovoz.models;

public class Notify {

    String uui;
    String userFullName;
    String text;

    public Notify(){

    }

    public Notify(String uui, String userFullName, String text) {
        this.uui = uui;
        this.userFullName = userFullName;
        this.text = text;
    }

    public String getUui() {
        return uui;
    }

    public void setUui(String uui) {
        this.uui = uui;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Notify{" +
                "uui='" + uui + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
