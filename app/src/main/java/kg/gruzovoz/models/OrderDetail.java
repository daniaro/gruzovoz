package kg.gruzovoz.models;

public class OrderDetail {


    private int id;
    private String carType;
    private String date;
    private String initialDestination;
    private String finalDestination;
    private double payment;
    private double commission;
    private String cargoType;
    private String description;

    public String getDate() {
        return date;
    }

    public OrderDetail(int id, String carType, String date, String initialDestination, String finalDestination, double payment, double commission, String cargoType, String description) {
        this.id = id;
        this.carType = carType;
        this.date = date;
        this.initialDestination = initialDestination;
        this.finalDestination = finalDestination;
        this.payment = payment;
        this.commission = commission;
        this.cargoType = cargoType;
        this.description = description;
    }

    public String getCarType() {
        return carType;
    }

    public String getInitialDestination() {
        return initialDestination;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public double getPayment() {
        return payment;
    }

    public double getCommission() {
        return commission;
    }

    public String getCargoType() {
        return cargoType;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

}
