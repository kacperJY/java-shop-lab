package pl.shop.lab.view.panels;

import pl.shop.lab.controller.AppController;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel(AppController controller) {

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginField = new JTextField(15);

        JLabel passLabel = new JLabel("Hasło:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginBtn = new JButton("Zaloguj");
        JButton registerBtn = new JButton("Rejestracja");

        loginBtn.addActionListener(e ->
                controller.login(loginField.getText(), new String(passField.getPassword()))
        );

        registerBtn.addActionListener(e ->
                controller.showRegister()
        );

        c.gridy = 0; add(loginLabel, c);
        c.gridy = 1; add(loginField, c);
        c.gridy = 2; add(passLabel, c);
        c.gridy = 3; add(passField, c);
        c.gridy = 4; add(loginBtn, c);
        c.gridy = 5; add(registerBtn, c);
    }
}
