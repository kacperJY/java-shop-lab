package pl.shop.lab.view.panels;

import pl.shop.lab.app.ApplicationContext;
import pl.shop.lab.model.DigitalProduct;
import pl.shop.lab.model.PhysicalProduct;
import pl.shop.lab.service.ProductService;

import javax.swing.*;
import java.awt.*;

public class AddProductPanel extends JPanel {

    private final JComboBox<String> typeCombo;
    private final JTextField nameField;
    private final JTextArea descArea;
    private final JTextField priceField;
    private final JTextField imageField;

    // Physical
    private final JTextField brandField;
    private final JTextField categoryField;
    private final JTextField weightField;
    private final JTextField shippingCostField;

    // Digital
    private final JTextField platformField;
    private final JTextField licenseField;
    private final JTextField downloadUrlField;

    private final CardLayout extraLayout = new CardLayout();
    private final JPanel extraPanel = new JPanel(extraLayout);

    public AddProductPanel() {

        ProductService productService = ApplicationContext.getInstance().getProductService();

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));

        // ================== TYPE ==================
        typeCombo = new JComboBox<>(new String[]{"PHYSICAL", "DIGITAL"});
        form.add(new JLabel("Typ produktu:"));
        form.add(typeCombo);

        // ================== COMMON FIELDS ==================
        nameField = new JTextField();

        descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(descArea);

        priceField = new JTextField();
        imageField = new JTextField();

        form.add(new JLabel("Nazwa:"));
        form.add(nameField);

        form.add(new JLabel("Opis:"));
        form.add(descScroll);

        form.add(new JLabel("Cena:"));
        form.add(priceField);

        form.add(new JLabel("URL obrazka:"));
        form.add(imageField);

        // ================== EXTRA FIELDS ==================

        // ---- PHYSICAL PRODUCT ----
        JPanel physicalPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        physicalPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));

        brandField = new JTextField();
        categoryField = new JTextField();
        weightField = new JTextField();
        shippingCostField = new JTextField();

        physicalPanel.add(new JLabel("Marka:"));
        physicalPanel.add(brandField);

        physicalPanel.add(new JLabel("Kategoria:"));
        physicalPanel.add(categoryField);

        physicalPanel.add(new JLabel("Waga (kg):"));
        physicalPanel.add(weightField);

        physicalPanel.add(new JLabel("Koszt wysyłki:"));
        physicalPanel.add(shippingCostField);

        // ---- DIGITAL PRODUCT ----
        JPanel digitalPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        digitalPanel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));

        platformField = new JTextField();
        licenseField = new JTextField();
        downloadUrlField = new JTextField();

        digitalPanel.add(new JLabel("Platforma:"));
        digitalPanel.add(platformField);

        digitalPanel.add(new JLabel("Klucz licencyjny:"));
        digitalPanel.add(licenseField);

        digitalPanel.add(new JLabel("Link downloadu:"));
        digitalPanel.add(downloadUrlField);

        extraPanel.add(physicalPanel, "PHYSICAL");
        extraPanel.add(digitalPanel, "DIGITAL");

        typeCombo.addActionListener(e ->
                extraLayout.show(extraPanel, typeCombo.getSelectedItem().toString())
        );

        // Default
        extraLayout.show(extraPanel, "PHYSICAL");

        // ================== SUBMIT BUTTON ==================
        JButton saveBtn = new JButton("Dodaj produkt");
        saveBtn.addActionListener(e -> {

            try {
                String type = typeCombo.getSelectedItem().toString();
                String name = nameField.getText();
                String desc = descArea.getText();
                double price = Double.parseDouble(priceField.getText());
                String image = imageField.getText();

                if (type.equals("PHYSICAL")) {
                    String brand = brandField.getText();
                    String category = categoryField.getText();
                    double weight = Double.parseDouble(weightField.getText());
                    double shippingCost = Double.parseDouble(shippingCostField.getText());

                    productService.addPhysical(name, desc, price, image, brand, category, weight, shippingCost);
                    JOptionPane.showMessageDialog(this, "Produkt fizyczny dodany!");

                } else {
                    String platformUrl = platformField.getText();
                    String licence = licenseField.getText();
                    String downloadUrl = downloadUrlField.getText();

                    productService.addDigital(name,desc,price,image,platformUrl,licence,downloadUrl);
                    JOptionPane.showMessageDialog(this, "Produkt cyfrowy dodany!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd: " + ex.getMessage());
            }
        });

        // Layout final
        add(form, BorderLayout.NORTH);
        add(extraPanel, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }
}

