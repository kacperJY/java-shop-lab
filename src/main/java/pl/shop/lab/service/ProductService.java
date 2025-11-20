package pl.shop.lab.service;

import pl.shop.lab.model.DigitalProduct;
import pl.shop.lab.model.PhysicalProduct;
import pl.shop.lab.model.Product;
import pl.shop.lab.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // ========== GET ==========
    public List<Product> getAll() {
        return repo.findAll();
    }

    public Optional<Product> getById(String id) {
        return repo.findById(id);
    }

    // ========== ADD ==========
    public Product addPhysical(String name, String description, double price, String imageUrl,
                               String brand, String category, double weight, double shippingCost) {

        Product p = new PhysicalProduct(
                null, name, description, price, imageUrl,
                brand, category, weight, shippingCost
        );

        return repo.insert(p);
    }

    public Product addDigital(String name, String description, double price, String imageUrl,
                              String platform, String licenseKey, String downloadUrl) {

        Product p = new DigitalProduct(
                null, name, description, price, imageUrl,
                platform, licenseKey, downloadUrl
        );

        return repo.insert(p);
    }

    // ========== UPDATE ==========
    public boolean update(Product product) {
        if (repo.findById(product.getId()).isEmpty())
            return false;

        repo.update(product);
        return true;
    }

    // ========== FILTERS ==========
    public List<Product> findByName(String name) {
        return repo.findByName(name);
    }

    public List<Product> getPhysical() {
        return repo.getPhysical();
    }

    public List<Product> getDigital() {
        return repo.getDigital();
    }
}

