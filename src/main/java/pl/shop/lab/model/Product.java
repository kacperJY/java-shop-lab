package pl.shop.lab.model;

public abstract class Product {

    private String id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;

    public Product(String id, String name,String description, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setDescription(String description) {
        this.description = description;
    }

    public abstract String toCsv();
    public abstract String getType();
}
