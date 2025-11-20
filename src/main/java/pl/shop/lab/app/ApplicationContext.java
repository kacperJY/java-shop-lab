package pl.shop.lab.app;

import pl.shop.lab.model.User;
import pl.shop.lab.repository.OrderRepository;
import pl.shop.lab.repository.PaymentRequestRepository;
import pl.shop.lab.repository.ProductRepository;
import pl.shop.lab.repository.UserRepository;
import pl.shop.lab.service.OrderService;
import pl.shop.lab.service.PaymentService;
import pl.shop.lab.service.ProductService;
import pl.shop.lab.service.UserService;

public class ApplicationContext {

    private static final ApplicationContext INSTANCE = new ApplicationContext();

    public static ApplicationContext getInstance(){
        return INSTANCE;
    }

    // ========== LOGGED USER ==========
    private User loggedUser;

    public void loginUser(User user){
        this.loggedUser = user;
    }

    public User getLoggedUser(){
        return this.loggedUser;
    }

    public void logoutUser(){
        this.loggedUser = null;
    }

    // ========== SERVICES ==========
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    private ApplicationContext(){
        // ========== REPOSITORIES ==========
        // Repository constructor - load CSV data
        PaymentRequestRepository paymentRequestRepository = new PaymentRequestRepository();
        UserRepository userRepository = new UserRepository();
        ProductRepository productRepository = new ProductRepository();
        OrderRepository orderRepository = new OrderRepository();

        this.userService = new UserService(userRepository, paymentRequestRepository);
        this.productService = new ProductService(productRepository);
        this.orderService = new OrderService(orderRepository, productRepository, userRepository);
        this.paymentService = new PaymentService(paymentRequestRepository, userRepository);
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    // === CSV PATHS ===
    private static final String usersCsvPath = "data/users.csv";
    private static final String productsCsvPath = "data/products.csv";
    private static final String paymentRequestCsvPath = "data/paymentRequest.csv";
    private static final String ordersCsvPath = "data/orders.csv";
    private static final String idsCsvPath = "data/ids.csv";

    public static String getUsersCsvPath() {
        return usersCsvPath;
    }

    public static String getProductsCsvPath() {
        return productsCsvPath;
    }

    public static String getPaymentRequestCsvPath() {
        return paymentRequestCsvPath;
    }

    public static String getOrdersCsvPath() {
        return ordersCsvPath;
    }

    public static String getIdsCsvPath() {
        return idsCsvPath;
    }
}
