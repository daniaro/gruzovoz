package kg.gruzovoz.models;

public class UserPage {
    private UserObj user;
    private int car_category;
    private String car_number;
    private String type_of_car;
    private String car_color;
    private String balance;

    public UserPage(UserObj user, int car_category, String car_number, String type_of_car, String car_color, String balance) {
        this.user = user;
        this.car_category = car_category;
        this.car_number = car_number;
        this.type_of_car = type_of_car;
        this.car_color = car_color;
        this.balance = balance;
    }

    public UserPage() {

    }

    public UserObj getUser() {
        return user;
    }

    public void setUser(UserObj user) {
        this.user = user;
    }

    public int getCar_category() {
        return car_category;
    }

    public void setCar_category(int car_category) {
        this.car_category = car_category;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getType_of_car() {
        return type_of_car;
    }

    public void setType_of_car(String type_of_car) {
        this.type_of_car = type_of_car;
    }

    public String getCar_color() {
        return car_color;
    }

    public void setCar_color(String car_color) {
        this.car_color = car_color;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}


