package kg.gruzovoz.models;

import com.google.gson.annotations.SerializedName;

public class Order {

    private long id;
    @SerializedName("type_of_car")
    private String carType;
    @SerializedName("start_address")
    private String address;
    @SerializedName("order_price")
    private double price;
    private String comission;
    @SerializedName("lead_time")
    private int time;

    public Order(long id, String carType, String address, double price, String comission, int time) {
        this.id = id;
        this.carType = carType;
        this.address = address;
        this.price = price;
        this.comission = comission;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public String getCarType() {
        return carType;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public String getComission() {
        return comission;
    }

    public int getTime() {
        return time;
    }
}

