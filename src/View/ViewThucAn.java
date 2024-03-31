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

    private JButton backButton, searchButton, addButton, updateButton, deleteButton, importButton, statisticsButton;
    private JLabel idLabel, nameLabel, typeLabel, searchLable, animalIdLabel, quantityLabel, expiryLabel, supplierLabel;
    private JTextField idField, nameField, typeField, searchField, quantityField, expiryField;
    private JTable table;
    private JComboBox supplierIdField, animalIdField;

    private JScrollPane scrollPane;
    public DefaultTableModel model = new DefaultTableModel();

    public ViewThucAn() {
        new Menu(this);
        setTitle("Food Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchField = new JTextField(20);
        searchLable = new JLabel("Tìm kiếm:");
        idLabel = new JLabel("Id thức ăn:");
        idField = new JTextField(20);
        idField.setEditable(false);
        nameLabel = new JLabel("Tên thức ăn:");
        nameField = new JTextField(20);
        typeLabel = new JLabel("Loại thức ăn:");
        typeField = new JTextField(20);
        animalIdLabel = new JLabel("Id động vật:");
        animalIdField = new JComboBox();
        animalIdField.setEditable(true);
        quantityLabel = new JLabel("Số lượng:");
        quantityField = new JTextField(20);

        expiryLabel = new JLabel("HSD:");
        expiryField = new JTextField(20);

        supplierLabel = new JLabel("Id NCC:");
        supplierIdField = new JComboBox();
        supplierIdField.setEditable(true);
        searchButton = new JButton("Tìm kiếm");
        addButton = new JButton("Thêm");
        updateButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        backButton = new JButton("Quay lại");
        importButton = new JButton("Xuất Excel");
        statisticsButton = new JButton("Thống kê");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchLable);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
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

        JLabel titleLabel = new JLabel("QUẢN LÝ THỨC ĂN");
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

        model.addColumn("ID Thuc An");
        model.addColumn("Ten Thuc An");
        model.addColumn("Loai Thuc An");
        model.addColumn("ID Dong Vat");
        model.addColumn("So Luong");
        model.addColumn("Han Thuc An");
        model.addColumn("Id NCC");
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm danh sách vào layout
        add(listPanel, BorderLayout.WEST);

        pack();
        setSize(1000, 700);
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

        return addButton;
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public JButton getUpdateButton() {
        return updateButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public JButton getImportButton() {
        return importButton;
    }

    public void addExportButtonListener(ActionListener listener) {
        importButton.addActionListener(listener);
    }

    public JButton getStatisticsButton() {
        return statisticsButton;
    }

    public void addStatisticButtonListener(ActionListener listener) {
        statisticsButton.addActionListener(listener);
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
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

    public JComboBox getAnimalIdField() {
        return animalIdField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JTextField getExpiryField() {
        return expiryField;
    }

    public JComboBox getSupplierField() {
        return supplierIdField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void clear() {
        getNameField().setText("");
        getTypeField().setText("");
        getAnimalIdField().setSelectedItem("");
        getQuantityField().setText("");
        getExpiryField().setText("");
        getSupplierField().setSelectedItem(1);
    }

    public void displayStatistics(int totalQuantity) {

    }
}
