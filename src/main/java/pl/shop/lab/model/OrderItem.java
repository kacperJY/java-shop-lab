package pl.shop.lab.model;

public class OrderItem {

    private String productId;
    private String productName;
    private double pricePerUnit;
    private int quantity;

    public OrderItem(String productId, String productName,
                     double pricePerUnit, int quantity) {

        this.productId = productId;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPricePerUnit() { return pricePerUnit; }
    public int getQuantity() { return quantity; }

    public double getTotal() {
        return pricePerUnit * quantity;
    }
}
