/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class ViewNCC extends JFrame {

    private JButton backButton, addButton, updateButton, deleteButton, searchButton, importButton;
    private JLabel searchLable, idLabel, nameLabel, foodTypeLabel, locationLabel;
    private JTextField searchField, idField, nameField, foodTypeField, locationField;
    private JTable table;
    private JScrollPane scrollPane;
    public DefaultTableModel model = new DefaultTableModel();

    public ViewNCC() {
        new Menu(this);
        setTitle("Supplier Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchField = new JTextField(20);
        searchLable = new JLabel("Search:");
        idLabel = new JLabel("ID NCC:");
        idField = new JTextField(20);
        idField.setEditable(false);
        nameLabel = new JLabel("Tên NCC:");
        nameField = new JTextField(20);

        foodTypeLabel = new JLabel("Loại thức ăn:");
        foodTypeField = new JTextField(20);

        locationLabel = new JLabel("Vị trí:");
        locationField = new JTextField(20);

        addButton = new JButton("Thêm");
        updateButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        searchButton = new JButton("Tìm kiếm");

        importButton = new JButton("Xuất Excel");
        backButton = new JButton("Quay lại");
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchPanel.add(searchLable);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(foodTypeLabel);
        inputPanel.add(foodTypeField);
        inputPanel.add(locationLabel);
        inputPanel.add(locationField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(importButton);
        buttonPanel.add(backButton);
        JLabel titleLabel = new JLabel("QUẢN LÝ NHÀ CUNG CẤP");
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
        model.addColumn("ID Nhà cung cấp");
        model.addColumn("Tên nhà cung cấp");
        model.addColumn("Loại thức ăn");
        model.addColumn("Vị Trí");

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);

        // Thêm danh sách vào layout
        add(listPanel, BorderLayout.WEST);

//        pack();
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

    public JButton getBackButton() {
        return backButton;
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
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

    public JButton getExportButton() {
        return importButton;
    }

    public void addExportButtonListener(ActionListener listener) {
        importButton.addActionListener(listener);
    }

    public JTextField getIdField() {

        return idField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getFoodTypeField() {
        return foodTypeField;
    }

    public JTextField getLocationField() {
        return locationField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void clear() {
        getIdField().setText("");
        getNameField().setText("");
        getFoodTypeField().setText("");
        getLocationField().setText("");

//            getSupplierField().setSelectedItem(1);
    }
}
