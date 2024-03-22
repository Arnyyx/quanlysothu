package View;

import Controller.ControllerVe;
import Utils.IntegerDocumentFilter;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class ViewVe extends JFrame {

    private JLabel label = new JLabel("QUẢN LÝ VÉ", SwingConstants.CENTER);
    private JLabel labelSLVe = new JLabel();

    private String[] lableStrings = {"ID Vé", "ID Nhân viên", "Tên vé", "Loại vé", "Giá vé", "Số lượng"};
    private String[] loaiVeString = {"Người lớn", "Trẻ em", "Quân nhân", "Người khuyết tật"};

    private JLabel lbID = new JLabel(lableStrings[0]);
    private JLabel lbIDNhanVien = new JLabel(lableStrings[1]);
    private JLabel lbTen = new JLabel(lableStrings[2]);
    private JLabel lbLoai = new JLabel(lableStrings[3]);
    private JLabel lbGia = new JLabel(lableStrings[4]);
    private JLabel lbSoLuong = new JLabel(lableStrings[5]);

    // Text field
    private JTextField tfID = new JTextField(20);
    private JComboBox<String> tfIDNhanVien = new JComboBox();
    private JTextField tfTen = new JTextField(20);
    private JComboBox<String> tfLoai = new JComboBox(loaiVeString);
    private JTextField tfGia = new JTextField(20);
    private JTextField tfSoLuong = new JTextField(20);
    private JTextField tfTimKiem = new JTextField(20);
    // Button
    private JButton btnAdd = new JButton("Thêm");
    private JButton btnDelete = new JButton("Xoá");
    private JButton btnSave = new JButton("Lưu");
    private JButton btnSearch = new JButton("Tìm kiếm");
    private JButton btnNhap = new JButton("Nhập dữ liệu");
    private JButton btnXuat = new JButton("Xuất dữ liệu");
    private JButton btnLoad = new JButton("Làm mới");

    // Table
    private JTable table = new JTable();
    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ViewVe() {
        setTitle("Quản lý động vật");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Menu(this);

        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel chứa thông tin động vật
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        table.getTableHeader().setReorderingAllowed(false);
        tfID.setEditable(false);
        tfIDNhanVien.setEditable(true);
//        tfLoai.setEditable(true);
        ((AbstractDocument) tfGia.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        ((AbstractDocument) tfSoLuong.getDocument()).setDocumentFilter(new IntegerDocumentFilter());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lbID, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lbIDNhanVien, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfIDNhanVien, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lbTen, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lbLoai, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfLoai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(lbGia, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfGia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(lbSoLuong, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfSoLuong, gbc);

        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelSouth.add(labelSLVe);
        buttonPanelSouth.add(btnAdd);
        buttonPanelSouth.add(btnDelete);
        buttonPanelSouth.add(btnSave);
        buttonPanelSouth.add(btnSearch);
        buttonPanelSouth.add(tfTimKiem);

        JPanel buttonPanelNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelNorth.add(btnLoad);
        buttonPanelNorth.add(btnNhap);
        buttonPanelNorth.add(btnXuat);

        // Thêm các panel vào split pane
        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(inputPanel);
        splitPane.setResizeWeight(0.6);

        // Sắp xếp split pane
        add(buttonPanelNorth, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanelSouth, BorderLayout.SOUTH);

//         Font chữ
//        Font font = new Font("Arial", Font.PLAIN, 14);
        Font font = UIManager.getFont("large.font");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        lbID.setFont(font);
        lbIDNhanVien.setFont(font);
        lbTen.setFont(font);
        lbLoai.setFont(font);
        lbGia.setFont(font);
        lbSoLuong.setFont(font);

        tfID.setFont(font);
        tfTen.setFont(font);
        tfLoai.setFont(font);
        tfTimKiem.setFont(font);
        btnAdd.setFont(font);
        btnDelete.setFont(font);
        btnSave.setFont(font);
        btnSearch.setFont(font);
        btnNhap.setFont(font);
        btnXuat.setFont(font);
        btnLoad.setFont(font);
        labelSLVe.setFont(font);

        tableModel.addColumn(lableStrings[0]);
        tableModel.addColumn(lableStrings[1]);
        tableModel.addColumn(lableStrings[2]);
        tableModel.addColumn(lableStrings[3]);
        tableModel.addColumn(lableStrings[4]);
        tableModel.addColumn(lableStrings[5]);
        table.setFont(font);

        setLocationRelativeTo(null);
        setVisible(true);
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

    public JLabel getLbTen() {
        return lbTen;
    }

    public JLabel getLbLoai() {
        return lbLoai;
    }

    public JTextField getTfID() {
        return tfID;
    }

    public JTextField getTfTen() {
        return tfTen;
    }

    public JComboBox<String> getTfLoai() {
        return tfLoai;
    }

    public JTextField getTfTimKiem() {
        return tfTimKiem;
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

    public JButton getBtnNhap() {
        return btnNhap;
    }

    public JButton getBtnXuat() {
        return btnXuat;
    }

    public JButton getBtnLoad() {
        return btnLoad;
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

    public JLabel getLabelSLVe() {
        return labelSLVe;
    }

    public void setLabelSLVe(String text) {
        this.labelSLVe.setText(text);
    }

    public JComboBox<String> getTfIDNhanVien() {
        return tfIDNhanVien;
    }

    public void setTfIDNhanVien(JComboBox<String> tfIDNhanVien) {
        this.tfIDNhanVien = tfIDNhanVien;
    }

    public JTextField getTfGia() {
        return tfGia;
    }

    public void setTfGia(JTextField tfGia) {
        this.tfGia = tfGia;
    }

    public JTextField getTfSoLuong() {
        return tfSoLuong;
    }

    public void setTfSoLuong(JTextField tfSoLuong) {
        this.tfSoLuong = tfSoLuong;
    }
    
    

}
