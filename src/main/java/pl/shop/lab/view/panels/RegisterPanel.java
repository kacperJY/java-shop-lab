package pl.shop.lab.view.panels;

import pl.shop.lab.controller.AppController;
import pl.shop.lab.model.Address;
import pl.shop.lab.model.User;
import pl.shop.lab.model.UserRole;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class RegisterPanel extends JPanel {

    public RegisterPanel(AppController controller) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5);

        // --------------------
        // Pola formularza
        // --------------------
        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField(15);

        JLabel passLabel = new JLabel("Hasło:");
        JPasswordField passField = new JPasswordField(15);

        JLabel fullNameLabel = new JLabel("Imię i nazwisko:");
        JTextField fullNameField = new JTextField(15);

        JLabel cityLabel = new JLabel("Miasto:");
        JTextField cityField = new JTextField(15);

        JLabel streetLabel = new JLabel("Ulica:");
        JTextField streetField = new JTextField(15);

        JLabel postalLabel = new JLabel("Kod pocztowy:");
        JTextField postalField = new JTextField(10);

        JLabel phoneLabel = new JLabel("Telefon:");
        JTextField phoneField = new JTextField(12);

        JButton registerBtn = new JButton("Zarejestruj");
        JButton backBtn = new JButton("Powrót");

        // --------------------
        // Logika przycisków
        // --------------------
        registerBtn.addActionListener(e -> {

            String login = loginField.getText().trim();
            String pass = new String(passField.getPassword()).trim();

            if (login.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Login i hasło są wymagane.");
                return;
            }

            Address address = new Address(
                    fullNameField.getText().trim(),
                    cityField.getText().trim(),
                    streetField.getText().trim(),
                    postalField.getText().trim(),
                    phoneField.getText().trim()
            );

            User newUser = new User(
                    null,
                    login,
                    pass,
                    0.0,
                    UserRole.CLIENT,
                    address
            );

            Optional<User> result = controller.register(newUser);

            if (result.isPresent()) {
                JOptionPane.showMessageDialog(this, "Rejestracja udana!");
                controller.showLogin();
            } else {
                JOptionPane.showMessageDialog(this, "Użytkownik o tym loginie już istnieje.");
            }
        });

        backBtn.addActionListener(e -> controller.showLogin());

        // --------------------
        // Układ formularza
        // --------------------

        int row = 0;
        c.gridy = row++; add(loginLabel, c);
        c.gridy = row++; add(loginField, c);
        c.gridy = row++; add(passLabel, c);
        c.gridy = row++; add(passField, c);
        c.gridy = row++; add(fullNameLabel, c);
        c.gridy = row++; add(fullNameField, c);
        c.gridy = row++; add(cityLabel, c);
        c.gridy = row++; add(cityField, c);
        c.gridy = row++; add(streetLabel, c);
        c.gridy = row++; add(streetField, c);
        c.gridy = row++; add(postalLabel, c);
        c.gridy = row++; add(postalField, c);
        c.gridy = row++; add(phoneLabel, c);
        c.gridy = row++; add(phoneField, c);
        c.gridy = row++; add(registerBtn, c);
        c.gridy = row++; add(backBtn, c);
    }
}

