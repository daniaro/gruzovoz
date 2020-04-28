package kg.gruzovoz.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Results implements Serializable {

    private long id;
    @SerializedName("type_of_car")
    private String carType;
    @SerializedName("type_of_transport")
    private int typeOfTransport;
    @SerializedName("type_of_cargo")
    private String typeOfCargo;
    @SerializedName("short_address")
    private String shortAddress;
    @SerializedName("end_adress")
    private String endAdress;
    @SerializedName("detailed_address")
    private String detailedAdress;
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

    public Results(long id, String carType, int typeOfTransport, String typeOfCargo, String shortAddress, String endAdress, String detailedAdress, double price, String commission, int leadTime, String dateOfCreated, String phoneNumber, String comments, boolean isActive, boolean isDone, boolean calculate) {
        this.id = id;
        this.carType = carType;
        this.typeOfTransport = typeOfTransport;
        this.typeOfCargo = typeOfCargo;
        this.shortAddress = shortAddress;
        this.endAdress = endAdress;
        this.detailedAdress = detailedAdress;
        this.price = price;
        this.commission = commission;
        this.leadTime = leadTime;
        this.dateOfCreated = dateOfCreated;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
        this.isActive = isActive;
        this.isDone = isDone;
        this.calculate = calculate;
    }

//    public Results(long id, String carType, int typeOfTransport, String typeOfCargo, String shortAddress, String finishAddress, double price, String commission, int leadTime, String dateOfCreated, String phoneNumber, String comments, boolean active, boolean done, boolean calculate) {
//        this.id = id;
//        this.carType = carType;
//        this.typeOfTransport = typeOfTransport;
//        this.typeOfCargo = typeOfCargo;
//        this.shortAddress = shortAddress;
//        this.endAdress = finishAddress;
//        this.price = price;
//        this.commission = commission;
//        this.leadTime = leadTime;
//        this.dateOfCreated = dateOfCreated;
//        this.phoneNumber = phoneNumber;
//        this.comments = comments;
//        this.isActive = active;
//        this.isDone = done;
//        this.calculate = calculate;
//    }

    public String getDetailedAdress() {
        return detailedAdress;
    }

    public void setDetailedAdress(String detailedAdress) {
        this.detailedAdress = detailedAdress;
    }

    public Results() {
        super();
    }

    public long getId() {
        return id;
    }

    public String getCarType() {
        return carType;
    }

    public String getShortAddress() {
        return shortAddress;
    }

    public String getEndAdress() {
        return endAdress;
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
