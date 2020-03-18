package kg.gruzovoz.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Serializable {

    private long id;
    @SerializedName("type_of_car")
    private String carType;
    @SerializedName("type_of_transport")
    private int typeOfTransport;
    @SerializedName("type_of_cargo")
    private String typeOfCargo;
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
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("comments")
    private String comments;
    @SerializedName("active")
    private boolean isActive;
    @SerializedName("done")
    private boolean isDone;
    @SerializedName("calculated")
    private boolean calculate;

    public Order(long id, String carType, int typeOfTransport,String typeOfCargo, String startAddress, String finishAddress, double price, String commission, int leadTime,String dateOfCreated, String phoneNumber, String comments, boolean active, boolean done, boolean calculate) {
        this.id = id;
        this.carType = carType;
        this.typeOfTransport = typeOfTransport;
        this.typeOfCargo = typeOfCargo;
        this.startAddress = startAddress;
        this.finishAddress = finishAddress;
        this.price = price;
        this.commission = commission;
        this.leadTime = leadTime;
        this.dateOfCreated = dateOfCreated;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
        this.isActive = active;
        this.isDone = done;
        this.calculate = calculate;
    }

    public Order() {

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

    public boolean isActive() {
        return isActive;
    }

    public String getTypeOfCargo() {
        return typeOfCargo;
    }

    public boolean isCalculated() {
        return calculate;
    }
}
