package pl.shop.lab.repository;

import pl.shop.lab.App;
import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.io.CsvUtils;
import pl.shop.lab.io.IdGenerator;
import pl.shop.lab.model.PaymentRequest;
import pl.shop.lab.model.PaymentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentRequestRepository implements Repository<PaymentRequest> {

    private static final String FILE_PATH = ApplicationContext.getPaymentRequestCsvPath();

    private final List<PaymentRequest> requests = new ArrayList<>();

    public PaymentRequestRepository() {
        requests.addAll(loadAll());
    }

    // ============================================================
    // LOAD
    // ============================================================

    @Override
    public List<PaymentRequest> loadAll() {

        List<PaymentRequest> list = new ArrayList<>();
        List<String[]> rows = CsvUtils.read(FILE_PATH);

        for (String[] r : rows) {

            // CSV: id;userId;amount;status;createdAt
            String id = r[0];
            String userId = r[1];
            double amount = Double.parseDouble(r[2]);
            PaymentStatus status = PaymentStatus.valueOf(r[3]);
            LocalDateTime createdAt = LocalDateTime.parse(r[4]);

            PaymentRequest pr = new PaymentRequest(id, userId, amount, status, createdAt);
            list.add(pr);

        }

        return list;
    }

    // ============================================================
    // SAVE
    // ============================================================

    @Override
    public void saveAll(List<PaymentRequest> items) {

        List<String[]> rows = new ArrayList<>();

        for (PaymentRequest p : items) {
            rows.add(p.toCsv().split(";"));
        }

        CsvUtils.write(FILE_PATH, rows);
    }

    // ============================================================
    // CRUD
    // ============================================================

    @Override
    public List<PaymentRequest> findAll() {
        return new ArrayList<>(requests);
    }

    @Override
    public Optional<PaymentRequest> findById(String id) {
        return requests.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    @Override
    public PaymentRequest insert(PaymentRequest pr) {
        String newId = IdGenerator.nextId("pay");
        pr = new PaymentRequest(
                newId,
                pr.getUserId(),
                pr.getAmount(),
                pr.getStatus(),
                pr.getCreatedAt()
        );
        requests.add(pr);
        saveAll(requests);
        return pr;
    }

    @Override
    public void update(PaymentRequest request) {
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(request.getId())) {
                requests.set(i, request);
                saveAll(requests);
                return;
            }
        }
    }

    // ============================================================
    // ADDITIONAL HELPERS
    // ============================================================

    public List<PaymentRequest> findPending() {
        return requests.stream()
                .filter(r -> r.getStatus() == PaymentStatus.PENDING)
                .toList();
    }

    public List<PaymentRequest> findByUser(String userId) {
        return requests.stream()
                .filter(r -> r.getUserId().equals(userId))
                .toList();
    }
}
