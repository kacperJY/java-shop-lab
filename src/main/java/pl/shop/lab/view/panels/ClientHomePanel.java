package pl.shop.lab.view.panels;

import pl.shop.lab.controller.AppController;

import javax.swing.*;
import java.awt.*;

public class ClientHomePanel extends JPanel {

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel contentPanel = new JPanel(contentLayout);

    public ClientHomePanel(AppController controller) {

        setLayout(new BorderLayout());

        // ================= LEFT MENU =================
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(6, 1, 5, 5));

        JButton productsBtn = new JButton("Produkty");
        JButton cartBtn = new JButton("Koszyk");
        JButton ordersBtn = new JButton("Moje zamówienia");
        JButton paymentsBtn = new JButton("Doładowanie konta");
        JButton logoutBtn = new JButton("Wyloguj");

        menuPanel.add(productsBtn);
        menuPanel.add(cartBtn);
        menuPanel.add(ordersBtn);
        menuPanel.add(paymentsBtn);
        menuPanel.add(logoutBtn);

        add(menuPanel, BorderLayout.WEST);

        // ================= CONTENT AREA =================
        // Na razie puste placeholdery
        contentPanel.add(new JLabel("Lista produktów (do zaimplementowania)", SwingConstants.CENTER), "products");
        contentPanel.add(new JLabel("Koszyk (do zaimplementowania)", SwingConstants.CENTER), "cart");
        contentPanel.add(new JLabel("Moje zamówienia (do zaimplementowania)", SwingConstants.CENTER), "orders");
        contentPanel.add(new JLabel("Doładowania konta (do zaimplementowania)", SwingConstants.CENTER), "payments");

        add(contentPanel, BorderLayout.CENTER);

        // ================= BUTTON ACTIONS =================
        productsBtn.addActionListener(e -> contentLayout.show(contentPanel, "products"));
        cartBtn.addActionListener(e -> contentLayout.show(contentPanel, "cart"));
        ordersBtn.addActionListener(e -> contentLayout.show(contentPanel, "orders"));
        paymentsBtn.addActionListener(e -> contentLayout.show(contentPanel, "payments"));

        logoutBtn.addActionListener(e -> controller.showLogin());
    }
}

