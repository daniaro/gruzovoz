package kg.gruzovoz.models;

public class NotifyStatus {
    boolean message;

    public NotifyStatus(boolean message) {
        this.message = message;
    }

    public boolean isMessage() {
        return message;
    }

    public void setMessage(boolean message) {
        this.message = message;
    }
}
