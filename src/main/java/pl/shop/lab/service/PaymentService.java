package pl.shop.lab.service;

import pl.shop.lab.model.PaymentRequest;
import pl.shop.lab.model.PaymentStatus;
import pl.shop.lab.model.User;
import pl.shop.lab.repository.PaymentRequestRepository;
import pl.shop.lab.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PaymentService {

    private final PaymentRequestRepository paymentRepo;
    private final UserRepository userRepo;

    public PaymentService(PaymentRequestRepository paymentRepo,
                          UserRepository userRepo) {
        this.paymentRepo = paymentRepo;
        this.userRepo = userRepo;
    }

    // ============================================
    // CLIENT — create payment request
    // ============================================

    public PaymentRequest createRequest(String userId, double amount) {
        PaymentRequest pr = new PaymentRequest(
                null,
                userId,
                amount,
                PaymentStatus.PENDING,
                LocalDateTime.now()
        );
        return paymentRepo.insert(pr);
    }

    // ============================================
    // ADMIN — approve
    // ============================================

    public boolean approve(String requestId) {

        Optional<PaymentRequest> prOpt = paymentRepo.findById(requestId);
        if (prOpt.isEmpty()) return false;

        PaymentRequest pr = prOpt.get();

        if (pr.getStatus() != PaymentStatus.PENDING) return false;

        Optional<User> userOpt = userRepo.findById(pr.getUserId());
        if (userOpt.isEmpty()) return false;

        User u = userOpt.get();

        // add saldo
        u.setBalance(u.getBalance() + pr.getAmount());
        userRepo.update(u);

        // update request
        pr.setStatus(PaymentStatus.APPROVED);
        paymentRepo.update(pr);

        return true;
    }

    // ============================================
    // ADMIN — reject
    // ============================================

    public boolean reject(String requestId) {

        Optional<PaymentRequest> prOpt = paymentRepo.findById(requestId);
        if (prOpt.isEmpty()) return false;

        PaymentRequest pr = prOpt.get();

        if (pr.getStatus() != PaymentStatus.PENDING) return false;

        pr.setStatus(PaymentStatus.REJECTED);
        paymentRepo.update(pr);

        return true;
    }

    // ============================================
    // GETTERS
    // ============================================

    public List<PaymentRequest> getAll() {
        return paymentRepo.findAll();
    }

    public List<PaymentRequest> getPending() {
        return paymentRepo.findPending();
    }

    public List<PaymentRequest> getUserRequests(String userId) {
        return paymentRepo.findByUser(userId);
    }
}

