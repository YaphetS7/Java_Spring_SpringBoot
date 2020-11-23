package app.entity;

public class Customer extends BaseEntity {

    private String phoneNumber;
    private String name;

    public Customer(int id, String phoneNumber, String name) {
        super(id);

        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
