/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Duong
 */
public class ViewMoiTruong extends JFrame {

    private JLabel label = new JLabel("Quản lý Môi Trường", SwingConstants.CENTER);

    private String[] lableStrings = {"Mã", "Tên chuồng","Tên nhân viên","Ngày tháng", "Trạng thái", "Mô tả chi tiết"};
    private String[] statestrings = {"Tốt", "Bình thường", "Tệ"};
    
    private JLabel lbID = new JLabel(lableStrings[0]);
    private JLabel lbIDHabitat = new JLabel(lableStrings[1]);
    private JLabel lbStaff = new JLabel(lableStrings[2]);
    private JLabel lbDate = new JLabel(lableStrings[3]);
    private JLabel lbState = new JLabel(lableStrings[4]);
    private JLabel lbDescription = new JLabel(lableStrings[5]);

    private JTextField tfID = new JTextField(20);
    private JTextField tfIDHabitat = new JTextField(20);
    private JComboBox<String> tfStaff = new JComboBox();
    private JDateChooser tfDate = new JDateChooser();
    private JComboBox<String> tfState = new JComboBox(statestrings);
    private JTextField tfDescription = new JTextField(20);
    private JTextField tfSearch = new JTextField(20);

    private JButton btnAdd = new JButton("Thêm");
    private JButton btnDelete = new JButton("Xoá");
    private JButton btnSave = new JButton("Lưu");
    private JButton btnSearch = new JButton("Tìm kiếm");
    private JButton btnInput = new JButton("Nhập dữ liệu");
    private JButton btnOutPut = new JButton("Xuất dữ liệu");
    private JButton btnLoad = new JButton("Làm mới");

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

    public ViewMoiTruong() {
        setTitle("Quản lý môi trường");
        setSize(800, 600);

        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel chứa thông tin động vật
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
        inputPanel.add(lbIDHabitat, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIDHabitat, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lbStaff, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfStaff, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lbDate, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(lbState, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfState, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(lbDescription, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfDescription, gbc);

        // Panel chứa button
        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelSouth.add(btnAdd);
        buttonPanelSouth.add(btnDelete);
        buttonPanelSouth.add(btnSave);
        buttonPanelSouth.add(btnSearch);
        buttonPanelSouth.add(tfSearch);

        JPanel buttonPanelNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelNorth.add(btnLoad);
        buttonPanelNorth.add(btnInput);
        buttonPanelNorth.add(btnOutPut);

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
        lbIDHabitat.setFont(font);
        lbState.setFont(font);
        lbDescription.setFont(font);
        tfID.setFont(font);
        tfIDHabitat.setFont(font);
        tfStaff.setFont(font);
        tfDate.setFont(font);
        tfState.setFont(font);
        tfDescription.setFont(font);
        tfDescription.setPreferredSize(new Dimension(200, 100));
        tfSearch.setFont(font);
        btnAdd.setFont(font);
        btnDelete.setFont(font);
        btnSave.setFont(font);
        btnSearch.setFont(font);
        btnInput.setFont(font);
        btnOutPut.setFont(font);
        btnLoad.setFont(font);

        tableModel.addColumn(lableStrings[0]);
        tableModel.addColumn(lableStrings[1]);
        tableModel.addColumn(lableStrings[2]);
        tableModel.addColumn(lableStrings[3]);
        tableModel.addColumn(lableStrings[4]);
        tableModel.addColumn(lableStrings[5]);

        setLocationRelativeTo(null);
        setVisible(true);

        tfID.setEditable(false);
        tfIDHabitat.setEditable(false);
    }

    public JDateChooser getTfDate() {
        return tfDate;
    }

    public void setTfDate(JDateChooser tfDate) {
        this.tfDate = tfDate;
    }

    public String[] getStatestrings() {
        return statestrings;
    }

    public void setStatestrings(String[] statestrings) {
        this.statestrings = statestrings;
    }

    public JLabel getLbStaff() {
        return lbStaff;
    }

    public void setLbStaff(JLabel lbStaff) {
        this.lbStaff = lbStaff;
    }

    public JLabel getLbDate() {
        return lbDate;
    }

    public void setLbDate(JLabel lbDate) {
        this.lbDate = lbDate;
    }

    public JComboBox<String> getTfStaff() {
        return tfStaff;
    }

    public void setTfStaff(JComboBox<String> tfStaff) {
        this.tfStaff = tfStaff;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public String[] getLableStrings() {
        return lableStrings;
    }

    public void setLableStrings(String[] lableStrings) {
        this.lableStrings = lableStrings;
    }

    public JLabel getLbID() {
        return lbID;
    }

    public void setLbID(JLabel lbID) {
        this.lbID = lbID;
    }

    public JLabel getLbIDHabitat() {
        return lbIDHabitat;
    }

    public void setLbIDHabitat(JLabel lbIDHabitat) {
        this.lbIDHabitat = lbIDHabitat;
    }

    public JLabel getLbState() {
        return lbState;
    }

    public void setLbState(JLabel lbState) {
        this.lbState = lbState;
    }

    public JLabel getLbDescription() {
        return lbDescription;
    }

    public void setLbDescription(JLabel lbDescription) {
        this.lbDescription = lbDescription;
    }

    public JTextField getTfID() {
        return tfID;
    }

    public void setTfID(JTextField tfID) {
        this.tfID = tfID;
    }

    public JTextField getTfIDHabitat() {
        return tfIDHabitat;
    }

    public void setTfIDHabitat(JTextField tfIDHabitat) {
        this.tfIDHabitat = tfIDHabitat;
    }

    public JComboBox<String> getTfState() {
        return tfState;
    }

    public void setTfState(JComboBox<String> tfState) {
        this.tfState = tfState;
    }

    public JTextField getTfDescription() {
        return tfDescription;
    }

    public void setTfDescription(JTextField tfDescription) {
        this.tfDescription = tfDescription;
    }

    public JTextField getTfSearch() {
        return tfSearch;
    }

    public void setTfSearch(JTextField tfSearch) {
        this.tfSearch = tfSearch;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(JButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
    }

    public JButton getBtnSearch() {
        return btnSearch;
    }

    public void setBtnSearch(JButton btnSearch) {
        this.btnSearch = btnSearch;
    }

    public JButton getBtnInput() {
        return btnInput;
    }

    public void setBtnInput(JButton btnInput) {
        this.btnInput = btnInput;
    }

    public JButton getBtnOutPut() {
        return btnOutPut;
    }

    public void setBtnOutPut(JButton btnOutPut) {
        this.btnOutPut = btnOutPut;
    }

    public JButton getBtnLoad() {
        return btnLoad;
    }

    public void setBtnLoad(JButton btnLoad) {
        this.btnLoad = btnLoad;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }

    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public void Clear() {
        tfID.setText("");
        tfStaff.setSelectedItem(null);
        tfDescription.setText("");
        tfSearch.setText("");
        tfDate.setDate(null);
    }
    
}
