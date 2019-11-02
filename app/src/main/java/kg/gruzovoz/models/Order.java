package kg.gruzovoz.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

    private long id;
    @SerializedName("type_of_car")
    private String carType;
    @SerializedName("start_address")
    private String startAddress;
    @SerializedName("finish_address")
    private String finishAddress;
    @SerializedName("order_price")
    private double price;
    @SerializedName("commission")
    private String commission;
    @SerializedName("lead_time")
    private int leadTime;
    @SerializedName("date_of_created")
    private String dateOfCreated;
    @SerializedName("type_of_transportation")
    private String cargoType;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("comments")
    private String comments;
    @SerializedName("active")
    private boolean isActive;
    @SerializedName("done")
    private boolean isDone;

    public Order(long id, String carType, String startAddress, String finishAddress, double price, String commission, int leadTime,String dateOfCreated, String cargoType, String phoneNumber, String comments, boolean active, boolean done) {
        this.id = id;
        this.carType = carType;
        this.startAddress = startAddress;
        this.finishAddress = finishAddress;
        this.price = price;
        this.commission = commission;
        this.leadTime = leadTime;
        this.dateOfCreated = dateOfCreated;
        this.cargoType = cargoType;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
        this.isActive = active;
        this.isDone = done;
    }

    public long getId() {
        return id;
    }

    public String getCarType() {
        return carType;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public String getFinishAddress() {
        return finishAddress;
    }

    public double getPrice() {
        return price;
    }

    public String getCommission() {
        return commission;
    }

    public int getLeadTime() {
        return leadTime;
    }

    public String getCargoType() {
        return cargoType;
    }

    public String getDateOfCreated() {
        return dateOfCreated;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getComments() {
        return comments;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public boolean isActive() {
        return isActive;
    }
}
