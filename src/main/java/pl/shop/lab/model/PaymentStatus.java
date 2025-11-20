package pl.shop.lab.model;

public enum PaymentStatus {
    PENDING,   // Czeka na akceptację admina
    APPROVED,  // Admin zatwierdził
    REJECTED   // Admin odrzucił
}
