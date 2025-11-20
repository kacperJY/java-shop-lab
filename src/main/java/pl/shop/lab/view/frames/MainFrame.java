package pl.shop.lab.view.frames;

import javax.swing.*;
import java.awt.*;

import pl.shop.lab.controller.AppController;
import pl.shop.lab.view.panels.LoginPanel;
import pl.shop.lab.view.panels.RegisterPanel;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);

    public MainFrame() {
        setTitle("Shop Application");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // tworzymy kontroler
        AppController controller = new AppController(this);

        // rejestrujemy panele
        cards.add(new LoginPanel(controller), "login");
        cards.add(new RegisterPanel(controller), "register");

        add(cards);
        showPanel("login");
    }

    public void showPanel(String name) {
        cardLayout.show(cards, name);
    }

    public JPanel getCards() {
        return cards;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
