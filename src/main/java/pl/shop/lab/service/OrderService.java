package pl.shop.lab.service;

import pl.shop.lab.model.*;
import pl.shop.lab.repository.OrderRepository;
import pl.shop.lab.repository.ProductRepository;
import pl.shop.lab.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public OrderService(OrderRepository orderRepo,
                        ProductRepository productRepo,
                        UserRepository userRepo) {

        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    // ============================================================
    // CREATE ORDER (auto-payment)
    // ============================================================

    public Optional<Order> createOrder(String userId, List<OrderItem> items, Address deliveryAddress) {

        // 1) calculate price
        double total = items.stream()
                .mapToDouble(OrderItem::getTotal)
                .sum();

        // 2) get user
        Optional<User> userOpt = userRepo.findById(userId);
        if (userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();

        // 3) check balance
        if (user.getBalance() < total) {
            return Optional.empty(); // not enough money
        }

        // 4) reduce balance
        user.setBalance(user.getBalance() - total);
        userRepo.update(user);

        // 5) create order
        Order order = new Order(
                null,
                userId,
                items,
                total,
                deliveryAddress,
                OrderStatus.PAID,
                LocalDateTime.now()
        );

        return Optional.of(orderRepo.insert(order));
    }

    // ============================================================
    // ADMIN — update status
    // ============================================================

    public boolean updateStatus(String orderId, OrderStatus status) {
        Optional<Order> orderOpt = orderRepo.findById(orderId);
        if (orderOpt.isEmpty()) return false;

        Order o = orderOpt.get();
        o.setStatus(status);
        orderRepo.update(o);

        return true;
    }

    // ============================================================
    // GETTERS
    // ============================================================

    public List<Order> getUserOrders(String userId) {
        return orderRepo.findAll().stream()
                .filter(o -> o.getUserId().equals(userId))
                .toList();
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public Optional<Order> getById(String id) {
        return orderRepo.findById(id);
    }
}
