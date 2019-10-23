package kg.gruzovoz.models;

import android.content.res.Resources;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.PrivateKey;

import kg.gruzovoz.R;

public class OrderDetail {

    private long id;
    @SerializedName("type_of_car")
    private String carType;
    @SerializedName("start_address")
    private String startAddress;
    @SerializedName("finish_address")
    private String finishAddress;
    @SerializedName("order_price")
    private double price;
    @SerializedName("comission")
    private String comission;
    @SerializedName("lead_time")
    private String leadTime;
    @SerializedName("type_of_transportation")
    private String cargoType;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("comments")
    private String comments;
    @SerializedName("active")
    private boolean active;
    @SerializedName("done")
    private boolean done;

    public OrderDetail(long id, String carType, String startAddress, String finishAddress, double price, String comission, int leadTime, String cargoType, String phoneNumber, String comments, boolean active, boolean done) {
        this.id = id;
        this.carType = carType;
        this.startAddress = startAddress;
        this.finishAddress = finishAddress;
        this.price = price;
        this.comission = comission;
        switch (leadTime) {
            case 1:
                this.leadTime = Resources.getSystem().getString(R.string.date_today);
                break;
            case 2:
                this.leadTime = Resources.getSystem().getString(R.string.date_tomorrow);
                break;
            case 3: 
                this.leadTime = Resources.getSystem().getString(R.string.date_now);
                break;
        }
        this.cargoType = cargoType;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
        this.active = active;
        this.done = done;
    }
}
