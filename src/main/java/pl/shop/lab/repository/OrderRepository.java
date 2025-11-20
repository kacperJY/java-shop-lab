package pl.shop.lab.repository;

import pl.shop.lab.App;
import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.io.CsvUtils;
import pl.shop.lab.io.IdGenerator;
import pl.shop.lab.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements Repository<Order> {

    private static final String FILE_PATH = ApplicationContext.getOrdersCsvPath();

    private final List<Order> orders = new ArrayList<>();

    public OrderRepository() {
        orders.addAll(loadAll());
    }

    // ============================================================
    // LOAD
    // ============================================================

    @Override
    public List<Order> loadAll() {

        List<Order> list = new ArrayList<>();
        List<String[]> rows = CsvUtils.read(FILE_PATH);

        for (String[] r : rows) {

            // CSV format:
            // 0 id
            // 1 userId
            // 2 totalPrice
            // 3 status
            // 4 createdAt
            // 5 fullName
            // 6 city
            // 7 street
            // 8 postalCode
            // 9 phone
            // 10 itemsString

            String id = r[0];
            String userId = r[1];
            double totalPrice = Double.parseDouble(r[2]);
            OrderStatus status = OrderStatus.valueOf(r[3]);
            LocalDateTime createdAt = LocalDateTime.parse(r[4]);

            Address address = new Address(
                    r[5], // fullName
                    r[6], // city
                    r[7], // street
                    r[8], // postalCode
                    r[9]  // phone
            );

            String itemsString = r[10];
            List<OrderItem> items = parseItems(itemsString);

            Order order = new Order(id, userId, items, totalPrice, address, status, createdAt);

            list.add(order);
        }

        return list;
    }

    // ============================================================
    // SAVE
    // ============================================================

    @Override
    public void saveAll(List<Order> items) {

        List<String[]> rows = new ArrayList<>();

        for (Order o : items) {
            rows.add(new String[]{
                    o.getId(),
                    o.getUserId(),
                    String.valueOf(o.getTotalPrice()),
                    o.getStatus().name(),
                    o.getCreatedAt().toString(),
                    o.getDeliveryAddress().getFullName(),
                    o.getDeliveryAddress().getCity(),
                    o.getDeliveryAddress().getStreet(),
                    o.getDeliveryAddress().getPostalCode(),
                    o.getDeliveryAddress().getPhone(),
                    itemsToString(o.getItems())
            });
        }

        CsvUtils.write(FILE_PATH, rows);
    }

    // ============================================================
    // CRUD
    // ============================================================

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Optional<Order> findById(String id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public Order insert(Order order) {

        String newId = IdGenerator.nextId("ord");

        Order newOrder = new Order(
                newId,
                order.getUserId(),
                order.getItems(),
                order.getTotalPrice(),
                order.getDeliveryAddress(),
                order.getStatus(),
                order.getCreatedAt()
        );

        orders.add(newOrder);
        saveAll(orders);
        return newOrder;
    }

    @Override
    public void update(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getId().equals(order.getId())) {
                orders.set(i, order);
                saveAll(orders);
                return;
            }
        }
    }

    // ============================================================
    // HELPERS
    // ============================================================

    private static String itemsToString(List<OrderItem> items) {
        List<String> itemStrings = new ArrayList<>();

        for (OrderItem i : items) {
            itemStrings.add(String.join(",",
                    i.getProductId(),
                    i.getProductName(),
                    String.valueOf(i.getPricePerUnit()),
                    String.valueOf(i.getQuantity())
            ));
        }
        return String.join("|", itemStrings);
    }

    private static List<OrderItem> parseItems(String data) {

        List<OrderItem> items = new ArrayList<>();

        if (data.isBlank())
            return items;

        String[] parts = data.split("\\|");

        for (String p : parts) {
            String[] fields = p.split(",");
            String productId = fields[0];
            String name = fields[1];
            double price = Double.parseDouble(fields[2]);
            int qty = Integer.parseInt(fields[3]);

            items.add(new OrderItem(productId, name, price, qty));
        }

        return items;
    }
}
