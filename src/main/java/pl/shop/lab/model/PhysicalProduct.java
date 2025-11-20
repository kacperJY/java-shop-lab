package pl.shop.lab.model;

public class PhysicalProduct extends Product {

    private String brand;
    private String category;
    private double weight;
    private double shippingCost;

    public PhysicalProduct(String id, String name, String description, double price, String imageUrl,
                           String brand, String category,
                           double weight, double shippingCost) {

        super(id, name, description, price, imageUrl);
        this.brand = brand;
        this.category = category;
        this.weight = weight;
        this.shippingCost = shippingCost;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public double getWeight() {
        return weight;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    @Override
    public String getType() {
        return "PHYSICAL";
    }

    @Override
    public String toCsv() {
        return String.join(";",
                getType(),
                getId(),
                getName(),
                String.valueOf(getPrice()),
                getImageUrl(),
                brand,
                category,
                String.valueOf(weight),
                String.valueOf(shippingCost)
        );
    }
}
