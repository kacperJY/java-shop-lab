package pl.shop.lab.model;

import java.time.LocalDateTime;

public class PaymentRequest {

    private String id;
    private String userId;
    private double amount;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public PaymentRequest(String id, String userId, double amount,
                          PaymentStatus status, LocalDateTime createdAt) {

        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String toCsv() {
        return String.join(";",
                id,
                userId,
                String.valueOf(amount),
                status.name(),
                createdAt.toString() // ISO 8601, pasuje do LocalDateTime.parse()
        );
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public double getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
