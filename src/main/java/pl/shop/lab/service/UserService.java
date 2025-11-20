package pl.shop.lab.service;

import pl.shop.lab.model.PaymentRequest;
import pl.shop.lab.model.PaymentStatus;
import pl.shop.lab.model.User;
import pl.shop.lab.repository.PaymentRequestRepository;
import pl.shop.lab.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;
    private final PaymentRequestRepository paymentRequestRepository;

    public UserService(UserRepository userRepository,
                       PaymentRequestRepository paymentRequestRepository) {
        this.userRepository = userRepository;
        this.paymentRequestRepository = paymentRequestRepository;
    }

    // =========================================
    // LOGOWANIE
    // =========================================

    public Optional<User> login(String login, String password) {
        return userRepository
                .findAll()
                .stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .filter(u -> u.getPassword().equals(password))
                .findFirst();
    }

    // =========================================
    // REJESTRACJA
    // =========================================

    public Optional<User> register(User user) {

        String login = user.getLogin();
        String password = user.getPassword();

        // login już istnieje?
        boolean exists = userRepository
                .findAll()
                .stream()
                .anyMatch(u -> u.getLogin().equalsIgnoreCase(login));

        if (exists) {
            return Optional.empty();
        }

        // ustaw dane początkowe
        user.setLogin(login);
        user.setPassword(password);
        user.setBalance(0.0);

        User created = userRepository.insert(user);
        return Optional.of(created);
    }

    // =========================================
    // POBIERANIE UŻYTKOWNIKA
    // =========================================

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByLogin(String login) {
        return userRepository
                .findAll()
                .stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    // =========================================
    // SALDO / DOŁADOWANIA
    // =========================================

    public double getBalance(String userId) {
        return userRepository.findById(userId)
                .map(User::getBalance)
                .orElse(0.0);
    }

    // tworzy request o doładowanie salda (CLIENT)
    public PaymentRequest createTopUpRequest(String userId, double amount) {
        PaymentRequest pr = new PaymentRequest(
                null,
                userId,
                amount,
                PaymentStatus.PENDING,
                LocalDateTime.now()
        );

        return paymentRequestRepository.insert(pr);
    }

    // aktualizacja salda (wywoływana tylko przy pozytywnej autoryzacji)
    private void addBalance(User user, double amount) {
        user.setBalance(user.getBalance() + amount);
        userRepository.update(user);
    }

    // =========================================
    // ADMIN: AKCEPTACJA/Odrzucanie doładowań
    // =========================================

    public boolean approveTopUp(String paymentRequestId) {
        Optional<PaymentRequest> prOpt = paymentRequestRepository.findById(paymentRequestId);
        if (prOpt.isEmpty()) {
            return false;
        }

        PaymentRequest pr = prOpt.get();
        if (pr.getStatus() != PaymentStatus.PENDING) {
            return false;
        }

        Optional<User> userOpt = userRepository.findById(pr.getUserId());
        if (userOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();

        // dodaj saldo
        addBalance(user, pr.getAmount());

        // aktualizuj request
        pr.setStatus(PaymentStatus.APPROVED);
        paymentRequestRepository.update(pr);

        return true;
    }

    public boolean rejectTopUp(String paymentRequestId) {
        Optional<PaymentRequest> prOpt = paymentRequestRepository.findById(paymentRequestId);
        if (prOpt.isEmpty()) {
            return false;
        }

        PaymentRequest pr = prOpt.get();
        if (pr.getStatus() != PaymentStatus.PENDING) {
            return false;
        }

        pr.setStatus(PaymentStatus.REJECTED);
        paymentRequestRepository.update(pr);

        return true;
    }

    // =========================================
    // POBIERANIE I FILTROWANIE PAYMENT REQUESTS
    // =========================================

    public List<PaymentRequest> getUserRequests(String userId) {
        return paymentRequestRepository.findAll()
                .stream()
                .filter(r -> r.getUserId().equals(userId))
                .toList();
    }

    public List<PaymentRequest> getWaitingRequests() {
        return paymentRequestRepository.findAll()
                .stream()
                .filter(r -> r.getStatus() == PaymentStatus.PENDING)
                .toList();
    }

    // =========================================
    // ROLE
    // =========================================

    public boolean isAdmin(User user) {
        return user.getUserRole().name().equals("ADMIN");
    }

    public boolean isClient(User user) {
        return user.getUserRole().name().equals("CLIENT");
    }
}
