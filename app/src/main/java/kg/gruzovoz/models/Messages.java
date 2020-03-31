package kg.gruzovoz.models;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Messages implements Serializable {

    private Timestamp sentAt;
    private String text;
    private String userFullName;
    private boolean isFromSuperAdmin;
    private String uid;

    public Messages(){

    }

    public Messages(Timestamp sentAt, String text, String userFullName, boolean isFromSuperAdmin, String uid) {
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


    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
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

//    private String sentAt;
//    private String text;
//    private String userFullName;
//    private boolean isFromSuperAdmin;
//    private String uid;
//
//    public Messages(){
//
//    }
//
//    public Messages(String sentAt, String text, String userFullName, boolean isFromSuperAdmin, String uid) {
//        this.sentAt = sentAt;
//        this.text = text;
//        this.userFullName = userFullName;
//        this.isFromSuperAdmin = isFromSuperAdmin;
//        this.uid = uid;
//    }
//
//    public Long parseDataToTheSeconds(){
//        Date date;
//
//        TimeZone timeZone = TimeZone.getTimeZone("Asia/Bishkek");
//        @SuppressLint("SimpleDateFormat")
//        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
//        sdf.setTimeZone(timeZone);
//
//        try {
//            date = sdf.parse(getSentAt());
//            assert date != null;
//            long dateInTime = date.getTime();
//            Log.e("dateInTime In Model", String.valueOf(dateInTime));
//            return dateInTime;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0L;
//        }
//    }
//
//    public boolean isFromSuperAdmin() {
//        return isFromSuperAdmin;
//    }
//
//    public void setFromSuperAdmin(boolean fromSuperAdmin) {
//        isFromSuperAdmin = fromSuperAdmin;
//    }
//
//    public String getUid() {
//        return uid;
//    }
//
//    public void setUid(String uid) {
//        this.uid = uid;
//    }
//
//
//    public String getSentAt() {
//        return sentAt;
//    }
//
//    public void setSentAt(String sentAt) {
//        this.sentAt = sentAt;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getUserFullName() {
//        return userFullName;
//    }
//
//    public void setUserFullName(String userFullName) {
//        this.userFullName = userFullName;
//    }
//}
