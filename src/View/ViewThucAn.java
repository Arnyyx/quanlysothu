package View;

import Model.ModelThucAn;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import Controller.Controllerthucan;

public class ViewThucAn extends JFrame {

    private JButton chooseImageButton, backButton, searchButton, addButton, updateButton, deleteButton, importButton, statisticsButton;
    private JLabel idLabel, nameLabel, typeLabel, searchLable, animalIdLabel, quantityLabel, expiryLabel, supplierLabel;
    private JTextField idField, nameField, typeField, searchField, animalIdField, quantityField, expiryField, supplierIdField;
    private JTable table;
    private JScrollPane scrollPane;
    public DefaultTableModel model = new DefaultTableModel();

    public ViewThucAn() {
        setSize(1000, 700);
        new Menu(this);
        setTitle("Food Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchField = new JTextField(20);
        searchLable = new JLabel("Search:");
        idLabel = new JLabel("ID:");
        idField = new JTextField(20);
        idField.setEditable(false);

        nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        typeLabel = new JLabel("Type:");
        typeField = new JTextField(20);

        animalIdLabel = new JLabel("Animal ID:");
        animalIdField = new JTextField(20);

        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(20);

        expiryLabel = new JLabel("Expiry Date:");
        expiryField = new JTextField(20);

        supplierLabel = new JLabel("Supplier ID:");
        supplierIdField = new JTextField(20);

        searchButton = new JButton("Search");
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Quay lại");
        importButton = new JButton("Import");
        statisticsButton = new JButton("Statistics");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.add(searchLable);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));

//        JPanel imagePanel = new JPanel(new FlowLayout());
//        imagePanel.add(new JLabel("Image:"));
//        imagePanel.add(imageField);
//        imagePanel.add(chooseImageButton);
//        
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(typeLabel);
        inputPanel.add(typeField);

        inputPanel.add(animalIdLabel);
        inputPanel.add(animalIdField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(expiryLabel);
        inputPanel.add(expiryField);
        inputPanel.add(supplierLabel);
        inputPanel.add(supplierIdField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        buttonPanel.add(importButton);
        buttonPanel.add(statisticsButton);
        buttonPanel.add(backButton);

        JLabel titleLabel = new JLabel("Food Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(titleLabel);
        topPanel.add(searchPanel);
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm danh sách vào layout
        add(listPanel, BorderLayout.WEST);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public DefaultTableModel getModel() {
        return model;
    }

    public JTable getTable() {
        return table;
    }

    public JButton getAddButton() {
        System.out.print('1');
        return addButton;
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getImportButton() {
        return importButton;
    }

    public JButton getStatisticsButton() {
        return statisticsButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JTextField getIdField() {

        return idField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getTypeField() {
        return typeField;
    }

    public JTextField getAnimalIdField() {
        return animalIdField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JTextField getExpiryField() {
        return expiryField;
    }

    public JTextField getSupplierField() {
        return supplierIdField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

}
