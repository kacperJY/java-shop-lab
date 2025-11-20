package pl.shop.lab.model;

import java.util.Objects;

public class User {
    private String id;
    private String login;
    private String password;
    private double balance;
    private UserRole userRole;
    private Address address;

    public User(String id, String login, String password, double balance, UserRole userRole, Address address) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.balance = balance;
        this.userRole = userRole;
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", userRole=" + userRole +
                ", address=" + address +
                '}';
    }

    public String toCsv() {
        return String.join(";",
                getId(),
                getLogin(),
                getPassword(),
                String.valueOf(getBalance()),
                getUserRole().name(),
                address.getFullName(),
                address.getCity(),
                address.getStreet(),
                address.getPostalCode(),
                address.getPhone()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
