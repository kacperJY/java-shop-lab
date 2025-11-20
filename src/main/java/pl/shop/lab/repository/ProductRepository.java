package pl.shop.lab.repository;

import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.io.CsvUtils;
import pl.shop.lab.io.IdGenerator;
import pl.shop.lab.model.DigitalProduct;
import pl.shop.lab.model.PhysicalProduct;
import pl.shop.lab.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository implements Repository<Product> {

    private static final String FILE_PATH = ApplicationContext.getProductsCsvPath();

    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        products.addAll(loadAll());
    }

    // ============================================================
    // LOAD
    // ============================================================

    @Override
    public List<Product> loadAll() {

        List<Product> result = new ArrayList<>();
        List<String[]> rows = CsvUtils.read(FILE_PATH);

        for (String[] r : rows) {

            // r[0] = TYPE: PHYSICAL / DIGITAL
            String type = r[0];
            String id = r[1];
            String name = r[2];
            double price = Double.parseDouble(r[3]);
            String imageUrl = r[4];

            Product product;

            if (type.equals("PHYSICAL")) {
                // brand, category, weight, shippingCost
                String brand = r[5];
                String category = r[6];
                double weight = Double.parseDouble(r[7]);
                double shippingCost = Double.parseDouble(r[8]);

                product = new PhysicalProduct(
                        id, name, "", price, imageUrl,
                        brand, category, weight, shippingCost
                );

            } else { // DIGITAL
                // platform, licenseKey, downloadUrl
                String platform = r[5];
                String licenseKey = r[6];
                String downloadUrl = r[7];

                product = new DigitalProduct(
                        id, name, "", price, imageUrl,
                        platform, licenseKey, downloadUrl
                );
            }

            result.add(product);
        }

        return result;
    }

    // ============================================================
    // SAVE
    // ============================================================

    @Override
    public void saveAll(List<Product> items) {
        List<String[]> rows = new ArrayList<>();

        for (Product p : items) {
            rows.add(p.toCsv().split(";"));
        }

        CsvUtils.write(FILE_PATH, rows);
    }

    // ============================================================
    // CRUD
    // ============================================================

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public Optional<Product> findById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product insert(Product product) {
        String newId = IdGenerator.nextId("prd");
        product.setId(newId);

        products.add(product);
        saveAll(products);

        return product;
    }

    @Override
    public void update(Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(product.getId())) {
                products.set(i, product);
                saveAll(products);
                return;
            }
        }
    }

    // ============================================================
    // EXTRA — filtrowanie pod GUI
    // ============================================================

    public List<Product> findByName(String text) {
        String lower = text.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .toList();
    }

    public List<Product> getPhysical() {
        return products.stream()
                .filter(p -> p instanceof PhysicalProduct)
                .toList();
    }

    public List<Product> getDigital() {
        return products.stream()
                .filter(p -> p instanceof DigitalProduct)
                .toList();
    }
}
