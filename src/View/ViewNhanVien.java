package View;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class ViewNhanVien extends JFrame {

    private final JLabel label = new JLabel("Quản lý nhân viên");
    private final JLabel IDLabel = new JLabel("ID:");
    private final JLabel TenNVLabel = new JLabel("Tên:");
    private final JLabel MaNVLabel = new JLabel("Mã Nhân viên:");
    private final JLabel ChucVuLabel = new JLabel("Chức vụ:");
    private final JLabel GioiTinhLabel = new JLabel("Giới tính:");
    private final JLabel NgaySinhLabel = new JLabel("Ngày sinh:");
    private final JLabel SDTLabel = new JLabel("Số điện thoại:");
    private final JLabel DiaChiLabel = new JLabel("Địa chỉ:");
    private final JLabel EmailLabel = new JLabel("Email:");
    private final JLabel NgayVaoLamLabel = new JLabel("Ngày vào làm:");

    // Text field
    private JTextField txtID = new JTextField(20);
    private JTextField txtTenNV = new JTextField(20);
    private JTextField txtMaNV = new JTextField(20);

    JComboBox<String> cmbChucVu = new JComboBox<>();
    JComboBox<String> cmbGioiTinh = new JComboBox<>();

    private JTextField txtNgaySinh = new JTextField(20);
    private JTextField txtSDT = new JTextField(20);
    private JTextField txtDiaChi = new JTextField(20);
    private JTextField txtEmail = new JTextField(20);
    private JTextField txtNgayVaoLam = new JTextField(20);
    public JTextField txtTimKiem = new JTextField(20);

    // Button
    private JButton btnThem = new JButton("Thêm");
    private JButton btnSua = new JButton("Sửa");
    private JButton btnXoa = new JButton("Xóa");
    private JButton btnHuy = new JButton("Huỷ");
    private JButton btnTimKiem = new JButton("Tìm kiếm");
    private JButton btnNhapExcel = new JButton("Nhập file Excel");
    private JButton btnXuatExcel = new JButton("Xuất file Excel");

    // Table
    DefaultTableModel dtmNhanVien;
    private JTable table = new JTable();

    private final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

    public ViewNhanVien() {

//        new ControllerNhanVien();
//        private ControllerNhanVien controllerNhanVien = new ControllerNhanVien();
        setTitle("Quản lý nhân viên");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Menu(this);

        // Panel chứa table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        tablePanel.setLayout(new BorderLayout());
        dtmNhanVien = new DefaultTableModel();
        dtmNhanVien.addColumn("ID");
        dtmNhanVien.addColumn("Tên nhân viên");
        dtmNhanVien.addColumn("Mã nhân viên");
        dtmNhanVien.addColumn("Chức vụ");
        dtmNhanVien.addColumn("Giới tính");
        dtmNhanVien.addColumn("Ngày sinh");
        dtmNhanVien.addColumn("SDT");
        dtmNhanVien.addColumn("Địa chỉ");
        dtmNhanVien.addColumn("Email");
        dtmNhanVien.addColumn("Ngày vào làm");
        table = new JTable(dtmNhanVien);
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
        inputPanel.add(TenNVLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtTenNV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(MaNVLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtMaNV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(ChucVuLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbChucVu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(GioiTinhLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbGioiTinh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        inputPanel.add(NgaySinhLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNgaySinh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        inputPanel.add(SDTLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtSDT, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        inputPanel.add(DiaChiLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtDiaChi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        inputPanel.add(EmailLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        inputPanel.add(NgayVaoLamLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(txtNgayVaoLam, gbc);

        // Panel chứa button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnHuy);
        buttonPanel.add(btnTimKiem);
        buttonPanel.add(txtTimKiem);
        buttonPanel.add(btnNhapExcel);
        buttonPanel.add(btnXuatExcel);

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
        txtID.setEnabled(false);
        TenNVLabel.setFont(font);//Ten
        txtTenNV.setFont(font);
        MaNVLabel.setFont(font);//MaNV
        txtMaNV.setFont(font);
        ChucVuLabel.setFont(font);//ChucVu
        cmbChucVu.setFont(font);
        cmbChucVu.addItem("Nhân viên");
        cmbChucVu.addItem("Quản lý");

        GioiTinhLabel.setFont(font);
//        txtGioiTinh.setFont(font);
        cmbGioiTinh.setFont(font);//GioiTinh
        cmbGioiTinh.addItem("Nam");
        cmbGioiTinh.addItem("Nữ");
        NgaySinhLabel.setFont(font);//NgaySinh
        txtNgaySinh.setFont(font);
        SDTLabel.setFont(font);//SDT
        txtSDT.setFont(font);
        DiaChiLabel.setFont(font);//DiaChi
        txtDiaChi.setFont(font);
        EmailLabel.setFont(font);//Email
        txtEmail.setFont(font);
        NgayVaoLamLabel.setFont(font);//NgayVaoLam
        txtNgayVaoLam.setFont(font);

        txtTimKiem.setFont(font);

        btnThem.setFont(font);
        btnSua.setFont(font);
        btnXoa.setFont(font);
        btnHuy.setFont(font);
        btnTimKiem.setFont(font);
        btnXuatExcel.setFont(font);
        btnNhapExcel.setFont(font);

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

    public JTextField getTxtTenNV() {
        return txtTenNV;
    }

    public void setTxtTenNV(JTextField txtTenNV) {
        this.txtTenNV = txtTenNV;
    }

    public JTextField getTxtMaNV() {
        return txtMaNV;
    }

    public void setTxtMaNV(JTextField txtMaNV) {
        this.txtMaNV = txtMaNV;
    }

    public JComboBox<String> getCmbChucVu() {
        return cmbChucVu;
    }

    public void setCmbChucVu(JComboBox<String> cmbChucVu) {
        this.cmbChucVu = cmbChucVu;
    }

    public JComboBox<String> getCmbGioiTinh() {
        return cmbGioiTinh;
    }

    public void setCmbGioiTinh(JComboBox<String> cmbGioiTinh) {
        this.cmbGioiTinh = cmbGioiTinh;
    }

    public JTextField getTxtNgaySinh() {
        return txtNgaySinh;
    }

    public void setTxtNgaySinh(JTextField txtNgaySinh) {
        this.txtNgaySinh = txtNgaySinh;
    }

    public JTextField getTxtSDT() {
        return txtSDT;
    }

    public void setTxtSDT(JTextField txtSDT) {
        this.txtSDT = txtSDT;
    }

    public JTextField getTxtDiaChi() {
        return txtDiaChi;
    }

    public void setTxtDiaChi(JTextField txtDiaChi) {
        this.txtDiaChi = txtDiaChi;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTextField getTxtNgayVaoLam() {
        return txtNgayVaoLam;
    }

    public void setTxtNgayVaoLam(JTextField txtNgayVaoLam) {
        this.txtNgayVaoLam = txtNgayVaoLam;
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

    public JButton getBtnNhapExcel() {
        return btnNhapExcel;
    }

    public void setBtnNhapExcel(JButton btnNhapExcel) {
        this.btnNhapExcel = btnNhapExcel;
    }

    public JButton getBtnXuatExcel() {
        return btnXuatExcel;
    }

    public void setBtnXuatExcel(JButton btnXuatExcel) {
        this.btnXuatExcel = btnXuatExcel;
    }

    public DefaultTableModel getDtmNhanVien() {
        return dtmNhanVien;
    }

    public void setDtmNhanVien(DefaultTableModel dtmNhanVien) {
        this.dtmNhanVien = dtmNhanVien;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

}
