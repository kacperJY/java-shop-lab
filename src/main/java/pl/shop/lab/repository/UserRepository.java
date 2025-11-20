package pl.shop.lab.repository;

import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.io.CsvUtils;
import pl.shop.lab.io.IdGenerator;
import pl.shop.lab.model.Address;
import pl.shop.lab.model.User;
import pl.shop.lab.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements Repository<User> {

    private static final String FILE_PATH = ApplicationContext.getUsersCsvPath();

    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // wczytanie danych z CSV do pamięci
        users.addAll(loadAll());
    }

    // =========================
    // implementacja interfejsu
    // =========================

    @Override
    public List<User> loadAll() {
        List<User> result = new ArrayList<>();
        List<String[]> rows = CsvUtils.read(FILE_PATH);

        for (String[] r : rows) {
            // format: id;login;password;balance;role;fullName;city;street;postalCode;phone
            String id = r[0];
            String login = r[1];
            String password = r[2];
            double balance = Double.parseDouble(r[3]);
            UserRole role = UserRole.valueOf(r[4]);

            Address address = new Address(
                    r[5], // fullName
                    r[6], // city
                    r[7], // street
                    r[8], // postalCode
                    r[9]  // phone
            );

            User user = new User(id, login, password, balance, role, address);
            result.add(user);

        }

        return result;
    }

    @Override
    public void saveAll(List<User> items) {
        List<String[]> rows = new ArrayList<>();
        for (User u : items) {
            rows.add(u.toCsv().split(";"));
        }
        CsvUtils.write(FILE_PATH, rows);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public Optional<User> findById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    @Override
    public User insert(User user) {
        String newId = IdGenerator.nextId("usr");
        user.setId(newId);
        users.add(user);
        saveAll(users);
        return user;
    }

    @Override
    public void update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                saveAll(users);
                return;
            }
        }
        // opcjonalnie: można rzucić wyjątek, jeśli nie znaleziono
    }

    // =========================
    // metody specyficzne dla User
    // =========================

    public Optional<User> findByLogin(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }
}
