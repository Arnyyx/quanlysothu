package View;

import Controller.ControllerDongVat;
import Utils.IntegerDocumentFilter;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class ViewDongVat extends JFrame {

    private ControllerDongVat c = new ControllerDongVat();
    private JLabel label = new JLabel("QUẢN LÝ ĐỘNG VẬT", SwingConstants.CENTER);
    private JLabel labelSLDongVat = new JLabel();

    private String[] lableStrings = {"Mã", "Tên", "Tên chuồng", "Loài", "Tuổi", "Giới Tính", "Trạng thái", "Ảnh"};
    private String[] trangThaiStrings = {"Khoẻ mạnh", "Bị bệnh", "Bị thương", "Mang thai", "Chết"};
    private String[] gioiTinhStrings = {"Đực", "Cái", "Không xác định"};

    private JLabel lbID = new JLabel(lableStrings[0]);
    private JLabel lbTen = new JLabel(lableStrings[1]);
    private JLabel lbTenChuong = new JLabel(lableStrings[2]);
    private JLabel lbLoai = new JLabel(lableStrings[3]);
    private JLabel lbTuoi = new JLabel(lableStrings[4]);
    private JLabel lbGioiTinh = new JLabel(lableStrings[5]);
    private JLabel lbTrangThai = new JLabel(lableStrings[6]);

    private JLabel lbAnhDongVat = new JLabel();

    // Text field
    private JTextField tfID = new JTextField(20);
    private JComboBox<String> tfTenChuong = new JComboBox();
    private JTextField tfTen = new JTextField(20);
    private JComboBox<String> tfLoai = new JComboBox();
    private JTextField tfTuoi = new JTextField(20);
    private JComboBox<String> tfGioiTinh = new JComboBox(gioiTinhStrings);
    private JComboBox<String> tfTrangThai = new JComboBox(trangThaiStrings);
    private JTextField tfTimKiem = new JTextField(20);
    // Button
    private JButton btnAdd = new JButton("Thêm");
    private JButton btnDelete = new JButton("Xoá");
    private JButton btnSave = new JButton("Lưu");
    private JButton btnSearch = new JButton("Tìm kiếm");
    private JButton btnNhap = new JButton("Nhập dữ liệu");
    private JButton btnXuat = new JButton("Xuất dữ liệu");
    private JButton btnLoad = new JButton("Làm mới");
    private JButton btnImageChange = new JButton("Đổi ảnh");

    private JPanel pnAnh = new JPanel();

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

    public ViewDongVat() {
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

        //Panel ảnh
        pnAnh.setPreferredSize(new Dimension((int) pnAnh.getPreferredSize().getWidth(), 220));
        lbAnhDongVat.setPreferredSize(new Dimension(200, 200));
        lbAnhDongVat.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lbAnhDongVat.setIcon(c.getAnhDongVat(""));
        pnAnh.add(lbAnhDongVat);

        table.getTableHeader().setReorderingAllowed(false);
        tfID.setEditable(false);
        tfLoai.setEditable(true);
        ((AbstractDocument) tfTuoi.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        table.setAutoCreateRowSorter(true);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
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
        inputPanel.add(lbTen, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTen, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lbTenChuong, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTenChuong, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(lbLoai, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfLoai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(lbTuoi, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTuoi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(lbGioiTinh, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfGioiTinh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        inputPanel.add(lbTrangThai, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfTrangThai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(pnAnh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(btnImageChange, gbc);

        JPanel buttonPanelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelSouth.add(btnAdd);
        buttonPanelSouth.add(btnDelete);
        buttonPanelSouth.add(btnSave);
        buttonPanelSouth.add(btnNhap);
        buttonPanelSouth.add(btnXuat);

        JPanel buttonPanelNorth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanelNorth.add(labelSLDongVat);
        buttonPanelNorth.add(btnLoad);
        buttonPanelNorth.add(btnSearch);
        buttonPanelNorth.add(tfTimKiem);

        // Thêm các panel vào split pane
        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(inputPanel);
        splitPane.setResizeWeight(0.8);

        // Sắp xếp split pane
        add(buttonPanelNorth, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanelSouth, BorderLayout.SOUTH);

//         Font chữ
        Font font = UIManager.getFont("large.font");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        lbID.setFont(font);
        lbTenChuong.setFont(font);
        lbTen.setFont(font);
        lbLoai.setFont(font);
        lbTuoi.setFont(font);
        lbGioiTinh.setFont(font);
        lbTrangThai.setFont(font);

        tfID.setFont(font);
        tfTenChuong.setFont(font);
        tfTen.setFont(font);
        tfLoai.setFont(font);
        tfTuoi.setFont(font);
        tfGioiTinh.setFont(font);
        tfTrangThai.setFont(font);
        tfTimKiem.setFont(font);

        btnAdd.setFont(font);
        btnDelete.setFont(font);
        btnSave.setFont(font);
        btnSearch.setFont(font);
        btnNhap.setFont(font);
        btnXuat.setFont(font);
        btnLoad.setFont(font);
        btnImageChange.setFont(font);
        labelSLDongVat.setFont(font);

        tableModel.addColumn(lableStrings[0]);
        tableModel.addColumn(lableStrings[1]);
        tableModel.addColumn(lableStrings[2]);
        tableModel.addColumn(lableStrings[3]);
        tableModel.addColumn(lableStrings[4]);
        tableModel.addColumn(lableStrings[5]);
        tableModel.addColumn(lableStrings[6]);
        tableModel.addColumn("Ảnh");
        table.setFont(font);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JComboBox<String> getTfTenChuong() {
        return tfTenChuong;
    }

    public void setTfTenChuong(JComboBox<String> tfTenChuong) {
        this.tfTenChuong = tfTenChuong;
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

    public JLabel getLbTuoi() {
        return lbTuoi;
    }

    public JLabel getLbGioiTinh() {
        return lbGioiTinh;
    }

    public JLabel getLbAnhDongVat() {
        return lbAnhDongVat;
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

    public JTextField getTfTuoi() {
        return tfTuoi;
    }

    public JComboBox getTfGioiTinh() {
        return tfGioiTinh;
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

    public JButton getBtnImageChange() {
        return btnImageChange;
    }

    public JPanel getPnAnh() {
        return pnAnh;
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

    public JComboBox<String> getTfTrangThai() {
        return tfTrangThai;
    }

    public JLabel getLabelSLDongVat() {
        return labelSLDongVat;
    }

    public void setLabelSLDongVat(String text) {
        this.labelSLDongVat.setText(text);
    }

}
