package pl.shop.lab.model;

public enum OrderStatus {
    NEW,           // zamówienie złożone przez klienta
    PAID,          // płatność zatwierdzona
    SHIPPED,       // wysłane
    COMPLETED,     // zakończone
    CANCELLED      // odrzucone przez admina
}
