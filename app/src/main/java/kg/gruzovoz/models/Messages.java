package kg.gruzovoz.models;

public class Messages {

    private String sentAt;
    private String text;
    private String userFullName;
    private boolean isFromSuperAdmin;
    private String uid;

    public Messages(){

    }

    public Messages(String sentAt, String text, String userFullName, boolean isFromSuperAdmin, String uid) {
        this.sentAt = sentAt;
        this.text = text;
        this.userFullName = userFullName;
        this.isFromSuperAdmin = isFromSuperAdmin;
        this.uid = uid;
    }

    public boolean isFromSuperAdmin() {
        return isFromSuperAdmin;
    }

    public void setFromSuperAdmin(boolean fromSuperAdmin) {
        isFromSuperAdmin = fromSuperAdmin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
