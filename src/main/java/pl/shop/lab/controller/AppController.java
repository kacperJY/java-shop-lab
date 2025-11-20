package pl.shop.lab.controller;


import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.io.IdGenerator;
import pl.shop.lab.model.User;
import pl.shop.lab.service.UserService;
import pl.shop.lab.view.frames.MainFrame;
import pl.shop.lab.view.panels.*;

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
        // Register card and load card view
        frame.getCards().add(new ClientHomePanel(this), "client");
        frame.showPanel("client");
        JOptionPane.showMessageDialog(frame, "Zalogowano jako klient");
    }

    private void openAdminPanel() {
        frame.getCards().add(new AdminHomePanel(this), "admin");
        frame.showPanel("admin");
        JOptionPane.showMessageDialog(frame, "Zalogowano jako admin");
    }

}

