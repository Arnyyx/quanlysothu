/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Controller.ControllerLoaiVe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duong
 */
public class ViewLoaiVe extends JFrame {

    ControllerLoaiVe c = new ControllerLoaiVe();

    private JLabel label = new JLabel("Quản lý Loại Vé", SwingConstants.CENTER);

    private String[] lableStrings = {"Mã", "Tên", "Khu Vực"};

    private JLabel lbID = new JLabel(lableStrings[0]);
    private JLabel lbName = new JLabel(lableStrings[1]);
    private JLabel lbArea = new JLabel(lableStrings[2]);

    // Text field
    private JTextField tfID = new JTextField(20);
    private JTextField tfName = new JTextField();
    private JTextField tfArea = new JTextField(20);
    // Button
    private JButton btnAdd = new JButton("Thêm");
    private JButton btnDelete = new JButton("Xoá");
    private JButton btnSave = new JButton("Lưu");
    private JButton btnSearch = new JButton("Tìm kiếm");
    private JButton btnLoad = new JButton("Làm mới");

    private JPanel pnImg = new JPanel();

    // Table
    private JTable table = new JTable();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            //all cells false
            return false;
        }
    };

    public ViewLoaiVe() {
        setTitle("Quản lý Loại Vé");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Menu(this);
        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel chứa thông tin loại vé
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Thêm label "Quản lý chuồng trại" vào panel, căn giữa
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Đặt chiều rộng của label bằng 2 cột
        gbc.anchor = GridBagConstraints.CENTER; // Đặt anchor để label căn giữa
        inputPanel.add(label, gbc);

        // Reset các giá trị của ràng buộc
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        // Thêm các label và text field vào panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lbID, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lbName, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lbArea, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfArea, gbc);

        // Panel chứa button
        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelSouth.add(btnAdd);
        buttonPanelSouth.add(btnDelete);
        buttonPanelSouth.add(btnSave);
        buttonPanelSouth.add(btnSearch);

        JPanel buttonPanelNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelNorth.add(btnLoad);

        // Thêm các panel vào split pane
        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(inputPanel);
        splitPane.setResizeWeight(0.6);

        // Sắp xếp split pane
        add(buttonPanelNorth, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanelSouth, BorderLayout.SOUTH);

        // Font chữ
        Font font = new Font("Arial", Font.PLAIN, 14);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        lbID.setFont(font);
        lbName.setFont(font);
        lbArea.setFont(font);
        tfID.setFont(font);
        tfName.setFont(font);
        tfArea.setFont(font);
        btnAdd.setFont(font);
        btnDelete.setFont(font);
        btnSave.setFont(font);
        btnSearch.setFont(font);
        btnLoad.setFont(font);

        tfID.disable();
        tableModel.addColumn(lableStrings[0]);
        tableModel.addColumn(lableStrings[1]);
        tableModel.addColumn(lableStrings[2]);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void Clear() {
        tfArea.setText("");
        tfID.setText("");
        tfName.setText("");

    }

    public void addLoadButtonListener(ActionListener listener) {
        btnLoad.addActionListener(listener);
    }

    public void addAddButtonListener(ActionListener listener) {
        btnAdd.addActionListener(listener);
    }

    public void addSaveButtonListener(ActionListener listener) {
        btnSave.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        btnDelete.addActionListener(listener);
    }

    public void addSearchButtonListener(ActionListener listener) {
        btnSearch.addActionListener(listener);
    }

    public void addTableRowClickListener(MouseListener listener) {
        table.addMouseListener(listener);
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        table.getSelectionModel().addListSelectionListener(listener);
    }

    public JLabel getLabel() {
        return label;
    }

    public String[] getLableStrings() {
        return lableStrings;
    }

    public JLabel getLbID() {
        return lbID;
    }

    public JLabel getLbName() {
        return lbName;
    }

    public JLabel getLbArea() {
        return lbArea;
    }

    public JTextField getTfID() {
        return tfID;
    }

    public JTextField getTfName() {
        return tfName;
    }

    public JTextField getTfArea() {
        return tfArea;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public JButton getBtnTimKiem() {
        return btnSearch;
    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public JPanel getPnImg() {
        return pnImg;
    }

    public JTable getTable() {
        return table;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}
