package pl.shop.lab.model;

import pl.shop.lab.model.Address;
import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private String id;
    private String userId;
    private List<OrderItem> items;
    private double totalPrice;
    private Address deliveryAddress;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(String id, String userId, List<OrderItem> items,
                 double totalPrice, Address deliveryAddress,
                 OrderStatus status, LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.createdAt = createdAt;
    }

    // --- Gettery ---
    public String getId() { return id; }
    public String getUserId() { return userId; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }
    public Address getDeliveryAddress() { return deliveryAddress; }
    public OrderStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // --- Settery dla admina ---
    public void setStatus(OrderStatus status) { this.status = status; }
}
