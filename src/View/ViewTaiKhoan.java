package View;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ViewTaiKhoan extends JFrame {

    /**
     *
     */
//    private ControllerTaiKhoan ControllerTK = new ControllerTaiKhoan();
    private final JLabel label = new JLabel("Quản lý tài khoản");
    private final JLabel IDLabel = new JLabel("ID:");
    private final JLabel MaNVLabel = new JLabel("Mã nhân viên:");
    private final JLabel TaiKhoanLabel = new JLabel("Tài khoản:");
    private final JLabel MatKhauLabel = new JLabel("Mật khẩu:");
    private final JLabel QuyenLabel = new JLabel("Quyền:");

    // Text field
    private JTextField txtID = new JTextField(20);
    private JTextField txtMaNV = new JTextField(20);
    private JTextField txtTaiKhoan = new JTextField(20);
    private JTextField txtMatKhau = new JTextField(20);
    JComboBox<String> cmbQuyen = new JComboBox<>();

    private JTextField txtTimKiem = new JTextField(20);

    // Button
    private JButton btnThem = new JButton("Thêm");
    private JButton btnSua = new JButton("Sửa");
    private JButton btnXoa = new JButton("Xóa");
    private JButton btnHuy = new JButton("Huỷ");
    private JButton btnTimKiem = new JButton("Tìm kiếm");

    // Table
    DefaultTableModel dtmTaiKhoan;
    private JTable table = new JTable();

    private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    public ViewTaiKhoan() {

//        addEventsTaiKhoan();
        setTitle("Quản lý tài khoản");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Menu(this);

        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.setLayout(new BorderLayout());
        dtmTaiKhoan = new DefaultTableModel();
        dtmTaiKhoan.addColumn("ID");
        dtmTaiKhoan.addColumn("Mã nhân viên");
        dtmTaiKhoan.addColumn("Tài khoản");
        dtmTaiKhoan.addColumn("Mật khẩu");
        dtmTaiKhoan.addColumn("Quyền");
        table = new JTable(dtmTaiKhoan);
        JScrollPane scrTblNhanVien = new JScrollPane(table);
        tablePanel.add(scrTblNhanVien, BorderLayout.CENTER);
        table.setVisible(true);

        // Panel chứa thông tin nhân viên
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        splitPane.setResizeWeight(0.6);

        // Thêm label "Quản lý nhân viên" vào panel, căn giữa
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
        inputPanel.add(IDLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(MaNVLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaNV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(TaiKhoanLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTaiKhoan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(MatKhauLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMatKhau, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(QuyenLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbQuyen, gbc);

        // Panel chứa button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(txtTimKiem);

        // Thêm các panel vào split pane
        splitPane.setLeftComponent(tablePanel);
        splitPane.setRightComponent(inputPanel);

        // Sắp xếp split pane
        add(splitPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Font chữ
        Font font = new Font("Arial", Font.PLAIN, 14);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        IDLabel.setFont(font);//ID
        txtID.setFont(font);
        txtID.setEnabled(false);
        MaNVLabel.setFont(font);//Ten
        txtMaNV.setFont(font);
        TaiKhoanLabel.setFont(font);//MaNV
        txtTaiKhoan.setFont(font);
        MatKhauLabel.setFont(font);//ChucVu
        txtMatKhau.setFont(font);

        QuyenLabel.setFont(font);
        cmbQuyen.setFont(font);//GioiTinh
        cmbQuyen.addItem("NV");
        cmbQuyen.addItem("QL");

        txtTimKiem.setFont(font);

        btnThem.setFont(font);
        btnSua.setFont(font);
        btnXoa.setFont(font);
        btnHuy.setFont(font);
        btnTimKiem.setFont(font);

        // Màu sắc
        Color backgroundColor = new Color(245, 245, 245);
        setBackground(backgroundColor);
        inputPanel.setBackground(backgroundColor);
        buttonPanel.setBackground(backgroundColor);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getTxtID() {
        return txtID;
    }

    public void setTxtID(JTextField txtID) {
        this.txtID = txtID;
    }

    public JTextField getTxtMaNV() {
        return txtMaNV;
    }

    public void setTxtMaNV(JTextField txtMaNV) {
        this.txtMaNV = txtMaNV;
    }

    public JTextField getTxtTaiKhoan() {
        return txtTaiKhoan;
    }

    public void setTxtTaiKhoan(JTextField txtTaiKhoan) {
        this.txtTaiKhoan = txtTaiKhoan;
    }

    public JTextField getTxtMatKhau() {
        return txtMatKhau;
    }

    public void setTxtMatKhau(JTextField txtMatKhau) {
        this.txtMatKhau = txtMatKhau;
    }

    public JComboBox<String> getCmbQuyen() {
        return cmbQuyen;
    }

    public void setCmbQuyen(JComboBox<String> cmbQuyen) {
        this.cmbQuyen = cmbQuyen;
    }

    public JTextField getTxtTimKiem() {
        return txtTimKiem;
    }

    public void setTxtTimKiem(JTextField txtTimKiem) {
        this.txtTimKiem = txtTimKiem;
    }

    public JButton getBtnThem() {
        return btnThem;
    }

    public void setBtnThem(JButton btnThem) {
        this.btnThem = btnThem;
    }

    public JButton getBtnSua() {
        return btnSua;
    }

    public void setBtnSua(JButton btnSua) {
        this.btnSua = btnSua;
    }

    public JButton getBtnXoa() {
        return btnXoa;
    }

    public void setBtnXoa(JButton btnXoa) {
        this.btnXoa = btnXoa;
    }

    public JButton getBtnHuy() {
        return btnHuy;
    }

    public void setBtnHuy(JButton btnHuy) {
        this.btnHuy = btnHuy;
    }

    public JButton getBtnTimKiem() {
        return btnTimKiem;
    }

    public void setBtnTimKiem(JButton btnTimKiem) {
        this.btnTimKiem = btnTimKiem;
    }

    public DefaultTableModel getDtmTaiKhoan() {
        return dtmTaiKhoan;
    }

    public void setDtmTaiKhoan(DefaultTableModel dtmTaiKhoan) {
        this.dtmTaiKhoan = dtmTaiKhoan;
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

}
