package pl.shop.lab.view.panels;

import pl.shop.lab.controller.AppController;

import javax.swing.*;
import java.awt.*;

public class AdminHomePanel extends JPanel {

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);

    public AdminHomePanel(AppController controller) {

        setLayout(new BorderLayout());

        // ================= LEFT MENU =================
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 5, 5));

        JButton productsBtn = new JButton("Lista produktów");
        JButton addProductBtn = new JButton("Dodaj produkt");
        JButton ordersBtn = new JButton("Zamówienia");
        JButton paymentsBtn = new JButton("Oczekujące doładowania");
        JButton usersBtn = new JButton("Użytkownicy");
        JButton logoutBtn = new JButton("Wyloguj");

        menuPanel.add(productsBtn);
        menuPanel.add(addProductBtn);
        menuPanel.add(ordersBtn);
        menuPanel.add(paymentsBtn);
        menuPanel.add(usersBtn);
        menuPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.WEST);

        // ================= CONTENT AREA =================
        // Na razie placeholdery
        contentPanel.add(new JLabel("Lista produktów (admin)", SwingConstants.CENTER), "products");
        contentPanel.add(new JLabel("Dodawanie produktu", SwingConstants.CENTER), "addProduct");
        contentPanel.add(new JLabel("Zamówienia klientów", SwingConstants.CENTER), "orders");
        contentPanel.add(new JLabel("Oczekujące płatności", SwingConstants.CENTER), "payments");
        contentPanel.add(new JLabel("Użytkownicy", SwingConstants.CENTER), "users");

        add(contentPanel, BorderLayout.CENTER);

        // ================= BUTTON ACTIONS =================
        productsBtn.addActionListener(e -> contentLayout.show(contentPanel, "products"));
        addProductBtn.addActionListener(e -> contentLayout.show(contentPanel, "addProduct"));
        ordersBtn.addActionListener(e -> contentLayout.show(contentPanel, "orders"));
        paymentsBtn.addActionListener(e -> contentLayout.show(contentPanel, "payments"));
        usersBtn.addActionListener(e -> contentLayout.show(contentPanel, "users"));

        logoutBtn.addActionListener(e -> controller.showLogin());
    }
}

