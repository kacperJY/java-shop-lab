package pl.shop.lab.model;

public class Address {
    private String fullName;
    private String city;
    private String street;
    private String postalCode;
    private String phone;

    public Address(String fullName, String city, String street, String postalCode, String phone) {
        this.fullName = fullName;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
