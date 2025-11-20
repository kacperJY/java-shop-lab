package pl.shop.lab.controller;


import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.io.IdGenerator;
import pl.shop.lab.model.Address;
import pl.shop.lab.model.User;
import pl.shop.lab.model.UserRole;
import pl.shop.lab.service.PaymentService;
import pl.shop.lab.service.ProductService;
import pl.shop.lab.service.UserService;
import pl.shop.lab.view.frames.MainFrame;

import javax.swing.*;
import java.util.Optional;


public class AppController {


    private final MainFrame frame;
    private final UserService userService = ApplicationContext.getInstance().getUserService();

    public AppController(MainFrame frame) {
        // INITIALIZE IDS
        IdGenerator.initializeIds();

        this.frame = frame;
    }

    public void showLogin() {
        frame.showPanel("login");

    }

    public void showRegister() {
        frame.showPanel("register");
    }

    public void login(String login, String password) {
        Optional<User> result = userService.login(login, password);

        if (result.isPresent()) {
            ApplicationContext.getInstance().loginUser(result.get());

            if (ApplicationContext.getInstance().getLoggedUser().getUserRole().name().equals("ADMIN")) {
                openAdminPanel();
            } else {
                openClientPanel();
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Błędne dane logowania");
        }
    }

    public Optional<User> register(User user) {
        Optional<User> register = userService.register(user);
        return register;
    }

    private void openClientPanel() {
        // TODO: dodamy ClientHomePanel
        JOptionPane.showMessageDialog(frame, "Zalogowano jako klient");
    }

    private void openAdminPanel() {
        // TODO: dodamy AdminPanel
        JOptionPane.showMessageDialog(frame, "Zalogowano jako admin");
    }

}

