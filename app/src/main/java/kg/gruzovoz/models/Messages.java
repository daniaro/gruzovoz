package kg.gruzovoz.models;

public class Messages {

    private String sentAt;
    private String text;
    private String userFullName;

    public Messages(){

    }

    public Messages(String sentAt, String text, String userFullName) {
        this.sentAt = sentAt;
        this.text = text;
        this.userFullName = userFullName;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}
